package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companybranch.CompanyTypeRequestDto;
import com.khajana.setting.dto.companybranch.CompanyTypeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.companybranch.CompanyTypeEntity;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyTypeRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class CompanyTypeService {

    @Autowired
    CompanyTypeRepository companyTypeRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<CompanyTypeResponseDto> findAllCompanyType(
            Pageable pageable) {
        Page<CompanyTypeEntity> page = companyTypeRepository
                .findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CompanyTypeResponseDto findCompanyTypeById(Long id) {

        CompanyTypeEntity newEntity = companyTypeRepository
                .findCompanyTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCompanyType(
            CompanyTypeRequestDto dto) {
        try {
            boolean typeExists = companyTypeRepository.existsByCompanyTypeCode(dto.getCompanyTypeCode());
            boolean typeExists1 = companyTypeRepository.existsByCompanyTypeCodeBn(dto.getCompanyTypeCodeBn());
            boolean typeExists2 = companyTypeRepository.existsByCompanyType(dto.getCompanyType());
            boolean typeExists3 = companyTypeRepository.existsByCompanyTypeBn(dto.getCompanyTypeBn());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Company Type Code not allowed", "error");
            }
            if (typeExists1) {
                return new ApiResponse(400, "Duplicate Company Type Code Bn not allowed", "error");
            }
            if (typeExists2) {
                return new ApiResponse(400, "Duplicate Company Type Name not allowed", "error");
            }
            if (typeExists3) {
                return new ApiResponse(400, "Duplicate Company Type Name Bn not allowed", "error");
            }
            CompanyTypeEntity newEntity = companyTypeRepository
                    .save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCompanyType(Long id,
                                         CompanyTypeRequestDto dto) {
        // Read user id from JWT Token
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = companyTypeRepository.existsByCompanyTypeCodeAndIdNot(dto.getCompanyTypeCode(), id);
            boolean nameExistsForOtherId1 = companyTypeRepository.existsByCompanyTypeCodeBnAndIdNot(dto.getCompanyTypeCodeBn(), id);
            boolean nameExistsForOtherId2 = companyTypeRepository.existsByCompanyTypeAndIdNot(dto.getCompanyType(), id);
            boolean nameExistsForOtherId3 = companyTypeRepository.existsByCompanyTypeBnAndIdNot(dto.getCompanyTypeBn(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Company Type Code not allowed", "error");
            }
            if (nameExistsForOtherId1) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Company Type Code BN not allowed", "error");
            }
            if (nameExistsForOtherId2) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Company Type Name not allowed", "error");
            }
            if (nameExistsForOtherId3) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Company Type Name BN not allowed", "error");
            }
            CompanyTypeEntity newEntity = companyTypeRepository
                    .save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }


    private CompanyTypeEntity transferToEntity(Long id, CompanyTypeRequestDto dto) {
        CompanyTypeEntity companyBranchAddressDetail = new CompanyTypeEntity();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            CompanyTypeEntity newEntity = companyTypeRepository
                    .findCompanyTypeById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            newEntity.setCompanyTypeCode(dto.getCompanyTypeCode());
            newEntity.setCompanyTypeCodeBn(dto.getCompanyTypeCodeBn());
            newEntity.setCompanyType(dto.getCompanyType());
            newEntity.setCompanyTypeBn(dto.getCompanyTypeBn());
            newEntity.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            newEntity.setActive(dto.getActive());
            newEntity.setUpdatedAt(new Date());
            newEntity.setUpdatedBy(userData.getId());
            return newEntity;
        } else {
            companyBranchAddressDetail.setCompanyTypeCode(dto.getCompanyTypeCode());
            companyBranchAddressDetail.setCompanyTypeCodeBn(dto.getCompanyTypeCodeBn());
            companyBranchAddressDetail.setCompanyType(dto.getCompanyType());
            companyBranchAddressDetail.setCompanyTypeBn(dto.getCompanyTypeBn());
            companyBranchAddressDetail.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            companyBranchAddressDetail.setActive(dto.getActive());
            companyBranchAddressDetail.setCreatedAt(new Date());
            companyBranchAddressDetail.setCreatedBy(userData.getId());
            return companyBranchAddressDetail;
        }

    }

    private CompanyTypeResponseDto transferToDTO(CompanyTypeEntity entity) {
        CompanyTypeResponseDto dto = new CompanyTypeResponseDto();
        dto.setId(entity.getId());
        dto.setCompanyTypeCode(entity.getCompanyTypeCode());
        dto.setCompanyTypeCodeBn(entity.getCompanyTypeCodeBn());
        dto.setCompanyType(entity.getCompanyType());
        dto.setCompanyTypeBn(entity.getCompanyTypeBn());
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
