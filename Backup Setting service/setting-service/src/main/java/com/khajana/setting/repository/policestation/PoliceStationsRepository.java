package com.khajana.setting.repository.policestation;

import com.khajana.setting.entity.policestation.PoliceStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PoliceStationsRepository extends JpaRepository<PoliceStationEntity, Long> {
    Optional<PoliceStationEntity> findPolicestationById(Long id);

    boolean existsByPsName(String name);

    boolean existsByPsNameAndIdNot(String name, Long id);
}
