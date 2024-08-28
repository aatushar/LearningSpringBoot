package com.khajana.setting.service.transactiontype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.transactiontype.ImportLcPurposeRequestDto;
import com.khajana.setting.dto.transactiontype.ImportLcPurposeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.transactiontype.ImportLcPurpose;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.transactiontype.ImportLcPurposeRepository;
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
public class ImportLcPurposeService {
    @Autowired
    ImportLcPurposeRepository importLcPurposeRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<ImportLcPurposeResponseDto> findAllImportLcPurposes(Pageable pageable) {

        Page<ImportLcPurpose> page = importLcPurposeRepository.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public List<ImportLcPurposeResponseDto> getDropDown() {
        List<ImportLcPurpose> page = importLcPurposeRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public ImportLcPurposeResponseDto findImportLcPurposeById(Long id) {
        ImportLcPurpose importLcPurpose = importLcPurposeRepository.findImportLcPurposeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(importLcPurpose);
    }

    public ApiResponse addImportLcPurpose(ImportLcPurposeRequestDto dto) {
        try {
            boolean typeExists = importLcPurposeRepository.existsByPurpose(dto.getPurpose());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Import LC Purpose is not allowed", "error");
            }
            ImportLcPurpose newEntity = importLcPurposeRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateImportLcPurpose(Long id, ImportLcPurposeRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = importLcPurposeRepository.existsByPurposeAndIdNot(dto.getPurpose(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Import LC Purpose is not allowed", "error");
            }
            ImportLcPurpose newEntity = importLcPurposeRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private ImportLcPurpose transferToEntity(Long id, ImportLcPurposeRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        ImportLcPurpose importLcPurpose = new ImportLcPurpose();
        if (id != null && id > 0) {
            importLcPurpose.setId(id);
            importLcPurpose.setForUse(dto.getForUse());
            importLcPurpose.setPurpose(dto.getPurpose());
            importLcPurpose.setSeqNo(dto.getSeqNo());
            importLcPurpose.setActive(dto.getActive());
            importLcPurpose.setUpdatedBy(userData.getId());
            importLcPurpose.setUpdatedAt(new Date());

            return importLcPurpose;
        } else {
            importLcPurpose.setForUse(dto.getForUse());
            importLcPurpose.setPurpose(dto.getPurpose());
            importLcPurpose.setSeqNo(dto.getSeqNo());
            importLcPurpose.setActive(dto.getActive());
            importLcPurpose.setCreatedBy(userData.getId());
            importLcPurpose.setCreatedAt(new Date());
            return importLcPurpose;
        }
    }

    private ImportLcPurposeResponseDto transferToDTO(ImportLcPurpose importLcPurpose) {
        ImportLcPurposeResponseDto dto = new ImportLcPurposeResponseDto();
        dto.setId(importLcPurpose.getId());
        dto.setForUse(importLcPurpose.getForUse());
        dto.setPurpose(importLcPurpose.getPurpose());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(importLcPurpose.getSeqNo()));
        dto.setSeqNo(NumberFormat.get2DigitDecimal(importLcPurpose.getSeqNo()));
        dto.setActive(importLcPurpose.getActive());
        dto.setCreatedAt(
                DateUtil.convertDateToString(importLcPurpose.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(
                DateUtil.convertDateToString(importLcPurpose.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        if (importLcPurpose.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(importLcPurpose.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (importLcPurpose.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(importLcPurpose.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }
        return dto;
    }
}
