package com.amirul.booking.Service;

import com.amirul.booking.Dto.PurchaseDto;
import org.springframework.stereotype.Service;

@Service
public class MockPaymentService {
    public boolean charge(PurchaseDto purchaseDto) {
        if (purchaseDto == null || purchaseDto.getAmount() <= 0) {
            System.out.println("[MockPaymentService] Invalid purchase details.");
            return false;
        }

        System.out.println("[MockPaymentService] Payment successful. Charged amount: " + purchaseDto.getAmount());
        return true;
    }
}