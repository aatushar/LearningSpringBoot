package com.khajana.setting.service.item;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.item.ItemSubGroupRequestDto;
import com.khajana.setting.dto.item.ItemSubGroupResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.hscode.HsCode;
import com.khajana.setting.entity.inventorymethod.InventoryMethod;
import com.khajana.setting.entity.item.ItemGroup;
import com.khajana.setting.entity.item.ItemSubGroup;
import com.khajana.setting.entity.item.ItemSubGroupHsCodeMapping;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.hscode.HsCodeRepository;
import com.khajana.setting.repository.inventorymethod.InventoryMethodRepo;
import com.khajana.setting.repository.item.ItemGroupRepository;
import com.khajana.setting.repository.item.ItemSubGroupRepository;
import com.khajana.setting.repository.item.ItemSubGrouphsCodeMappingRepository;
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
public class ItemSubGroupService {

    @Autowired
    ItemSubGroupRepository itemSubGroupRepository;

    @Autowired
    ItemGroupRepository itemGroupRepository;

    @Autowired
    InventoryMethodRepo inventoryMethodRepo;

    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    @Autowired
    HsCodeRepository hsCodeRepository;

    @Autowired
    ItemSubGrouphsCodeMappingRepository itemSubGrouphsCodeMappingRepository;

    public SimplePage<ItemSubGroupResponseDto> findAllItemSubGroups(String filter, Pageable pageable) {
        Page<ItemSubGroup> page;
        if (StringUtils.isNotEmpty(filter)) {

            page = itemSubGroupRepository.findAllItemSubGroupByItmSubGrpNameContaining(filter, pageable);
        } else {

            page = itemSubGroupRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public ItemSubGroupResponseDto findItemSubGroupById(Long id) {

        ItemSubGroup newEntity = itemSubGroupRepository.findItemSubGroupById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionItemSubGroup", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addItemSubGroup(ItemSubGroupRequestDto dto) {
        // Read user id from JWT Token
        try {
            ItemSubGroup newEntity = itemSubGroupRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateItemSubGroup(Long id, ItemSubGroupRequestDto dto) {
        // Read user id from JWT Token
        try {
            ItemSubGroup newEntity = itemSubGroupRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.itemSubGroupDropdown();
        return result;
    }

    public void deleteItemSubGroup(Long id) {
        itemSubGroupRepository.deleteItemSubGroupById(id);
    }

    private ItemSubGroup transferToEntity(Long id, ItemSubGroupRequestDto dto) {
        ItemSubGroup itemSubGroup = new ItemSubGroup();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            itemSubGroup.setId(id);

            itemSubGroup.setItmGrpId(dto.getItmGrpId());
            itemSubGroup.setItmSubGrpName(dto.getItmSubGrpName());
            itemSubGroup.setItmSubGrpNameBn(dto.getItmSubGrpNameBn());
            itemSubGroup.setItmSubGrpPrefix(dto.getItmSubGrpPrefix());
            itemSubGroup.setInvMethodId(dto.getInvMethodId());
            itemSubGroup.setSeqNo(dto.getSeqNo());
            itemSubGroup.setActive(dto.getActive());
            itemSubGroup.setUpdatedAt(new Date());
            itemSubGroup.setUpdatedBy(userData.getId());

            ItemSubGroup itemSubGroup2 = itemSubGroupRepository.save(itemSubGroup);

            ItemSubGroupHsCodeMapping itemSubGroupHsCodeMapping = itemSubGrouphsCodeMappingRepository
                    .findByItmSubGrpId(itemSubGroup2.getId()).orElse(null);
            itemSubGroupHsCodeMapping.setId(itemSubGroupHsCodeMapping.getId());
            itemSubGroupHsCodeMapping.setHsCodeId(dto.getHsCodeId());
            itemSubGroupHsCodeMapping.setItmSubGrpId(itemSubGroup2.getId());
            itemSubGroupHsCodeMapping.setUpdatedAt(new Date());
            itemSubGroupHsCodeMapping.setUpdatedBy(userData.getId());

            itemSubGrouphsCodeMappingRepository.save(itemSubGroupHsCodeMapping);

            return itemSubGroup;
        } else {

            itemSubGroup.setItmGrpId(dto.getItmGrpId());
            itemSubGroup.setItmSubGrpName(dto.getItmSubGrpName());
            itemSubGroup.setItmSubGrpNameBn(dto.getItmSubGrpNameBn());
            itemSubGroup.setItmSubGrpPrefix(dto.getItmSubGrpPrefix());
            itemSubGroup.setInvMethodId(dto.getInvMethodId());

            itemSubGroup.setSeqNo(dto.getSeqNo());
            itemSubGroup.setActive(dto.getActive());
            itemSubGroup.setCreatedAt(new Date());
            itemSubGroup.setCreatedBy(userData.getId());

            ItemSubGroup itemSubGroup2 = itemSubGroupRepository.save(itemSubGroup);

            ItemSubGroupHsCodeMapping itemSubGroupHsCodeMapping = new ItemSubGroupHsCodeMapping();
            itemSubGroupHsCodeMapping.setHsCodeId(dto.getHsCodeId());
            itemSubGroupHsCodeMapping.setItmSubGrpId(itemSubGroup2.getId());
            itemSubGroupHsCodeMapping.setCreatedAt(new Date());
            itemSubGroupHsCodeMapping.setCreatedBy(userData.getId());

            itemSubGrouphsCodeMappingRepository.save(itemSubGroupHsCodeMapping);

            return itemSubGroup;
        }
    }

    private ItemSubGroupResponseDto transferToDTO(ItemSubGroup entity) {
        ItemSubGroupResponseDto dto = new ItemSubGroupResponseDto();
        dto.setId(entity.getId());

        dto.setItmGrpId(entity.getItmGrpId());
        if (entity.getItmGrpId() != null) {
            ItemGroup itemGroup = itemGroupRepository.findById(entity.getItmGrpId()).orElse(null);
            dto.setItmGrpName(itemGroup != null ? itemGroup.getItmGrpName() : null);
        }
        dto.setHsCodeId(entity.getId());
        if (entity.getItemSubGroupHsCodeMappings() != null) {
            HsCode hsCode = hsCodeRepository.findById(entity.getItemSubGroupHsCodeMappings().getHsCodeId())
                    .orElse(null);
            if (hsCode != null) {
                dto.setHsCodeName(hsCode.getHsCode());
            }
        }
        dto.setItmSubGrpName(entity.getItmSubGrpName());
        dto.setItmSubGrpNameBn(entity.getItmSubGrpNameBn());
        dto.setItmSubGrpPrefix(entity.getItmSubGrpPrefix());

        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setInvMethodId(entity.getInvMethodId());
        if (entity.getInvMethodId() != null) {
            InventoryMethod inventoryMethod = inventoryMethodRepo.findById(entity.getInvMethodId()).orElse(null);
            dto.setInvMethodName(inventoryMethod != null ? inventoryMethod.getInvMethodName() : null);
        }

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
}
