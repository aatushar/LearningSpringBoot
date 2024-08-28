package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanySupplierBankDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanySupplierBankDetailRepository extends JpaRepository<CompanySupplierBankDetail, Long> {
    Optional<CompanySupplierBankDetail> findCompanySupplierBankDetailById(Long id);

    void deleteCompanySupplierBankDetailById(Long id);
}
