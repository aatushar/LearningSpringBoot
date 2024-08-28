package com.khajana.setting.repository.vat;

import com.khajana.setting.entity.vat.VatRebateType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VatRebateTypeRepo extends JpaRepository<VatRebateType, Long> {
    Optional<VatRebateType> findVatRebateTypeById(Long id);

    List<VatRebateType> findByVatRebateNameIgnoreCaseContaining(String name);


    boolean existsByVatRebateName(String vatRebateName);

    boolean existsByVatRebateNameAndIdNot(String vatRebateName, Long id);
}
