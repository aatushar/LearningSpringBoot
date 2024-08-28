package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companybranch.CompanyBranchBankDetailRequestDto;
import com.khajana.setting.dto.companybranch.CompanyBranchBankDetailResponseDto;
import com.khajana.setting.entity.companybranch.CompanyBranchBankDetail;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyBranchBankDetailRepository;
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
public class CompanyBranchBankDetailService {

    @Autowired
    CompanyBranchBankDetailRepository companyBranchBankDetailRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<CompanyBranchBankDetailResponseDto> findAllCompanyBranchBankDetails(Pageable pageable) {
        Page<CompanyBranchBankDetail> page = companyBranchBankDetailRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CompanyBranchBankDetailResponseDto findCompanyBranchBankDetailById(Long id) {

        CompanyBranchBankDetail newEntity = companyBranchBankDetailRepository.findCompanyBranchBankDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCompanyBranchBankDetail(CompanyBranchBankDetailRequestDto dto) {
        try {
            CompanyBranchBankDetail newEntity = companyBranchBankDetailRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCompanyBranchBankDetail(Long id, CompanyBranchBankDetailRequestDto dto) {
        // Read user id from JWT Token
        try {
            CompanyBranchBankDetail newEntity = companyBranchBankDetailRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CompanyBranchBankDetailResponseDto> getDropDown() {
        List<CompanyBranchBankDetail> page = companyBranchBankDetailRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCompanyBranchBankDetail(Long id) {
        companyBranchBankDetailRepository.deleteCompanyBranchBankDetailById(id);
    }

    private CompanyBranchBankDetail transferToEntity(Long id, CompanyBranchBankDetailRequestDto dto) {
        CompanyBranchBankDetail companyBranchBankDetail = new CompanyBranchBankDetail();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            companyBranchBankDetail.setId(id);

            companyBranchBankDetail.setBranchId(dto.getBranchId());
            companyBranchBankDetail.setBankBranchId(dto.getBankBranchId());
            companyBranchBankDetail.setBankAccountTypeId(dto.getBankAccountTypeId());
            companyBranchBankDetail.setCompanyAccountNumber(dto.getCompanyAccountNumber());
            companyBranchBankDetail.setCompanyAccountNumberBn(dto.getCompanyAccountNumberBn());

            companyBranchBankDetail.setActive(dto.getActive());
            companyBranchBankDetail.setCreatedAt(new Date());
            companyBranchBankDetail.setCreatedBy(userData.getId());
            companyBranchBankDetail.setUpdatedAt(new Date());
            companyBranchBankDetail.setUpdatedBy(userData.getId());

            return companyBranchBankDetail;
        } else {

            companyBranchBankDetail.setBranchId(dto.getBranchId());
            companyBranchBankDetail.setBankBranchId(dto.getBankBranchId());
            companyBranchBankDetail.setBankAccountTypeId(dto.getBankAccountTypeId());
            companyBranchBankDetail.setCompanyAccountNumber(dto.getCompanyAccountNumber());
            companyBranchBankDetail.setCompanyAccountNumberBn(dto.getCompanyAccountNumberBn());

            companyBranchBankDetail.setActive(dto.getActive());
            companyBranchBankDetail.setCreatedAt(new Date());
            companyBranchBankDetail.setCreatedBy(userData.getId());
            companyBranchBankDetail.setUpdatedAt(new Date());
            companyBranchBankDetail.setUpdatedBy(userData.getId());

            return companyBranchBankDetail;
        }

    }

    private CompanyBranchBankDetailResponseDto transferToDTO(CompanyBranchBankDetail entity) {
        CompanyBranchBankDetailResponseDto dto = new CompanyBranchBankDetailResponseDto();
        dto.setId(entity.getId());

        dto.setBranchId(entity.getBranchId());
        dto.setBankBranchId(entity.getBankBranchId());
        dto.setBankAccountTypeId(entity.getBankAccountTypeId());
        dto.setCompanyAccountNumber(entity.getCompanyAccountNumber());
        dto.setCompanyAccountNumberBn(entity.getCompanyAccountNumberBn());

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
