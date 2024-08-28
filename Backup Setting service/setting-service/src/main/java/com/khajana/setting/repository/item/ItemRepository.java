package com.khajana.setting.repository.item;

import com.khajana.setting.entity.item.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemById(Long id);

    // @Query("SELECT * FROM Item as itm WHERE itm.is_rebateable = :isRebateable")
    List<Item> findByIsRebateable(boolean isRebateable);

    Page<Item> findAllItemByDisplayItmNameContaining(String displayItmName, Pageable pageable);


    void deleteItemById(Long id);
}
