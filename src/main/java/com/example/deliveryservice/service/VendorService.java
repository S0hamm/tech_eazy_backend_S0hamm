package com.example.deliveryservice.service;

import com.example.deliveryservice.model.Vendor;
import com.example.deliveryservice.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;

    @Transactional(readOnly = true)
    public Page<Vendor> getAllVendors(Pageable pageable) {
        return vendorRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Vendor> getVendorById(Long id) {
        return vendorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Vendor> getVendorByName(String name) {
        return vendorRepository.findByName(name);
    }

    @Transactional
    public Vendor createVendor(Vendor vendor) {
        // Add validation here if necessary (e.g., check for duplicate names)
        return vendorRepository.save(vendor);
    }

    @Transactional
    public Vendor updateVendor(Long id, Vendor updatedVendor) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    vendor.setName(updatedVendor.getName());
                    vendor.setSubscriptionType(updatedVendor.getSubscriptionType());
                    // Update other fields as needed
                    return vendorRepository.save(vendor);
                }).orElseThrow(() -> new RuntimeException("Vendor not found with id: " + id)); // Use custom exception
    }

    @Transactional
    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }
}