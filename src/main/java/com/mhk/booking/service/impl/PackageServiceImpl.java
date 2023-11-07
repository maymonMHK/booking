/*
package com.mhk.booking.service.impl;

import com.mhk.booking.models.PurchasePackage;
import com.mhk.booking.models.User;
import com.mhk.booking.service.PurchasePackageService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class PackageServiceImpl implements PurchasePackageService {
    private Map<String, Package> packages = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private Map<String, List<Package>> userPurchasedPackages = new HashMap<>();

    private List<Package> availablePackages = new ArrayList<>();

    public boolean purchasePackage(User user, PurchasePackage packageToPurchase) {

        boolean paymentSuccessful = Math.random() < 0.9;
        if (paymentSuccessful) {

            users.get(user.getUsername()).addPurchasedPackage(packageToPurchase);
            users.get(user.getUsername()).addCredits(packageToPurchase.getCredits());
            return true;
        } else {
            return false;
        }

    }
        public List<PurchasePackage> getAvailablePackages (String country){
            List<PurchasePackage> packagesForCountry = new ArrayList<>();

            for (PurchasePackage packageItem : availablePackages) {
                if (packageItem.getCountry().equalsIgnoreCase(country)) {
                    packagesForCountry.add(packageItem);
                }
            }

            return packagesForCountry;
        }


    public List<Package> getPurchasedPackages(User user) {
            List<Package> purchasedPackages = userPurchasedPackages.get(user.getUsername());

            if (purchasedPackages != null) {
                return purchasedPackages;
            }

            return new ArrayList<>();

    }

        public int getRemainingCredits(User user, PurchasePackage packageToCheck) {
            if (user == null || packageToCheck == null) {
                return 0;
            }

            int totalCredits = packageToCheck.getCredits();
            int usedCredits = user.getUsedCredits();

            int remainingCredits = totalCredits - usedCredits;

            return remainingCredits >= 0 ? remainingCredits : 0;
        }



}
*/
