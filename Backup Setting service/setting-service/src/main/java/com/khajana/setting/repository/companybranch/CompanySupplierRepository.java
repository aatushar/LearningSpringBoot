package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanySupplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CompanySupplierRepository extends JpaRepository<CompanySupplier, Long> {
    Optional<CompanySupplier> findCompanySupplierById(Long id);

    Page<CompanySupplier> findAllCompanySupplierBySupplierNameContaining(String supplierName, Pageable pageable);

    void deleteCompanySupplierById(Long id);
}
