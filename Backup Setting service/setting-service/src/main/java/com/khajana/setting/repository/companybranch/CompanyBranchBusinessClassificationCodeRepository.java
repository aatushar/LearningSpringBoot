package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanyBranchBusinessClassificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyBranchBusinessClassificationCodeRepository extends JpaRepository<CompanyBranchBusinessClassificationCode, Long> {
    Optional<CompanyBranchBusinessClassificationCode> findCompanyBranchBusinessClassificationCodeById(Long id);

    void deleteCompanyBranchBusinessClassificationCodeById(Long id);
}
