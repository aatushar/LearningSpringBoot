package com.khajana.setting.service.documenttype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.documenttype.DocumentTypesRequestDto;
import com.khajana.setting.dto.documenttype.DocumentTypesResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.documenttype.DocumentTypesEntity;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.documenttype.DocumentTypesRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class DocumentTypesService {
    @Autowired
    DocumentTypesRepository documentTypesRepository;

    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<DocumentTypesResponseDto> findAllDocumentType(Pageable pageable) {

        Page<DocumentTypesEntity> page = documentTypesRepository.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }


    public DocumentTypesResponseDto findDocumentTypeById(Long id) {
        DocumentTypesEntity economyActivity = documentTypesRepository.findDocumentTypesById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(economyActivity);
    }

    public ApiResponse addDocumentType(DocumentTypesRequestDto dto) {
        try {
            boolean typeExists = documentTypesRepository.existsByDocType(dto.getDocType());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Document type not allowed", "error");
            }
            DocumentTypesEntity newEntity = documentTypesRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateDocumentType(Long id, DocumentTypesRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = documentTypesRepository.existsByDocTypeAndIdNot(dto.getDocType(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Document type  not allowed", "error");
            }
            DocumentTypesEntity newEntity = documentTypesRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private DocumentTypesEntity transferToEntity(Long id, DocumentTypesRequestDto dto) {
        DocumentTypesEntity economyActivity = new DocumentTypesEntity();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            DocumentTypesEntity document = documentTypesRepository.findDocumentTypesById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

            document.setDocType(dto.getDocType());
            document.setDocTypeBn(dto.getDocTypeBn());
            document.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            document.setActive(dto.getActive());
            document.setUpdatedAt(new Date());
            document.setUpdatedBy(userData.getId());

            return document;
        } else {
            economyActivity.setDocType(dto.getDocType());
            economyActivity.setDocTypeBn(dto.getDocTypeBn());
            economyActivity.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            economyActivity.setActive(dto.getActive());
            economyActivity.setCreatedAt(new Date());
            economyActivity.setCreatedBy(userData.getId());
            return economyActivity;
        }
    }

    private DocumentTypesResponseDto transferToDTO(DocumentTypesEntity economyActivity) {
        DocumentTypesResponseDto dto = new DocumentTypesResponseDto();
        dto.setId(economyActivity.getId());
        dto.setDocType(economyActivity.getDocType());
        dto.setDocTypeBn(economyActivity.getDocTypeBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(economyActivity.getSeqNo()));
        dto.setActive(economyActivity.getActive());
        dto.setCreatedAt(
                DateUtil.convertDateToString(economyActivity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(
                DateUtil.convertDateToString(economyActivity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        ;

        if (economyActivity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(economyActivity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (economyActivity.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(economyActivity.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }

        return dto;
    }
}
