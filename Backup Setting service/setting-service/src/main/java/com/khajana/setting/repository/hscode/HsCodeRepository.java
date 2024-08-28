package com.khajana.setting.repository.hscode;

import com.khajana.setting.entity.hscode.HsCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HsCodeRepository extends JpaRepository<HsCode, Long> {
    Optional<HsCode> findHsCodeById(Long id);

    boolean existsByHsCode(String hsCode);

    boolean existsByHsCodeAndIdNot(String hsCode, Long id);

    void deleteHsCodeById(Long id);
}
