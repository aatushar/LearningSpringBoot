package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanyBranchAddressDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyBranchAddressDetailRepository extends JpaRepository<CompanyBranchAddressDetail, Long> {
    Optional<CompanyBranchAddressDetail> findCompanyBranchAddressDetailById(Long id);

    void deleteCompanyBranchAddressDetailById(Long id);
}
