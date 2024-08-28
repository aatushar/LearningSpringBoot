package com.khajana.setting.repository.fy;

import com.khajana.setting.entity.fy.FiscalYearInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FiscalYearInfoRepository extends JpaRepository<FiscalYearInfo, Long> {
    boolean existsByFiscalYear(String name);

    boolean existsByFiscalYearAndIdNot(String name, Long id);

    Optional<FiscalYearInfo> findFiscalYearById(Long id);
}
