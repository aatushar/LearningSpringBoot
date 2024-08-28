package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanyDepartment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CompanyDepartmentRepository extends JpaRepository<CompanyDepartment, Long> {
    Optional<CompanyDepartment> findCompanyDepartmentById(Long id);

    Page<CompanyDepartment> findAllCompanyDepartmentByDepartmentNameContaining(String departmentName, Pageable pageable);

    void deleteCompanyDepartmentById(Long id);
}
