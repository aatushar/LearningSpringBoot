package com.khajana.setting.repository.ordermanagement;

import com.khajana.setting.entity.ordermanagement.exportlcinformation.ExportLcInformationMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExportLcInformationMasterRepository extends JpaRepository<ExportLcInformationMaster, Long> {
    Optional<ExportLcInformationMaster> findExportLcInformationMasterById(Long id);

    boolean existsByLcNo(String lcNo);

    void deleteExportLcInformationMasterById(Long id);
}
