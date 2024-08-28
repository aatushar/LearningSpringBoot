package com.khajana.setting.service.challantype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.challantype.ChallanTypeRequestDto;
import com.khajana.setting.dto.challantype.ChallanTypeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.challantype.ChallanTypeEntity;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.challantype.ChallanTypeRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class ChallanTypeService {

    @Autowired
    ChallanTypeRepository challanTypeRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    // public List<ChallanTypeDto> findAllChallanTypes() {
    // List<ChallanTypeEntity> entities = challanTypeRepository.findAll();
    // return
    // entities.stream().map(this::transferToDTO).collect(Collectors.toList());
    // }
    public SimplePage<ChallanTypeResponseDto> findAllChallanTypes(Pageable pageable) {

        Page<ChallanTypeEntity> page = challanTypeRepository.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public ChallanTypeResponseDto findChallanTypeById(Long id) {

        ChallanTypeEntity newEntity = challanTypeRepository.findChallanTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addChallanTypes(ChallanTypeRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = challanTypeRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Challan Type Name not allowed", "error");
            }
            ChallanTypeEntity newEntity = challanTypeRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateChallanTypes(Long id, ChallanTypeRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = challanTypeRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Challan Type Name not allowed", "error");
            }
            ChallanTypeEntity newEntity = challanTypeRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private ChallanTypeEntity transferToEntity(Long id, ChallanTypeRequestDto dto) {
        ChallanTypeEntity sourceType = new ChallanTypeEntity();

        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            ChallanTypeEntity newEntity = challanTypeRepository.findChallanTypeById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            sourceType.setId(newEntity.getId());

            sourceType.setName(dto.getName());
            sourceType.setNameBn(dto.getNameBn());
            sourceType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            sourceType.setActive(dto.getActive());

            sourceType.setUpdatedAt(new Date());
            sourceType.setUpdatedBy(userData.getId());

            return sourceType;
        } else {
            sourceType.setName(dto.getName());
            sourceType.setNameBn(dto.getNameBn());
            sourceType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            sourceType.setActive(dto.getActive());

            sourceType.setCreatedAt(new Date());
            sourceType.setCreatedBy(userData.getId());

            return sourceType;
        }

    }

    ChallanTypeResponseDto transferToDTO(ChallanTypeEntity entity) {

        ChallanTypeResponseDto dto = new ChallanTypeResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
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
}
