package com.khajana.setting.repository.ordermanagement;

import com.khajana.setting.entity.ordermanagement.exportlcinformation.ExportLcInformationChild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExportLcInformationChildRepository extends JpaRepository<ExportLcInformationChild, Long> {
    Optional<ExportLcInformationChild> findExportLcInformationChildById(Long id);

    void deleteExportLcInformationChildById(Long id);
}
