package com.khajana.setting.service.transactiontype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.transactiontype.LcForRequestDto;
import com.khajana.setting.dto.transactiontype.LcForResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.transactiontype.LcFor;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.transactiontype.LcForRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LcForService {
    @Autowired
    LcForRepository lcForRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<LcForResponseDto> findAllLcFors(Pageable pageable) {

        Page<LcFor> page = lcForRepository.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public List<LcForResponseDto> getDropDown() {
        List<LcFor> page = lcForRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public LcForResponseDto findLcForById(Long id) {
        LcFor lcFor = lcForRepository.findLcForById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(lcFor);
    }

    public ApiResponse addLcFor(@Valid LcForRequestDto dto) {
        try {
            boolean typeExists = lcForRepository.existsByDescription(dto.getDescription());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate LC For is not allowed.", "error");
            }
            LcFor newEntity = lcForRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateLcFor(Long id, LcForRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = lcForRepository.existsByDescriptionAndIdNot(dto.getDescription(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate LC For is not allowed.", "error");
            }
            LcFor newEntity = lcForRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private LcFor transferToEntity(Long id, LcForRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        LcFor lcFor = new LcFor();
        if (id != null && id > 0) {
            LcFor lcFors = lcForRepository.findLcForById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            lcFor.setId(lcFors.getId());
            lcFor.setDescription(dto.getDescription());
            lcFor.setUpdatedAt(new Date());
            lcFor.setUpdatedBy(userData.getId());
            lcFor.setSeqNo(dto.getSeqNo());
            lcFor.setActive(dto.getActive());

            return lcFor;
        } else {
            lcFor.setDescription(dto.getDescription());
            lcFor.setSeqNo(dto.getSeqNo());
            lcFor.setActive(dto.getActive());
            lcFor.setCreatedAt(new Date());
            lcFor.setCreatedBy(userData.getId());

            return lcFor;
        }
    }

    private LcForResponseDto transferToDTO(LcFor lcFor) {
        LcForResponseDto dto = new LcForResponseDto();
        dto.setId(lcFor.getId());
        dto.setDescription(lcFor.getDescription());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(lcFor.getSeqNo()));
        dto.setActive(lcFor.getActive());
        dto.setCreatedAt(DateUtil.convertDateToString(lcFor.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(lcFor.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        if (lcFor.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(lcFor.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (lcFor.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(lcFor.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }
        return dto;
    }
}
