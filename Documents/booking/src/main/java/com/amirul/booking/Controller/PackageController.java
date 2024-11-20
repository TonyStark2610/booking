package com.amirul.booking.Controller;

import com.amirul.booking.Dto.PurchaseDto;
import com.amirul.booking.Entity.Packages;
import com.amirul.booking.Service.BookingService;
import com.amirul.booking.Service.MockPaymentService;
import com.amirul.booking.Service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    PackageService packageService;

    @Autowired
    BookingService bookingService;

    @Autowired
    MockPaymentService mockPaymentService;
    @GetMapping
    public ResponseEntity<List<Packages>> getAvailablePackages(@RequestParam String country) {
        return ResponseEntity.ok(packageService.getPackagesByCountry(country));
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchasePackage(@RequestBody PurchaseDto purchaseDto) {
        boolean paymentSuccess = mockPaymentService.charge(purchaseDto);
        if (paymentSuccess) {
            packageService.purchasePackage(purchaseDto);
            return ResponseEntity.ok("Package purchased successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body("Payment failed.");
        }
    }

    @PutMapping("/cancel")
    public ResponseEntity<String> cancelBooking(@RequestParam Long bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok("Booking canceled successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
