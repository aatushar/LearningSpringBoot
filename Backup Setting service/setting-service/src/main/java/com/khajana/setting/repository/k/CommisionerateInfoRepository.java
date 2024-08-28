package com.khajana.setting.repository.k;

import com.khajana.setting.entity.k.CommisionerateInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommisionerateInfoRepository extends JpaRepository<CommisionerateInfo, Long> {
    Optional<CommisionerateInfo> findCommisionerateInfoById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    void deleteCommisionerateInfoById(Long id);
}
