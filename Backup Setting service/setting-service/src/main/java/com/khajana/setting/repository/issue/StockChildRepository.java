package com.khajana.setting.repository.issue;

import com.khajana.setting.entity.issue.StockChild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockChildRepository extends JpaRepository<StockChild, Long> {
    Optional<StockChild> findStockChildById(Long id);


    void deleteStockChildById(Long id);
}
