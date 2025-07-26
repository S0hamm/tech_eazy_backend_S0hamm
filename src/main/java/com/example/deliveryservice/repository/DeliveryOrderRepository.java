package com.example.deliveryservice.repository;

import com.example.deliveryservice.model.DeliveryOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // <--- ADD THIS IMPORT
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long>, JpaSpecificationExecutor<DeliveryOrder> {
    // This method is fine as it uses a direct property of DeliveryOrder
    Page<DeliveryOrder> findByOrderDeliveryDate(LocalDate orderDeliveryDate, Pageable pageable);

    // No need for a findByVendorNameAndOrderDeliveryDate as we're using specifications for dynamic filtering
    // @Query("SELECT do FROM DeliveryOrder do WHERE do.vendor.name = :vendorName AND do.orderDeliveryDate = :date")
    // Page<DeliveryOrder> findByVendorNameAndOrderDeliveryDateCustom(@Param("vendorName") String vendorName, @Param("date") LocalDate date, Pageable pageable);
}