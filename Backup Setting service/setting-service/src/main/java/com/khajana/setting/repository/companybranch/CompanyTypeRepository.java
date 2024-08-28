package com.khajana.setting.repository.companybranch;

import com.khajana.setting.entity.companybranch.CompanyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyTypeRepository extends JpaRepository<CompanyTypeEntity, Long> {
    Optional<CompanyTypeEntity> findCompanyTypeById(Long id);

    boolean existsByCompanyTypeCode(String name);

    boolean existsByCompanyTypeCodeAndIdNot(String name, Long id);

    boolean existsByCompanyTypeCodeBn(String name);

    boolean existsByCompanyTypeCodeBnAndIdNot(String name, Long id);

    boolean existsByCompanyType(String name);

    boolean existsByCompanyTypeAndIdNot(String name, Long id);

    boolean existsByCompanyTypeBn(String name);

    boolean existsByCompanyTypeBnAndIdNot(String name, Long id);

}
