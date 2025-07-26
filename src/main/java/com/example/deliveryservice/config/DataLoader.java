package com.example.deliveryservice.config;

import com.example.deliveryservice.enums.VendorSubscriptionType;
import com.example.deliveryservice.model.User;
import com.example.deliveryservice.model.Vendor;
import com.example.deliveryservice.repository.UserRepository;
import com.example.deliveryservice.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VendorRepository vendorRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create initial users if they don't exist
        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Set.of("USER"));
            userRepository.save(user);
            log.info("Created user: user/password");
        }
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of("ADMIN", "USER"));
            userRepository.save(admin);
            log.info("Created admin user: admin/admin");
        }
        if (userRepository.findByUsername("myntra_vendor").isEmpty()) {
            User vendorUser = new User();
            vendorUser.setUsername("myntra_vendor");
            vendorUser.setPassword(passwordEncoder.encode("vendorpass"));
            vendorUser.setRoles(Set.of("VENDOR_USER")); // Custom role for vendors
            userRepository.save(vendorUser);
            log.info("Created vendor user: myntra_vendor/vendorpass");
        }

        // Create sample vendors if they don't exist
        if (vendorRepository.findByName("Myntra").isEmpty()) {
            Vendor myntra = new Vendor();
            myntra.setName("Myntra");
            myntra.setSubscriptionType(VendorSubscriptionType.PRIME);
            vendorRepository.save(myntra);
            log.info("Created vendor: Myntra (PRIME)");
        }
        if (vendorRepository.findByName("Flipkart").isEmpty()) {
            Vendor flipkart = new Vendor();
            flipkart.setName("Flipkart");
            flipkart.setSubscriptionType(VendorSubscriptionType.NORMAL);
            vendorRepository.save(flipkart);
            log.info("Created vendor: Flipkart (NORMAL)");
        }
    }
}