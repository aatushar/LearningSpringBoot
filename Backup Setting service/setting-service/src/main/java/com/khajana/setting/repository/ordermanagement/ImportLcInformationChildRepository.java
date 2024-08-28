package com.khajana.setting.repository.ordermanagement;

import com.khajana.setting.entity.ordermanagement.importlcinformation.ImportLcInformationChild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImportLcInformationChildRepository extends JpaRepository<ImportLcInformationChild, Long> {
    Optional<ImportLcInformationChild> findImportLcInformationChildById(Long id);

    void deleteImportLcInformationChildById(Long id);
}
