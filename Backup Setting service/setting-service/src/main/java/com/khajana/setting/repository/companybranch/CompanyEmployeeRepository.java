package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanyEmployee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CompanyEmployeeRepository extends JpaRepository<CompanyEmployee, Long> {
    Optional<CompanyEmployee> findCompanyEmployeeById(Long id);

    Page<CompanyEmployee> findAllCompanyEmployeeByNameContaining(String name, Pageable pageable);

    void deleteCompanyEmployeeById(Long id);
}
