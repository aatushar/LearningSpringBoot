package com.khajana.setting.repository.companystore;

import com.khajana.setting.entity.companystore.UserCompanyStoreMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCompanyStoreMappingRepository extends JpaRepository<UserCompanyStoreMapping, Long> {
    Optional<UserCompanyStoreMapping> findUserCompanyStoreMappingById(Long id);

    void deleteUserCompanyStoreMappingById(Long id);
}
