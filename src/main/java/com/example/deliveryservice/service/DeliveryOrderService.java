package com.example.deliveryservice.service;

import com.example.deliveryservice.dto.DeliveryOrderResponse;
import com.example.deliveryservice.dto.DeliveryOrderUploadResponse;
import com.example.deliveryservice.dto.ParcelResponse;
import com.example.deliveryservice.dto.ParcelUploadDTO;
import com.example.deliveryservice.model.DeliveryOrder;
import com.example.deliveryservice.model.Parcel;
import com.example.deliveryservice.model.Vendor;
import com.example.deliveryservice.repository.DeliveryOrderRepository;
import com.example.deliveryservice.repository.VendorRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate; // <--- Correct import for Predicate
import jakarta.persistence.criteria.Root;      // <--- Correct import for Root
import jakarta.persistence.criteria.CriteriaBuilder; // <--- Correct import for CriteriaBuilder
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification; // <--- ADD THIS IMPORT
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList; // <--- ADD THIS IMPORT
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryOrderService {

    private final DeliveryOrderRepository deliveryOrderRepository;
    private final VendorRepository vendorRepository;
    private final FileStorageService fileStorageService;

    // For "Create a parcel - save it in memory"
    public ParcelResponse createParcelInMemory(ParcelUploadDTO parcelDto) {
        return new ParcelResponse(
                null, // No ID as it's not saved
                parcelDto.getTrackingNumber(),
                parcelDto.getRecipientName(),
                parcelDto.getRecipientAddress(),
                parcelDto.getPincode(),
                parcelDto.getWeight()
        );
    }

    @Transactional
    public DeliveryOrderUploadResponse uploadOrderDetails(MultipartFile file, String vendorName, LocalDate orderDeliveryDate) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty.");
        }
        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("Only CSV files are supported for upload.");
        }

        Vendor vendor = vendorRepository.findByName(vendorName)
                .orElseThrow(() -> new EntityNotFoundException("Vendor with name " + vendorName + " not found. Please create the vendor first."));

        String fileLink = fileStorageService.storeFile(file);

        List<ParcelUploadDTO> parcelUploadDTOs;
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            parcelUploadDTOs = new CsvToBeanBuilder<ParcelUploadDTO>(reader)
                    .withType(ParcelUploadDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        }

        if (parcelUploadDTOs.isEmpty()) {
            throw new IllegalArgumentException("No parcel data found in the uploaded CSV.");
        }

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setVendor(vendor);
        deliveryOrder.setOrderDeliveryDate(orderDeliveryDate);
        deliveryOrder.setOriginalFileLink(fileLink);
        deliveryOrder.setUploadTimestamp(LocalDateTime.now());

        List<Parcel> parcels = parcelUploadDTOs.stream().map(dto -> {
            Parcel parcel = new Parcel();
            parcel.setTrackingNumber(dto.getTrackingNumber() != null ? dto.getTrackingNumber() : UUID.randomUUID().toString());
            parcel.setRecipientName(dto.getRecipientName());
            parcel.setRecipientAddress(dto.getRecipientAddress());
            parcel.setPincode(dto.getPincode());
            parcel.setWeight(dto.getWeight());
            parcel.setDeliveryOrder(deliveryOrder);
            return parcel;
        }).collect(Collectors.toList());

        deliveryOrder.setParcels(parcels);

        DeliveryOrder savedOrder = deliveryOrderRepository.save(deliveryOrder);

        return new DeliveryOrderUploadResponse(
                "Delivery order and parcels uploaded successfully.",
                parcels.size(),
                "/api/delivery-orders/" + savedOrder.getId(),
                fileLink
        );
    }

    @Transactional(readOnly = true)
    public Page<DeliveryOrderResponse> getDeliveryOrdersForToday(Pageable pageable) {
        LocalDate today = LocalDate.now();
        Page<DeliveryOrder> ordersPage = deliveryOrderRepository.findByOrderDeliveryDate(today, pageable);
        return convertToResponsePage(ordersPage);
    }

    @Transactional(readOnly = true)
    public Page<DeliveryOrderResponse> getFilteredDeliveryOrders(String vendorName, LocalDate date, Pageable pageable) {
        // Using JpaSpecificationExecutor for dynamic filtering
        Specification<DeliveryOrder> spec = (Root<DeliveryOrder> root, jakarta.persistence.criteria.CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (vendorName != null && !vendorName.isEmpty()) {
                predicates.add(cb.equal(root.get("vendor").get("name"), vendorName));
            }
            if (date != null) {
                predicates.add(cb.equal(root.get("orderDeliveryDate"), date));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<DeliveryOrder> ordersPage = deliveryOrderRepository.findAll(spec, pageable);
        return convertToResponsePage(ordersPage);
    }

    private Page<DeliveryOrderResponse> convertToResponsePage(Page<DeliveryOrder> ordersPage) {
        List<DeliveryOrderResponse> responses = ordersPage.getContent().stream().map(order -> {
            DeliveryOrderResponse dto = new DeliveryOrderResponse();
            dto.setId(order.getId());
            dto.setVendorName(order.getVendor().getName());
            dto.setVendorSubscriptionType(order.getVendor().getSubscriptionType());
            dto.setOrderDeliveryDate(order.getOrderDeliveryDate());
            dto.setOriginalFileLink(order.getOriginalFileLink());
            dto.setUploadTimestamp(order.getUploadTimestamp());
            dto.setParcels(order.getParcels().stream().map(parcel -> new ParcelResponse(
                    parcel.getId(),
                    parcel.getTrackingNumber(),
                    parcel.getRecipientName(),
                    parcel.getRecipientAddress(),
                    parcel.getPincode(),
                    parcel.getWeight()
            )).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
        return new PageImpl<>(responses, ordersPage.getPageable(), ordersPage.getTotalElements());
    }
}