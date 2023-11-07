package com.mhk.booking.service.impl;

import com.mhk.booking.models.PurchasePackage;
import com.mhk.booking.repository.PurchasePackageRepository;
import com.mhk.booking.service.PurchasePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchasePackageServiceImpl implements PurchasePackageService {
    private final PurchasePackageRepository packageRepository;

    @Autowired
    public PurchasePackageServiceImpl(PurchasePackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public List<PurchasePackage> getPackagesByCountry(String country) {
        return packageRepository.findByCountry(country);
    }


}
