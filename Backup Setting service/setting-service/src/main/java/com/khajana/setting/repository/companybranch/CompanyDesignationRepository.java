package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanyDesignation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CompanyDesignationRepository extends JpaRepository<CompanyDesignation, Long> {
    Optional<CompanyDesignation> findCompanyDesignationById(Long id);

    Page<CompanyDesignation> findAllCompanyDesignationByDesigNameContaining(String desigName, Pageable pageable);

    void deleteCompanyDesignationById(Long id);
}
