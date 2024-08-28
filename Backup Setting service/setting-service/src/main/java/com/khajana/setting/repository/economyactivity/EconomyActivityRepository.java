package com.khajana.setting.repository.economyactivity;

import com.khajana.setting.entity.economyactivity.EconomyActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EconomyActivityRepository extends JpaRepository<EconomyActivity, Long> {
    Optional<EconomyActivity> findEconomyActivityById(Long id);

    boolean existsByEconomicActivityName(String economicActivityName);

    boolean existsByEconomicActivityNameAndIdNot(String economicActivityName, Long id);
}
