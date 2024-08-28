package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companybranch.CompanyBranchBusinessClassificationCodeRequestDto;
import com.khajana.setting.dto.companybranch.CompanyBranchBusinessClassificationCodeResponseDto;
import com.khajana.setting.entity.companybranch.CompanyBranchBusinessClassificationCode;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyBranchBusinessClassificationCodeRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.ContextUser;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyBranchBusinessClassificationCodeService {

    @Autowired
    CompanyBranchBusinessClassificationCodeRepository companyBranchBusinessClassificationCodeRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<CompanyBranchBusinessClassificationCodeResponseDto> findAllCompanyBranchBusinessClassificationCodes(
            Pageable pageable) {
        Page<CompanyBranchBusinessClassificationCode> page = companyBranchBusinessClassificationCodeRepository
                .findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CompanyBranchBusinessClassificationCodeResponseDto findCompanyBranchBusinessClassificationCodeById(Long id) {

        CompanyBranchBusinessClassificationCode newEntity = companyBranchBusinessClassificationCodeRepository
                .findCompanyBranchBusinessClassificationCodeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCompanyBranchBusinessClassificationCode(
            CompanyBranchBusinessClassificationCodeRequestDto dto) {
        try {
            CompanyBranchBusinessClassificationCode newEntity = companyBranchBusinessClassificationCodeRepository
                    .save(transferToEntity(0L, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCompanyBranchBusinessClassificationCode(Long id,
                                                                     CompanyBranchBusinessClassificationCodeRequestDto dto) {
        // Read user id from JWT Token
        try {
            CompanyBranchBusinessClassificationCode newEntity = companyBranchBusinessClassificationCodeRepository
                    .save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CompanyBranchBusinessClassificationCodeResponseDto> getDropDown() {
        List<CompanyBranchBusinessClassificationCode> page = companyBranchBusinessClassificationCodeRepository
                .findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCompanyBranchBusinessClassificationCode(Long id) {
        companyBranchBusinessClassificationCodeRepository.deleteCompanyBranchBusinessClassificationCodeById(id);
    }

    private CompanyBranchBusinessClassificationCode transferToEntity(Long id,
                                                                     CompanyBranchBusinessClassificationCodeRequestDto dto) {
        CompanyBranchBusinessClassificationCode companyBranchBusinessClassificationCode = new CompanyBranchBusinessClassificationCode();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            companyBranchBusinessClassificationCode.setId(id);

            companyBranchBusinessClassificationCode.setBranchId(dto.getBranchId());
            companyBranchBusinessClassificationCode.setHsCodeId(dto.getHsCodeId());
            companyBranchBusinessClassificationCode
                    .setCommercialDescriptionOfSupply(dto.getCommercialDescriptionOfSupply());
            companyBranchBusinessClassificationCode.setDescriptionOfHsCode(dto.getDescriptionOfHsCode());

            companyBranchBusinessClassificationCode.setSeqNo(dto.getSeqNo());
            companyBranchBusinessClassificationCode.setActive(dto.getActive());

            companyBranchBusinessClassificationCode.setCreatedAt(new Date());
            companyBranchBusinessClassificationCode.setCreatedBy(userData.getId());
            companyBranchBusinessClassificationCode.setUpdatedAt(new Date());
            companyBranchBusinessClassificationCode.setUpdatedBy(userData.getId());

            return companyBranchBusinessClassificationCode;
        } else {

            companyBranchBusinessClassificationCode.setBranchId(dto.getBranchId());
            companyBranchBusinessClassificationCode.setHsCodeId(dto.getHsCodeId());
            companyBranchBusinessClassificationCode
                    .setCommercialDescriptionOfSupply(dto.getCommercialDescriptionOfSupply());
            companyBranchBusinessClassificationCode.setDescriptionOfHsCode(dto.getDescriptionOfHsCode());

            companyBranchBusinessClassificationCode.setSeqNo(dto.getSeqNo());
            companyBranchBusinessClassificationCode.setActive(dto.getActive());

            companyBranchBusinessClassificationCode.setCreatedAt(new Date());
            companyBranchBusinessClassificationCode.setCreatedBy(userData.getId());
            companyBranchBusinessClassificationCode.setUpdatedAt(new Date());
            companyBranchBusinessClassificationCode.setUpdatedBy(userData.getId());

            return companyBranchBusinessClassificationCode;
        }

    }

    private CompanyBranchBusinessClassificationCodeResponseDto transferToDTO(
            CompanyBranchBusinessClassificationCode entity) {
        CompanyBranchBusinessClassificationCodeResponseDto dto = new CompanyBranchBusinessClassificationCodeResponseDto();
        dto.setId(entity.getId());

        dto.setBranchId(entity.getBranchId());
        dto.setHsCodeId(entity.getHsCodeId());
        dto.setCommercialDescriptionOfSupply(entity.getCommercialDescriptionOfSupply());
        dto.setDescriptionOfHsCode(entity.getDescriptionOfHsCode());

        dto.setSeqNo(entity.getSeqNo());
        dto.setActive(entity.getActive());

        // dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        // dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        // if (entity.getCreatedBy() != null) {
        //     User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
        //     dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        // }

        // if (entity.getUpdatedBy() != null) {
        //     User updatedByUser = userCredentials.findById(entity.getUpdatedBy()).orElse(null);
        //     dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        // }
        return dto;
    }
}
