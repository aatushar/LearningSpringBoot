package com.khajana.setting.repository.vehicletype;

import com.khajana.setting.entity.vehicletype.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    Optional<VehicleType> findVehicleTypeById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    void deleteVehicleTypeById(Long id);
}
