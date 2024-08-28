package com.khajana.setting.service.transactiontype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.transactiontype.TransAccountItemInfoRequestDto;
import com.khajana.setting.dto.transactiontype.TransAccountItemInfoResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.transactiontype.TransAccountItemInfo;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.transactiontype.TransAccountItemInfoRepo;
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
public class TransAccountItemInfoService {

    @Autowired
    TransAccountItemInfoRepo transAccountItemInfoRepo;
    @Autowired
    UserCredentialRepository userCredentials;

    public Page<TransAccountItemInfoResponseDto> findAllTransAccountItem(Pageable pageable) {
        Page<TransAccountItemInfo> page = transAccountItemInfoRepo.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
        // return page.map(this::transferToDTO);
    }

    public TransAccountItemInfoResponseDto getTransAccountItemById(Long id) {

        TransAccountItemInfo newEntity = transAccountItemInfoRepo.findTransAccountItemById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public List<TransAccountItemInfoResponseDto> getDropDown() {

        List<TransAccountItemInfo> list = transAccountItemInfoRepo.findAll();

        return list.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public ApiResponse addTransAccountItem(TransAccountItemInfoRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = transAccountItemInfoRepo.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Transaction account item not allowed", "error");
            }
            TransAccountItemInfo newEntity = transAccountItemInfoRepo.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public TransAccountItemInfoResponseDto updateTransAccountItem(Long id,TransAccountItemInfoRequestDto dto) {
//        TransAccountItemInfo newEntity = transAccountItemInfoRepo.save(transferToEntity(id,dto));
//        return  transferToDTO(newEntity);
//    }
    public ApiResponse updateTransAccountItem(Long id, TransAccountItemInfoRequestDto dto) {
        // Read user id from JWT Token
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = transAccountItemInfoRepo.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Transaction account item not allowed", "error");
            }
            TransAccountItemInfo newEntity = transAccountItemInfoRepo.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    TransAccountItemInfo transferToEntity(Long id, TransAccountItemInfoRequestDto dto) {
        TransAccountItemInfo sourceType = new TransAccountItemInfo();

        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            TransAccountItemInfo newEntity = transAccountItemInfoRepo.findTransAccountItemById(id)
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

    TransAccountItemInfoResponseDto transferToDTO(TransAccountItemInfo entity) {
        TransAccountItemInfoResponseDto dto = new TransAccountItemInfoResponseDto();
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
