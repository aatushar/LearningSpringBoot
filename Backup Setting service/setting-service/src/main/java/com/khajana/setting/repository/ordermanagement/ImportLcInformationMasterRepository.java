package com.khajana.setting.repository.ordermanagement;

import com.khajana.setting.entity.ordermanagement.importlcinformation.ImportLcInformationMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImportLcInformationMasterRepository extends JpaRepository<ImportLcInformationMaster, Long> {
    Optional<ImportLcInformationMaster> findImportLcInformationMasterById(Long id);

    boolean existsByImportLcNo(String importLcNo);

    void deleteImportLcInformationMasterById(Long id);
}
