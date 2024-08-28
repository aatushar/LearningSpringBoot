package com.khajana.setting.repository.vat;

import com.khajana.setting.entity.vat.VatRateStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VatRateStageRepository extends JpaRepository<VatRateStage, Long> {
    Optional<VatRateStage> findVatRateStageById(Long id);

    boolean existsByName(String trnsTypeName);

    boolean existsByNameAndIdNot(String trnsTypeName, Long id);
}
