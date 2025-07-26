package com.example.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelResponse {
    private Long id;
    private String trackingNumber;
    private String recipientName;
    private String recipientAddress;
    private String pincode;
    private Double weight;
}