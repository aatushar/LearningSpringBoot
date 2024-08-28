package com.khajana.setting.repository.item;

import com.khajana.setting.entity.item.ItemStoreMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ItemStoreMappingRepository extends JpaRepository<ItemStoreMapping, Long> {

    Optional<ItemStoreMapping> findItemStoreMappingById(Long id);

    Optional<ItemStoreMapping> findByItemInfoId(Long itemInfoId);

    void deleteItemStoreMappingById(Long id);
}
