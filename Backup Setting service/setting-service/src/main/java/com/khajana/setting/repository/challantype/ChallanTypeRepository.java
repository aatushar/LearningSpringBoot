package com.khajana.setting.repository.challantype;

import com.khajana.setting.entity.challantype.ChallanTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChallanTypeRepository extends JpaRepository<ChallanTypeEntity, Long> {
    Optional<ChallanTypeEntity> findChallanTypeById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
