package com.khajana.setting.repository.itemcatforretail;

import com.khajana.setting.entity.itemcatforretail.ItemCatForRetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemCatForRetailRepo extends JpaRepository<ItemCatForRetailEntity, Long> {
    Optional<ItemCatForRetailEntity> findItemCatById(Long id);

    boolean existsByitemRetailNameE(String itemCatRetailName);

    //
    boolean existsByitemRetailNameEAndIdNot(String itemCatRetailName, Long id);
}
