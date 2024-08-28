package com.khajana.setting.repository.item;

import com.khajana.setting.entity.item.ItemSubGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ItemSubGroupRepository extends JpaRepository<ItemSubGroup, Long> {
    Optional<ItemSubGroup> findItemSubGroupById(Long id);

    Page<ItemSubGroup> findAllItemSubGroupByItmSubGrpNameContaining(String itmSubGrpName, Pageable pageable);

    void deleteItemSubGroupById(Long id);
}
