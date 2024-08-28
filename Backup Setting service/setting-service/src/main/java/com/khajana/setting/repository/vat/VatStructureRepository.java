package com.khajana.setting.repository.vat;

import com.khajana.setting.entity.vat.VatStructure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VatStructureRepository extends JpaRepository<VatStructure, Long> {
    Optional<VatStructure> findVatStructureById(Long id);
}
