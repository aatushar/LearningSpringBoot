package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companybranch.CompanyBranchEconomicActivityRequestDto;
import com.khajana.setting.dto.companybranch.CompanyBranchEconomicActivityResponseDto;
import com.khajana.setting.entity.companybranch.CompanyBranchEconomicActivity;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyBranchEconomyActivityRepository;
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
public class CompanyBranchEconomicActivityService {

    @Autowired
    CompanyBranchEconomyActivityRepository companyBranchEconomicActivityRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<CompanyBranchEconomicActivityResponseDto> findAllCompanyBranchEconomicActivitys(
            Pageable pageable) {
        Page<CompanyBranchEconomicActivity> page = companyBranchEconomicActivityRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CompanyBranchEconomicActivityResponseDto findCompanyBranchEconomicActivityById(Long id) {

        CompanyBranchEconomicActivity newEntity = companyBranchEconomicActivityRepository
                .findCompanyBranchEconomicActivityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCompanyBranchEconomicActivity(CompanyBranchEconomicActivityRequestDto dto) {
        try {
            CompanyBranchEconomicActivity newEntity = companyBranchEconomicActivityRepository
                    .save(transferToEntity(0L, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }


    public ApiResponse updateCompanyBranchEconomicActivity(Long id, CompanyBranchEconomicActivityRequestDto dto) {
        // Read user id from JWT Token
        try {
            CompanyBranchEconomicActivity newEntity = companyBranchEconomicActivityRepository
                    .save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CompanyBranchEconomicActivityResponseDto> getDropDown() {
        List<CompanyBranchEconomicActivity> page = companyBranchEconomicActivityRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCompanyBranchEconomicActivity(Long id) {
        companyBranchEconomicActivityRepository.deleteCompanyBranchEconomicActivityById(id);
    }

    private CompanyBranchEconomicActivity transferToEntity(Long id, CompanyBranchEconomicActivityRequestDto dto) {
        CompanyBranchEconomicActivity companyBranchEconomicActivity = new CompanyBranchEconomicActivity();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            companyBranchEconomicActivity.setId(id);

            companyBranchEconomicActivity.setBranchId(dto.getBranchId());
            companyBranchEconomicActivity.setEconomicActivityId(dto.getEconomicActivityId());
            companyBranchEconomicActivity.setSupportingDocNo(dto.getSupportingDocNo());
            companyBranchEconomicActivity.setSupportingDocIssueDate(dto.getSupportingDocIssueDate());
            companyBranchEconomicActivity.setSeqNo(dto.getSeqNo());
            companyBranchEconomicActivity.setActive(dto.getActive());

            companyBranchEconomicActivity.setCreatedAt(new Date());
            companyBranchEconomicActivity.setCreatedBy(userData.getId());
            companyBranchEconomicActivity.setUpdatedAt(new Date());
            companyBranchEconomicActivity.setUpdatedBy(userData.getId());

            return companyBranchEconomicActivity;
        } else {

            companyBranchEconomicActivity.setBranchId(dto.getBranchId());
            companyBranchEconomicActivity.setEconomicActivityId(dto.getEconomicActivityId());
            companyBranchEconomicActivity.setSupportingDocNo(dto.getSupportingDocNo());
            companyBranchEconomicActivity.setSupportingDocIssueDate(dto.getSupportingDocIssueDate());
            companyBranchEconomicActivity.setSeqNo(dto.getSeqNo());
            companyBranchEconomicActivity.setActive(dto.getActive());

            companyBranchEconomicActivity.setCreatedAt(new Date());
            companyBranchEconomicActivity.setCreatedBy(userData.getId());
            companyBranchEconomicActivity.setUpdatedAt(new Date());
            companyBranchEconomicActivity.setUpdatedBy(userData.getId());

            return companyBranchEconomicActivity;
        }

    }

    private CompanyBranchEconomicActivityResponseDto transferToDTO(CompanyBranchEconomicActivity entity) {
        CompanyBranchEconomicActivityResponseDto dto = new CompanyBranchEconomicActivityResponseDto();
        dto.setId(entity.getId());

        dto.setBranchId(entity.getBranchId());
        dto.setEconomicActivityId(entity.getEconomicActivityId());
        dto.setSupportingDocNo(entity.getSupportingDocNo());
        // dto.setSupportingDocIssueDate(entity.getSupportingDocIssueDate());
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
