package com.khajana.setting.repository.businessinformation;

import com.khajana.setting.entity.businessinformation.BusinessInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessInfoRepo extends JpaRepository<BusinessInformation, Long> {
    Optional<BusinessInformation> findBusinessById(Long id);

    boolean existsByBusinessInfoName(String businessInfo);

    boolean existsByBusinessInfoNameAndIdNot(String businessInfo, Long id);

    void deleteBusinessById(Long id);
}
