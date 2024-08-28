package com.khajana.setting.service.itemcatforretail;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.itemcatforretail.ItemCatForRetailRequestDto;
import com.khajana.setting.dto.itemcatforretail.ItemCatForRetailResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.itemcatforretail.ItemCatForRetailEntity;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.itemcatforretail.ItemCatForRetailRepo;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemCatForRetailService {

    @Autowired
    ItemCatForRetailRepo itemCatForRetailRepo;
    @Autowired
    UserCredentialRepository userCredentials;

    public ApiResponse addItemCatRetail(ItemCatForRetailRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = itemCatForRetailRepo.existsByitemRetailNameE(dto.getItemCatRetailName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Item Category for retail not allowed", "error");
            }
            ItemCatForRetailEntity newEntity = itemCatForRetailRepo.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public SimplePage<ItemCatForRetailResponseDto> findAlltemCat(Pageable pageable) {

        Page<ItemCatForRetailEntity> page = itemCatForRetailRepo.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public List<ItemCatForRetailResponseDto> getDropDown() {
        List<ItemCatForRetailEntity> page = itemCatForRetailRepo.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public ApiResponse updatetemCat(Long id, ItemCatForRetailRequestDto dto) {

        // Read user id from JWT Token
        try {
            boolean nameExistsForOtherId = itemCatForRetailRepo.existsByitemRetailNameEAndIdNot(dto.getItemCatRetailName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Item Category for retail not allowed", "error");
            }
            ItemCatForRetailEntity newEntity = itemCatForRetailRepo.save(transferToEntity(id, dto));
            return new ApiResponse(200, "Successfully Updated", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }

    }

    public ItemCatForRetailResponseDto findItemCatTypeById(Long id) {

        ItemCatForRetailEntity newEntity = itemCatForRetailRepo.findItemCatById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

//    public void deleteTransactionSourceType(Long id){
//        repository.deleteSourceTypeById(id);
//    }

    ItemCatForRetailEntity transferToEntity(Long id, ItemCatForRetailRequestDto dto) {
        ItemCatForRetailEntity sourceType = new ItemCatForRetailEntity();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            ItemCatForRetailEntity newEntity = itemCatForRetailRepo.findItemCatById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            sourceType.setId(newEntity.getId());
            sourceType.setItemRetailNameE(dto.getItemCatRetailName());
            sourceType.setItemRetailNameBnE(dto.getItemCatRetailNameBn());
            sourceType.setSeqNoE(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            sourceType.setActive(dto.getActive());
            sourceType.setUpdatedAt(new Date());
            sourceType.setUpdatedBy(userData.getId());
        } else {
            sourceType.setItemRetailNameE(dto.getItemCatRetailName());
            sourceType.setItemRetailNameBnE(dto.getItemCatRetailNameBn());
            sourceType.setSeqNoE(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            sourceType.setActive(dto.getActive());
            sourceType.setCreatedAt(new Date());
            sourceType.setCreatedBy(userData.getId());

        }

        return sourceType;
    }

    ItemCatForRetailResponseDto transferToDTO(ItemCatForRetailEntity entity) {

        ItemCatForRetailResponseDto dto = new ItemCatForRetailResponseDto();
        dto.setId(entity.getId());
        dto.setItemCatRetailName(entity.getItemRetailNameE());
        dto.setItemCatRetailNameBn(entity.getItemRetailNameBnE());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNoE()));
        dto.setActive(entity.isActive());
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
