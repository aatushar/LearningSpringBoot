package com.khajana.setting.repository.menu;

import com.khajana.setting.entity.menu.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    Optional<MenuEntity> findVatRegSourceById(Long id);
}
