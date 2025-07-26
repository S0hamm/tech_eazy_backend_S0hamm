package com.example.deliveryservice.controller;

import com.example.deliveryservice.dto.DeliveryOrderResponse;
import com.example.deliveryservice.dto.DeliveryOrderUploadResponse;
import com.example.deliveryservice.dto.ParcelResponse;
import com.example.deliveryservice.dto.ParcelUploadDTO;
import com.example.deliveryservice.service.DeliveryOrderService;
import com.example.deliveryservice.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/delivery-orders")
@RequiredArgsConstructor
public class DeliveryOrderController {

    private final DeliveryOrderService deliveryOrderService;
    private final FileStorageService fileStorageService;


    @PostMapping("/upload")
    public ResponseEntity<DeliveryOrderUploadResponse> uploadOrderDetails(
            @RequestParam("file") MultipartFile file,
            @RequestParam("vendorName") String vendorName,
            @RequestParam("orderDeliveryDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate orderDeliveryDate) throws Exception {
        DeliveryOrderUploadResponse response = deliveryOrderService.uploadOrderDetails(file, vendorName, orderDeliveryDate);

        // Enhance file link to be a full URL for direct download via an API endpoint
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/")
                .path(response.getOriginalFileDownloadLink().replace("/files/", "")) // Extract filename
                .toUriString();
        response.setOriginalFileDownloadLink(fileDownloadUri);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/today")
    public ResponseEntity<Page<DeliveryOrderResponse>> getDeliveryOrdersForToday(Pageable pageable) {
        Page<DeliveryOrderResponse> orders = deliveryOrderService.getDeliveryOrdersForToday(pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<Page<DeliveryOrderResponse>> getFilteredDeliveryOrders(
            @RequestParam(value = "vendorName", required = false) String vendorName,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Pageable pageable) {
        Page<DeliveryOrderResponse> orders = deliveryOrderService.getFilteredDeliveryOrders(vendorName, date, pageable);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/parcels/in-memory")
    public ResponseEntity<ParcelResponse> createParcelInMemory(@RequestBody ParcelUploadDTO parcelDto) {
        // This endpoint fulfills the "save it in memory" requirement directly.
        // In a real production system, this kind of endpoint might not exist standalone,
        // or would be clearly marked as non-persistent.
        ParcelResponse response = deliveryOrderService.createParcelInMemory(parcelDto);
        return ResponseEntity.ok(response);
    }
}