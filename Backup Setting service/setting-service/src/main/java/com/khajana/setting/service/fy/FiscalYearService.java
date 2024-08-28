package com.khajana.setting.service.fy;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.fy.FiscalYearInfoRequestDto;
import com.khajana.setting.dto.fy.FiscalYearInfoResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.fy.FiscalYearInfo;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.fy.FiscalYearInfoRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FiscalYearService {

    @Autowired
    UserCredentialRepository userCredentials;
    @Autowired
    private FiscalYearInfoRepository repository;

    public SimplePage<FiscalYearInfoResponseDto> findAllFiscalYear(Pageable pageable) {

        Page<FiscalYearInfo> page = repository.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public List<FiscalYearInfoResponseDto> getDropDown() {
        List<FiscalYearInfo> page = repository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public ApiResponse addFiscalYear(FiscalYearInfoRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = repository.existsByFiscalYear(dto.getFiscalYear());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Fiscal Year Info not allowed", "error");
            }
            FiscalYearInfo newEntity = repository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public FiscalYearInfoResponseDto updateFiscalYear(Long id, FiscalYearInfoRequestDto dto) {
//        FiscalYearInfo newEntity = repository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateFiscalYear(Long id, FiscalYearInfoRequestDto dto) {
        // Read user id from JWT Token
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = repository.existsByFiscalYearAndIdNot(dto.getFiscalYear(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Fiscal Year Info not allowed", "error");
            }

            FiscalYearInfo newEntity = repository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public FiscalYearInfoResponseDto findFiscalYearById(Long id) {

        FiscalYearInfo newEntity = repository.findFiscalYearById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    private FiscalYearInfo transferToEntity(Long id, FiscalYearInfoRequestDto dto) {
        FiscalYearInfo sourceType = new FiscalYearInfo();

        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            FiscalYearInfo newEntity = repository.findFiscalYearById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            sourceType.setId(newEntity.getId());
            sourceType.setFiscalYear(dto.getFiscalYear());
            sourceType.setFiscalYearBn(dto.getFiscalYearBn());
            sourceType.setSequenceNumber(dto.getSeqNo());
            sourceType.setFromDate(dto.getFromDate());
            sourceType.setToDate(dto.getToDate());
            sourceType.setSequenceNumber(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            sourceType.setActive(dto.getActive());
            sourceType.setUpdatedAt(new Date());
            sourceType.setUpdatedBy(userData.getId());
            return sourceType;
        } else {
            sourceType.setFiscalYear(dto.getFiscalYear());
            sourceType.setFiscalYearBn(dto.getFiscalYearBn());
            sourceType.setSequenceNumber(dto.getSeqNo());
            sourceType.setFromDate(dto.getFromDate());
            sourceType.setToDate(dto.getToDate());
            sourceType.setSequenceNumber(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            sourceType.setActive(dto.getActive());
            sourceType.setCreatedAt(new Date());
            sourceType.setCreatedBy(userData.getId());

            return sourceType;
        }

    }

    private FiscalYearInfoResponseDto transferToDTO(FiscalYearInfo entity) {
        FiscalYearInfoResponseDto dto = new FiscalYearInfoResponseDto();
        dto.setId(entity.getId());
        dto.setFiscalYear(entity.getFiscalYear());
        dto.setFiscalYearBn(entity.getFiscalYearBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSequenceNumber()));
        dto.setFromDate(DateUtil.convertDateToString(entity.getFromDate(), ConstantValue.ONLY_DATE));
        dto.setToDate(DateUtil.convertDateToString(entity.getToDate(), ConstantValue.ONLY_DATE));
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
