package com.example.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryOrderUploadResponse {
    private String message;
    private int parcelsProcessed;
    private String deliveryOrderLink; // Link to the created delivery order (API URL)
    private String originalFileDownloadLink; // Link to the stored file
}