package com.khajana.setting.repository.inventorymethod;

import com.khajana.setting.entity.inventorymethod.InventoryMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryMethodRepo extends JpaRepository<InventoryMethod, Long> {
    Optional<InventoryMethod> findInventoryMethodById(Long id);

    boolean existsByInvMethodName(String name);

    boolean existsByInvMethodNameAndIdNot(String name, Long id);
}
