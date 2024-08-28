package com.khajana.setting.service.menu;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.menudto.MenuDto;
import com.khajana.setting.entity.menu.MenuEntity;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.menu.MenuRepository;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    MenuRepository menuRepository;

    /*
     * public Page<MenuDto> findAllMenus(Pageable pageable) { Page<Menu> page =
     * menuRepository.findAll(pageable); return page.map(this::transferToDTO); }
     */

    public SimplePage<MenuDto> findAllMenus(Pageable pageable) {

        Page<MenuEntity> page = menuRepository.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public MenuDto findMenuById(Long id) {

        MenuEntity newEntity = menuRepository.findVatRegSourceById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

//    public MenuDto addMenu(MenuDto dto) {
//        Menu newEntity = menuRepository.save(transferToEntity(dto));
//        return  transferToDTO(newEntity);
//    }

    public ApiResponse addMenu(MenuDto dto) {
        // Read user id from JWT Token
        try {

            if (dto.getMenuName() == null || dto.getMenuName().isEmpty()) {
                return new ApiResponse(404, "MenuName is empty!", "");
            }
            if (dto.getMenuNameBn() == null || dto.getMenuNameBn().isEmpty()) {
                return new ApiResponse(404, "MenuNameBn is empty!", "");
            }

            MenuEntity newEntity = menuRepository.save(transferToEntity(dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public MenuDto updateMenu(MenuDto dto) {
        MenuEntity newEntity = menuRepository.save(transferToEntity(dto));
        return transferToDTO(newEntity);
    }

    MenuEntity transferToEntity(MenuDto dto) {
        MenuEntity menu = new MenuEntity();
        menu.setId(dto.getId());
        menu.setMenuName(dto.getMenuName());
        menu.setMenuNameBn(dto.getMenuNameBn());
        menu.setEIsActive(dto.getEIsActive());
        menu.setIsChild(dto.getIsChild());
        menu.setSubMenuId(dto.getSubMenuId());
        menu.setIconName(dto.getIconName());
        menu.setRouteId(dto.getRouteId());
        menu.setRouteName(dto.getRouteName());
        menu.setSeqNumber(dto.getSeqNumber());
        menu.setIsNewtab(dto.getIsNewtab());
        menu.setCreatedAt(dto.getCreatedAt());
        menu.setUpdatedAt(dto.getUpdatedAt());

        // Add the following lines
        menu.setCreatedBy(dto.getCreatedBy());
        menu.setUpdatedBy(dto.getUpdatedBy());

        return menu;
    }

    MenuDto transferToDTO(MenuEntity entity) {
        MenuDto dto = new MenuDto();
        dto.setId(entity.getId());
        dto.setMenuName(entity.getMenuName());
        dto.setMenuNameBn(entity.getMenuNameBn());
        dto.setEIsActive(entity.getEIsActive());
        dto.setIsChild(entity.getIsChild());
        dto.setSubMenuId(entity.getSubMenuId());
        dto.setIconName(entity.getIconName());
        dto.setRouteId(entity.getRouteId());
        dto.setRouteName(entity.getRouteName());
        dto.setSeqNumber(entity.getSeqNumber());
        dto.setIsNewtab(entity.getIsNewtab());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        // Add the following lines
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());

        return dto;
    }

}
