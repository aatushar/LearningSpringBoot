package com.khajana.setting.repository.transactiontype;

import com.khajana.setting.entity.transactiontype.ImportLcPurpose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImportLcPurposeRepository extends JpaRepository<ImportLcPurpose, Long> {
    Optional<ImportLcPurpose> findImportLcPurposeById(Long id);

    boolean existsByPurpose(String name);

    boolean existsByPurposeAndIdNot(String name, Long id);
}
