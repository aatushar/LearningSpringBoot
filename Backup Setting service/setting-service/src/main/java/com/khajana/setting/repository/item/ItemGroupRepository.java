package com.khajana.setting.repository.item;

import com.khajana.setting.entity.item.ItemGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ItemGroupRepository extends JpaRepository<ItemGroup, Long> {
    Optional<ItemGroup> findItemGroupById(Long id);

    Page<ItemGroup> findAllItemGroupByItmGrpNameContaining(String itmGrpName, Pageable pageable);

    void deleteItemGroupById(Long id);
}
