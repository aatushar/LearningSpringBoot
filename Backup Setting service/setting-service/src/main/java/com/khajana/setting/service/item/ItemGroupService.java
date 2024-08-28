package com.khajana.setting.service.item;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.item.ItemGroupFindByIdResponseDto;
import com.khajana.setting.dto.item.ItemGroupRequestDto;
import com.khajana.setting.dto.item.ItemGroupResponseDto;
import com.khajana.setting.dto.item.ItemSubGroupGroupResponseDto;
import com.khajana.setting.dto.uom.UomFromGroupResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.item.ItemGroup;
import com.khajana.setting.entity.item.ItemMasterGroup;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.entity.product.ProductType;
import com.khajana.setting.entity.uom.UomSet;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.item.ItemGroupRepository;
import com.khajana.setting.repository.item.ItemMasterGroupRepository;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.repository.product.ProductTypeRepository;
import com.khajana.setting.repository.uom.UomSetRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.ConstantValue;
import com.khajana.setting.utils.ContextUser;
import com.khajana.setting.utils.DateUtil;
import com.khajana.setting.utils.SimplePage;

import io.micrometer.common.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemGroupService {

    @Autowired
    ItemGroupRepository itemGroupRepository;

    @Autowired
    ItemMasterGroupRepository itemMasterGroupRepository;

    @Autowired
    UomSetRepository uomSetRepository;

    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    public SimplePage<ItemGroupResponseDto> findAllItemGroups(String filter, Pageable pageable) {
        Page<ItemGroup> page;
        if (StringUtils.isNotEmpty(filter)) {

            page = itemGroupRepository.findAllItemGroupByItmGrpNameContaining(filter, pageable);
        } else {

            page = itemGroupRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public ItemGroupFindByIdResponseDto findItemGroupById(Long id) {

        ItemGroup newEntity = itemGroupRepository.findItemGroupById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionItemGroup", "Id", id));

        return transferToFindByIdDTO(newEntity);
    }

    public ApiResponse addItemGroup(ItemGroupRequestDto dto) {
        // Read user id from JWT Token
        try {
            ItemGroup newEntity = itemGroupRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateItemGroup(Long id, ItemGroupRequestDto dto) {
        // Read user id from JWT Token
        try {
            ItemGroup newEntity = itemGroupRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.itemGroupDropdown();
        return result;
    }

    public void deleteItemGroup(Long id) {
        itemGroupRepository.deleteItemGroupById(id);
    }

    private ItemGroup transferToEntity(Long id, ItemGroupRequestDto dto) {
        ItemGroup itemGroup = new ItemGroup();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            itemGroup.setId(id);

            itemGroup.setItmMstrGrpId(dto.getItmMstrGrpId());
            itemGroup.setItmGrpPrefix(dto.getItmGrpPrefix());
            itemGroup.setUomSetId(dto.getUomSetId());
            itemGroup.setItmGrpName(dto.getItmGrpName());
            itemGroup.setItmGrpNameBn(dto.getItmGrpNameBn());
            itemGroup.setItemGrpDes(dto.getItemGrpDes());
            itemGroup.setItemGrpDesBn(dto.getItemGrpDesBn());

            itemGroup.setActive(dto.getActive());
            itemGroup.setUpdatedAt(new Date());
            itemGroup.setUpdatedBy(userData.getId());

            return itemGroup;
        } else {

            itemGroup.setItmMstrGrpId(dto.getItmMstrGrpId());
            itemGroup.setItmGrpPrefix(dto.getItmGrpPrefix());
            itemGroup.setUomSetId(dto.getUomSetId());
            itemGroup.setItmGrpName(dto.getItmGrpName());
            itemGroup.setItmGrpNameBn(dto.getItmGrpNameBn());
            itemGroup.setItemGrpDes(dto.getItemGrpDes());
            itemGroup.setItemGrpDesBn(dto.getItemGrpDesBn());

            itemGroup.setActive(dto.getActive());
            itemGroup.setCreatedAt(new Date());
            itemGroup.setCreatedBy(userData.getId());

            return itemGroup;
        }
    }

    private ItemGroupResponseDto transferToDTO(ItemGroup entity) {
        ItemGroupResponseDto dto = new ItemGroupResponseDto();
        dto.setId(entity.getId());

        if (entity.getItmMstrGrpId() != null) {
            ItemMasterGroup itemMasterGroup = itemMasterGroupRepository.findById(entity.getItmMstrGrpId()).orElse(null);
            dto.setItmMstrGrpName(itemMasterGroup != null ? itemMasterGroup.getItmMstrGrpName() : null);
            ProductType productType = productTypeRepository.findById(itemMasterGroup.getProdTypeId()).orElse(null);
            dto.setProdTypeId(productType.getId());
            dto.setProdTypeName(productType.getName());
        }
        dto.setItmMstrGrpId(entity.getItmMstrGrpId());
        dto.setItmGrpPrefix(entity.getItmGrpPrefix());
        if (entity.getUomSetId() != null) {
            UomSet uomSet = uomSetRepository.findById(entity.getUomSetId()).orElse(null);
            dto.setUomSetName(uomSet != null ? uomSet.getUomSet() : null);
        }
        dto.setUomSetId(entity.getUomSetId());
        dto.setItmGrpName(entity.getItmGrpName());
        dto.setItmGrpNameBn(entity.getItmGrpNameBn());
        dto.setItemGrpDes(entity.getItemGrpDes());
        dto.setItemGrpDesBn(entity.getItemGrpDesBn());

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

    private ItemGroupFindByIdResponseDto transferToFindByIdDTO(ItemGroup entity) {
        ItemGroupFindByIdResponseDto dto = new ItemGroupFindByIdResponseDto();
        dto.setId(entity.getId());

        if (entity.getItmMstrGrpId() != null) {
            ItemMasterGroup itemMasterGroup = itemMasterGroupRepository.findById(entity.getItmMstrGrpId()).orElse(null);
            dto.setItmMstrGrpName(itemMasterGroup != null ? itemMasterGroup.getItmMstrGrpName() : null);
        }
        dto.setItmMstrGrpId(entity.getItmMstrGrpId());
        dto.setItmGrpPrefix(entity.getItmGrpPrefix());
        if (entity.getUomSetId() != null) {
            UomSet uomSet = uomSetRepository.findById(entity.getUomSetId()).orElse(null);
            dto.setUomSetName(uomSet != null ? uomSet.getUomSet() : null);
        }
        dto.setUomSetId(entity.getUomSetId());
        dto.setItmGrpName(entity.getItmGrpName());
        dto.setItmGrpNameBn(entity.getItmGrpNameBn());
        dto.setItemGrpDes(entity.getItemGrpDes());
        dto.setItemGrpDesBn(entity.getItemGrpDesBn());

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
        List<ItemSubGroupGroupResponseDto> itemSubGroups = entity.getItemSubGroup().stream().map(subGrp -> {
            ItemSubGroupGroupResponseDto itemSubGroup = new ItemSubGroupGroupResponseDto();
            itemSubGroup.setId(subGrp.getId());
            itemSubGroup.setItmSubGrpName(subGrp.getItmSubGrpName());
            itemSubGroup.setItmSubGrpNameBn(subGrp.getItmSubGrpNameBn());
            return itemSubGroup;
        }).collect(Collectors.toList());
        dto.setItemSubGroups(itemSubGroups);
        List<UomFromGroupResponseDto> uoms = entity.getUomSet().getUoms().stream().map(uom -> {
            UomFromGroupResponseDto uomDto = new UomFromGroupResponseDto();
            uomDto.setId(uom.getId());
            uomDto.setUomShortCode(uom.getUomShortCode());
            uomDto.setUomDesc(uom.getUomDesc());
            return uomDto;
        }).collect(Collectors.toList());
        dto.setUoms(uoms);
        return dto;
    }
}
