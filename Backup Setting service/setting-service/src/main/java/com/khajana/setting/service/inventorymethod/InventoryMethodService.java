package com.khajana.setting.service.inventorymethod;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.inventorymethod.InventoryMethodRequestDto;
import com.khajana.setting.dto.inventorymethod.InventoryMethodResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.inventorymethod.InventoryMethod;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.inventorymethod.InventoryMethodRepo;
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
public class InventoryMethodService {
    @Autowired
    InventoryMethodRepo inventoryMethodRepo;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<InventoryMethodResponseDto> findAllInventoryMethods(Pageable pageable) {

        Page<InventoryMethod> page = inventoryMethodRepo.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public List<InventoryMethodResponseDto> getDropDown() {
        List<InventoryMethod> page = inventoryMethodRepo.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public InventoryMethodResponseDto findInventoryMethodById(Long id) {
        InventoryMethod inventoryMethod = inventoryMethodRepo.findInventoryMethodById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(inventoryMethod);
    }

    public ApiResponse addInventoryMethod(InventoryMethodRequestDto dto) {
        try {
            boolean typeExists = inventoryMethodRepo.existsByInvMethodName(dto.getInvMethodName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Inventory Method Name not allowed", "error");
            }
            InventoryMethod newEntity = inventoryMethodRepo.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateInventoryMethod(Long id, InventoryMethodRequestDto dto) {
        try {
            boolean nameExistsForOtherId = inventoryMethodRepo.existsByInvMethodNameAndIdNot(dto.getInvMethodName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Inventory Method Name not allowed", "error");
            }
            InventoryMethod newEntity = inventoryMethodRepo.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private InventoryMethod transferToEntity(Long id, InventoryMethodRequestDto dto) {
        InventoryMethod inventoryMethod = new InventoryMethod();

        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            InventoryMethod inventoryMethods = inventoryMethodRepo.findInventoryMethodById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            inventoryMethod.setId(inventoryMethods.getId());
            inventoryMethod.setInvMethodName(dto.getInvMethodName());
            inventoryMethod.setInvMethodNameBn(dto.getInvMethodNameBn());
            inventoryMethod.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            inventoryMethod.setActive(dto.getActive());
            inventoryMethod.setUpdatedAt(new Date());
            inventoryMethod.setUpdatedBy(userData.getId());

            return inventoryMethod;
        } else {
            inventoryMethod.setInvMethodName(dto.getInvMethodName());
            inventoryMethod.setInvMethodNameBn(dto.getInvMethodNameBn());
            inventoryMethod.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            inventoryMethod.setActive(dto.getActive());
            inventoryMethod.setCreatedAt(new Date());
            inventoryMethod.setCreatedBy(userData.getId());
            return inventoryMethod;
        }
    }

    private InventoryMethodResponseDto transferToDTO(InventoryMethod inventoryMethod) {
        InventoryMethodResponseDto dto = new InventoryMethodResponseDto();
        dto.setId(inventoryMethod.getId());
        dto.setInvMethodName(inventoryMethod.getInvMethodName());
        dto.setInvMethodNameBn(inventoryMethod.getInvMethodNameBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(inventoryMethod.getSeqNo()));
        dto.setActive(inventoryMethod.getActive());
        dto.setCreatedAt(
                DateUtil.convertDateToString(inventoryMethod.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(
                DateUtil.convertDateToString(inventoryMethod.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (inventoryMethod.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(inventoryMethod.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (inventoryMethod.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(inventoryMethod.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }
        return dto;
    }
}
