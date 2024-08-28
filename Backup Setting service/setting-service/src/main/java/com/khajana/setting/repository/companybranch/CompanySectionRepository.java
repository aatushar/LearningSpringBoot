package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanySection;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CompanySectionRepository extends JpaRepository<CompanySection, Long> {
    Optional<CompanySection> findCompanySectionById(Long id);

    Page<CompanySection> findAllCompanySectionBySecNameContaining(String secName, org.springframework.data.domain.Pageable pageable);

    void deleteCompanySectionById(Long id);
}
