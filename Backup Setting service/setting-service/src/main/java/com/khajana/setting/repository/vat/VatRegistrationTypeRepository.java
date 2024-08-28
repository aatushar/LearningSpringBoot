package com.khajana.setting.repository.vat;

import com.khajana.setting.entity.vat.VatRegistrationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VatRegistrationTypeRepository extends JpaRepository<VatRegistrationType, Long> {
    Optional<VatRegistrationType> findVatRegistrationTypeById(Long id);

    boolean existsByVatRegistrationName(String vatRegistrationName);

    boolean existsByVatRegistrationNameAndIdNot(String vatRegistrationName, Long id);

    Optional<VatRegistrationType> findByVatRegistrationName(String vatRegistrationName);
}
