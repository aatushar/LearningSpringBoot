package com.khajana.setting.repository.uom;

import com.khajana.setting.entity.uom.UomSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UomSetRepository extends JpaRepository<UomSet, Long> {
    Optional<UomSet> findUomSetById(Long id);

    boolean existsByUomSet(String uomSet);

    boolean existsByUomSetAndIdNot(String uomSet, Long id);
}
