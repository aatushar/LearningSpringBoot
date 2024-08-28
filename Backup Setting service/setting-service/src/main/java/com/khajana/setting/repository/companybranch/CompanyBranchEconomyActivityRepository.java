package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanyBranchEconomicActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyBranchEconomyActivityRepository extends JpaRepository<CompanyBranchEconomicActivity, Long> {
    Optional<CompanyBranchEconomicActivity> findCompanyBranchEconomicActivityById(Long id);

    void deleteCompanyBranchEconomicActivityById(Long id);
}
