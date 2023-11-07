package com.mhk.booking.controllers;

import com.mhk.booking.models.PurchasePackage;
import com.mhk.booking.service.PurchasePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/packages")
public class PurchasePackageController {

    @Autowired
    PurchasePackageService  packageService;

    @GetMapping("/{country}")
    public List<PurchasePackage> getPackagesByCountry(@PathVariable String country) {
        return packageService.getPackagesByCountry(country);
    }

}
