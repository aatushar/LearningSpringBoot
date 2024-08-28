package com.khajana.setting.repository.vatratereference;

import com.khajana.setting.entity.vatratereference.VatRateReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VatRateReferenceRepository extends JpaRepository<VatRateReference, Long> {
    Optional<VatRateReference> findVatRateReferenceById(Long id);

    boolean existsByVatRateRefName(String trnsTypeName);

    boolean existsByVatRateRefNameAndIdNot(String trnsTypeName, Long id);

    void deleteVatRateReferenceById(Long id);
}
