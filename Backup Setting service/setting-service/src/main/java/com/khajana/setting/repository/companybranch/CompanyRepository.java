package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.Company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findCompanyById(Long id);
    
    Page<Company> findAllCompanyByCompNameContaining(String compName, Pageable pageable);
    
    Page<Company> findByCompNameContaining(String compName, Pageable pageable);

    void deleteCompanyById(Long id);
}
