package com.mhk.booking.service;

import com.mhk.booking.models.PurchasePackage;

import java.util.List;

public interface PurchasePackageService {
    List<PurchasePackage> getPackagesByCountry(String country);

    /*    List<Package> getAvailablePackages(String country);*/
}
