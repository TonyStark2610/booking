package com.amirul.booking.Controller;

import com.amirul.booking.Dto.PurchaseDto;
import com.amirul.booking.Service.MockPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private MockPaymentService mockPaymentService;

    @PostMapping("/charge")
    public ResponseEntity<String> charge(@RequestBody PurchaseDto purchaseDto) {
        boolean result = mockPaymentService.charge(purchaseDto);
        if (result) {
            return ResponseEntity.ok("Payment successful.");
        } else {
            return ResponseEntity.status(400).body("Payment failed.");
        }
    }
}
