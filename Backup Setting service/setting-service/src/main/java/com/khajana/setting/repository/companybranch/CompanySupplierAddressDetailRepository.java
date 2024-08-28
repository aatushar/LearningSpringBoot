package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanySupplierAddressDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanySupplierAddressDetailRepository extends JpaRepository<CompanySupplierAddressDetail, Long> {
    Optional<CompanySupplierAddressDetail> findCompanySupplierAddressDetailById(Long id);

    // void deleteCompanySupplierAddressDetailByById(Long id);
}
