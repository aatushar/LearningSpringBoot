package com.khajana.setting.repository.adjt;

import com.khajana.setting.entity.adjt.OtherAdjtType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtherAdjtTypeRepository extends JpaRepository<OtherAdjtType, Long> {
    Optional<OtherAdjtType> findOtherAdjtTypeById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    void deleteOtherAdjtTypeById(Long id);
}
