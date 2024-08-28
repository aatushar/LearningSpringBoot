package com.khajana.setting.repository.port;

import com.khajana.setting.entity.port.PortInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortInfoRepository extends JpaRepository<PortInfo, Long> {
    Optional<PortInfo> findPortInfoById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    void deletePortInfoById(Long id);
}
