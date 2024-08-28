package com.khajana.setting.repository.issue;

import com.khajana.setting.entity.issue.StockMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockMasterRepository extends JpaRepository<StockMaster, Long> {
    Optional<StockMaster> findStockMasterById(Long id);


    void deleteStockMasterById(Long id);
}
