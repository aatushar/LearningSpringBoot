package com.khajana.setting.repository.adjt;

import com.khajana.setting.entity.adjt.OtherAdjt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtherAdjtRepository extends JpaRepository<OtherAdjt, Long> {
    Optional<OtherAdjt> findOtherAdjtById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    void deleteOtherAdjtById(Long id);
}
