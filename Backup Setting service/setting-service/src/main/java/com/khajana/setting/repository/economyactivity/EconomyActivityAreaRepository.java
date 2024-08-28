package com.khajana.setting.repository.economyactivity;

import com.khajana.setting.entity.economyactivity.EconomicActivityAreaEntity2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EconomyActivityAreaRepository extends JpaRepository<EconomicActivityAreaEntity2, Long> {
    Optional<EconomicActivityAreaEntity2> findEconomyActivityAreaById(Long id);

    boolean existsByEconomicActivityAreaName(String economicActivityName);

    boolean existsByEconomicActivityAreaNameAndIdNot(String economicActivityName, Long id);

}
