package com.mhk.booking.repository;

import com.mhk.booking.models.PurchasePackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchasePackageRepository extends JpaRepository<PurchasePackage, Long> {
    List<PurchasePackage> findByCountry(String country);
}
