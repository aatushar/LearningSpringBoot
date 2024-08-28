package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanySupplierContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanySupplierContactInfoRepository extends JpaRepository<CompanySupplierContactInfo, Long> {
    Optional<CompanySupplierContactInfo> findCompanySupplierContactInfoById(Long id);

    void deleteCompanySupplierContactInfoById(Long id);
}
