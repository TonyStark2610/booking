package com.amirul.booking.Service;

import com.amirul.booking.Dto.PurchaseDto;
import com.amirul.booking.Entity.Packages;
import com.amirul.booking.Entity.User;
import com.amirul.booking.Repo.PackageRepository;
import com.amirul.booking.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Packages> getPackagesByCountry(String country) {
        return packageRepository.findByCountry(country);
    }

    public void purchasePackage(PurchaseDto purchaseDto) {
        Packages pkg = packageRepository.findById(purchaseDto.getPackageId()).orElseThrow();
        User user = userRepository.findById(purchaseDto.getUserId()).orElseThrow();
        // Logic to add package to user's account (e.g., saving to a UserPackage table)
    }
}

