package com.khajana.setting.service.item;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.item.ItemGroupMasterGroupResponseDto;
import com.khajana.setting.dto.item.ItemMasterGroupFindByIdResponseDto;
import com.khajana.setting.dto.item.ItemMasterGroupRequestDto;
import com.khajana.setting.dto.item.ItemMasterGroupResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.item.ItemMasterGroup;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.entity.product.ProductType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.item.ItemMasterGroupRepository;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;

import io.micrometer.common.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemMasterGroupService {

    @Autowired
    ItemMasterGroupRepository itemMasterGroupRepository;

    @Autowired

    com.khajana.setting.repository.product.ProductTypeRepository productTypeRepository;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<ItemMasterGroupResponseDto> findAllItemMasterGroups(String filter, Pageable pageable) {
        Page<ItemMasterGroup> page;
        if (StringUtils.isNotEmpty(filter)) {

            page = itemMasterGroupRepository.findAllItemMasterGroupByItmMstrGrpNameContaining(filter, pageable);
        } else {

            page = itemMasterGroupRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public ItemMasterGroupFindByIdResponseDto findItemMasterGroupById(Long id) {

        ItemMasterGroup newEntity = itemMasterGroupRepository.findItemMasterGroupById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionItemMasterGroup", "Id", id));

        return transferToFindByIdDTO(newEntity);
    }

    public ApiResponse addItemMasterGroup(ItemMasterGroupRequestDto dto) {
        // Read user id from JWT Token
        try {
            ItemMasterGroup newEntity = itemMasterGroupRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateItemMasterGroup(Long id, ItemMasterGroupRequestDto dto) {
        // Read user id from JWT Token
        try {
            ItemMasterGroup newEntity = itemMasterGroupRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.itemMasterGroupDropdown();
        return result;
    }

    public void deleteItemMasterGroup(Long id) {
        itemMasterGroupRepository.deleteItemMasterGroupById(id);
    }

    private ItemMasterGroup transferToEntity(Long id, ItemMasterGroupRequestDto dto) {
        ItemMasterGroup itemMasterGroup = new ItemMasterGroup();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            itemMasterGroup.setId(id);

            itemMasterGroup.setProdTypeId(dto.getProdTypeId());
            itemMasterGroup.setItmMstrGrpName(dto.getItmMstrGrpName());
            itemMasterGroup.setItmMstrGrpNameBn(dto.getItmMstrGrpNameBn());
            itemMasterGroup.setItmMstrGrpPrefix(dto.getItmMstrGrpPrefix());
            itemMasterGroup.setItemMstrGrpDes(dto.getItemMstrGrpDes());
            itemMasterGroup.setItemMstrGrpDesBn(dto.getItemMstrGrpDesBn());
            itemMasterGroup.setSeqNo(dto.getSeqNo());
            itemMasterGroup.setActive(dto.getActive());
            itemMasterGroup.setUpdatedAt(new Date());
            itemMasterGroup.setUpdatedBy(userData.getId());

            return itemMasterGroup;
        } else {

            itemMasterGroup.setProdTypeId(dto.getProdTypeId());
            itemMasterGroup.setItmMstrGrpName(dto.getItmMstrGrpName());
            itemMasterGroup.setItmMstrGrpNameBn(dto.getItmMstrGrpNameBn());
            itemMasterGroup.setItmMstrGrpPrefix(dto.getItmMstrGrpPrefix());
            itemMasterGroup.setItemMstrGrpDes(dto.getItemMstrGrpDes());
            itemMasterGroup.setItemMstrGrpDesBn(dto.getItemMstrGrpDesBn());
            itemMasterGroup.setSeqNo(dto.getSeqNo());
            itemMasterGroup.setActive(dto.getActive());
            itemMasterGroup.setCreatedAt(new Date());
            itemMasterGroup.setCreatedBy(userData.getId());

            return itemMasterGroup;
        }
    }

    private ItemMasterGroupResponseDto transferToDTO(ItemMasterGroup entity) {
        ItemMasterGroupResponseDto dto = new ItemMasterGroupResponseDto();
        dto.setId(entity.getId());

        dto.setProdTypeId(entity.getProdTypeId());
        if (entity.getProdTypeId() != null) {
            ProductType productType = productTypeRepository.findById(entity.getProdTypeId()).orElse(null);
            dto.setProdTypeName(productType != null ? productType.getName() : null);
        }
        dto.setItmMstrGrpName(entity.getItmMstrGrpName());
        dto.setItmMstrGrpNameBn(entity.getItmMstrGrpNameBn());
        dto.setItmMstrGrpPrefix(entity.getItmMstrGrpPrefix());
        dto.setItemMstrGrpDes(entity.getItemMstrGrpDes());
        dto.setItemMstrGrpDesBn(entity.getItemMstrGrpDesBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setActive(entity.getActive());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (entity.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(entity.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }
        return dto;
    }

    private ItemMasterGroupFindByIdResponseDto transferToFindByIdDTO(ItemMasterGroup entity) {
        ItemMasterGroupFindByIdResponseDto dto = new ItemMasterGroupFindByIdResponseDto();
        dto.setId(entity.getId());

        dto.setProdTypeId(entity.getProdTypeId());
        if (entity.getProdTypeId() != null) {
            ProductType productType = productTypeRepository.findById(entity.getProdTypeId()).orElse(null);
            dto.setProdTypeName(productType != null ? productType.getName() : null);
        }
        dto.setItmMstrGrpName(entity.getItmMstrGrpName());
        dto.setItmMstrGrpNameBn(entity.getItmMstrGrpNameBn());
        dto.setItmMstrGrpPrefix(entity.getItmMstrGrpPrefix());
        dto.setItemMstrGrpDes(entity.getItemMstrGrpDes());
        dto.setItemMstrGrpDesBn(entity.getItemMstrGrpDesBn());

        dto.setActive(entity.getActive());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (entity.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(entity.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }
        List<ItemGroupMasterGroupResponseDto> itemMasterGroups = entity.getItemGroups().stream()
                .map(mstrGrp -> {
                    ItemGroupMasterGroupResponseDto itemMasterGroup = new ItemGroupMasterGroupResponseDto();
                    itemMasterGroup.setId(mstrGrp.getId());
                    itemMasterGroup.setItmGrpName(mstrGrp.getItmGrpName());
                    itemMasterGroup.setItmGrpNameBn(mstrGrp.getItmGrpNameBn());
                    return itemMasterGroup;
                }).collect(Collectors.toList());
        dto.setItemGroups(itemMasterGroups);
        return dto;
    }
}
