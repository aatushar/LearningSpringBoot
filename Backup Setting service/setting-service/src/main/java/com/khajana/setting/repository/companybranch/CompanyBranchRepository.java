package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanyBranch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyBranchRepository extends JpaRepository<CompanyBranch, Long> {
    Optional<CompanyBranch> findCompanyBranchById(Long id);

    Page<CompanyBranch> findAllCompanyBranchByBranchUnitNameContaining(String branchUnitName, Pageable pageable);

    void deleteCompanyBranchById(Long id);
}
