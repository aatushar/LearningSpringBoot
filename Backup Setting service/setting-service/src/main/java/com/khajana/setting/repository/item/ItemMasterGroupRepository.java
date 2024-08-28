package com.khajana.setting.repository.item;

import com.khajana.setting.entity.item.ItemMasterGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ItemMasterGroupRepository extends JpaRepository<ItemMasterGroup, Long> {
    Optional<ItemMasterGroup> findItemMasterGroupById(Long id);

    Page<ItemMasterGroup> findAllItemMasterGroupByItmMstrGrpNameContaining(String itmMstrGrpName, Pageable pageable);

    void deleteItemMasterGroupById(Long id);
}
