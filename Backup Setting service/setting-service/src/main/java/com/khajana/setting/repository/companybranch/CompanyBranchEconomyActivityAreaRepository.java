package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanyBranchEconomicActivityArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyBranchEconomyActivityAreaRepository extends JpaRepository<CompanyBranchEconomicActivityArea, Long> {
    Optional<CompanyBranchEconomicActivityArea> findCompanyBranchEconomicActivityAreaById(Long id);

    void deleteCompanyBranchEconomicActivityAreaById(Long id);
}
