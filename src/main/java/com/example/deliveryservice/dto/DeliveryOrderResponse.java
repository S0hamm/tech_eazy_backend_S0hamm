package com.example.deliveryservice.dto;

import com.example.deliveryservice.enums.VendorSubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryOrderResponse {
    private Long id;
    private String vendorName;
    private VendorSubscriptionType vendorSubscriptionType;
    private LocalDate orderDeliveryDate;
    private String originalFileLink;
    private LocalDateTime uploadTimestamp;
    private List<ParcelResponse> parcels; // Include parcel details
}