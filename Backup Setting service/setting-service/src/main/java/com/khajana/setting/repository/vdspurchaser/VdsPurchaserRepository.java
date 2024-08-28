package com.khajana.setting.repository.vdspurchaser;

import com.khajana.setting.entity.vdspurchaser.VdsPurchaserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VdsPurchaserRepository extends JpaRepository<VdsPurchaserMaster, Long> {
    Optional<VdsPurchaserMaster> findVdsPurchaserById(Long id);

    @Query("SELECT vp FROM VdsPurchaserMaster vp " +
            "WHERE vp.tcMasterId IS NULL AND vp.isPaid = false")
    List<VdsPurchaserMaster> findByIsPaidFalse();

    void deleteVdsPurchaserById(Long id);
}
