package com.khajana.setting.repository.vatratetype;

import com.khajana.setting.entity.vatratetype.VatRateType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VatRateTypeRepository extends JpaRepository<VatRateType, Long> {
    Optional<VatRateType> findVatRateTypeById(Long id);

    boolean existsByVatRateTypeName(String vatRateType);

    boolean existsByVatRateTypeNameAndIdNot(String vatRateType, Long id);

    void deleteVatRateTypeById(Long id);
}
