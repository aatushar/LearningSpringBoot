package com.khajana.setting.repository.address;

import com.khajana.setting.entity.address.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<District> findDistrictById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    void deleteDistrictById(Long id);
}
