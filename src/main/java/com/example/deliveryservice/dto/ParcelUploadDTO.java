package com.example.deliveryservice.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// This DTO maps to columns in your CSV file
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelUploadDTO {
    @CsvBindByName(column = "trackingNumber") // Adjust column names based on your CSV header
    private String trackingNumber;
    @CsvBindByName(column = "recipientName")
    private String recipientName;
    @CsvBindByName(column = "recipientAddress")
    private String recipientAddress;
    @CsvBindByName(column = "pincode")
    private String pincode;
    @CsvBindByName(column = "weight")
    private Double weight;
    // Add more fields if your parcel data has them
}