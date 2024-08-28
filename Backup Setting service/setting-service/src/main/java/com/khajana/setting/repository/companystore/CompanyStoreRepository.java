package com.khajana.setting.repository.companystore;

import com.khajana.setting.entity.companystore.CompanyStore;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyStoreRepository extends JpaRepository<CompanyStore, Long> {
    Optional<CompanyStore> findCompanyStoreById(Long id);

    Page<CompanyStore> findAllCompanyStoreBySlNameContaining(String slName, Pageable pageable);

    void deleteCompanyStoreById(Long id);
}
