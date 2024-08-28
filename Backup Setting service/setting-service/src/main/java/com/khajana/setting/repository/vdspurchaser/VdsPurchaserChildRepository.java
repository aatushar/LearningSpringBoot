package com.khajana.setting.repository.vdspurchaser;

import com.khajana.setting.entity.vdspurchaser.VdsPurchaserChild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VdsPurchaserChildRepository extends JpaRepository<VdsPurchaserChild, Long> {
    Optional<VdsPurchaserChild> findVdsPurchaserById(Long id);

    void deleteVdsPurchaserById(Long id);
}
