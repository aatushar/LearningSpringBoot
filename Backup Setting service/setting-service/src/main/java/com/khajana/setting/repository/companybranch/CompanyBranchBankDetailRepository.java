package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanyBranchBankDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyBranchBankDetailRepository extends JpaRepository<CompanyBranchBankDetail, Long> {
    Optional<CompanyBranchBankDetail> findCompanyBranchBankDetailById(Long id);

    void deleteCompanyBranchBankDetailById(Long id);
}
