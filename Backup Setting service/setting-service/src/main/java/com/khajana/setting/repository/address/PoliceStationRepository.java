package com.khajana.setting.repository.address;

import com.khajana.setting.entity.address.PoliceStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PoliceStationRepository extends JpaRepository<PoliceStation, Long> {
    Optional<PoliceStation> findPoliceStationById(Long id);

    void deletePoliceStationById(Long id);
}
