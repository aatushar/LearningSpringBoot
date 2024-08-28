package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companybranch.CompanyDesignationRequestDto;
import com.khajana.setting.dto.companybranch.CompanyDesignationResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.companybranch.Company;
import com.khajana.setting.entity.companybranch.CompanyDesignation;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyDesignationRepository;
import com.khajana.setting.repository.companybranch.CompanyRepository;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;

import io.micrometer.common.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyDesignationService {

    @Autowired
    CompanyDesignationRepository companyDesignationRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    public SimplePage<CompanyDesignationResponseDto> findAllCompanyDesignations(String filter, Pageable pageable) {
        Page<CompanyDesignation> page;
        if (StringUtils.isNotEmpty(filter)) {

            page = companyDesignationRepository.findAllCompanyDesignationByDesigNameContaining(filter, pageable);
        } else {

            page = companyDesignationRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CompanyDesignationResponseDto findCompanyDesignationById(Long id) {

        CompanyDesignation newEntity = companyDesignationRepository.findCompanyDesignationById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCompanyDesignation(CompanyDesignationRequestDto dto) {
        try {
            CompanyDesignation newEntity = companyDesignationRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCompanyDesignation(Long id, CompanyDesignationRequestDto dto) {
        // Read user id from JWT Token
        try {
            CompanyDesignation newEntity = companyDesignationRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.companyListDropDown();
        return result;
    }

    public void deleteCompanyDesignation(Long id) {
        companyDesignationRepository.deleteCompanyDesignationById(id);
    }

    private CompanyDesignation transferToEntity(Long id, CompanyDesignationRequestDto dto) {
        CompanyDesignation companyDesignation = new CompanyDesignation();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            companyDesignation.setId(id);

            companyDesignation.setCompanyId(dto.getCompanyId());
            companyDesignation.setDesigName(dto.getDesigName());
            companyDesignation.setDesigNameBn(dto.getDesigNameBn());
            companyDesignation.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            companyDesignation.setActive(dto.getActive());

            companyDesignation.setUpdatedAt(new Date());
            companyDesignation.setUpdatedBy(userData.getId());

            return companyDesignation;
        } else {

            companyDesignation.setCompanyId(dto.getCompanyId());
            companyDesignation.setDesigName(dto.getDesigName());
            companyDesignation.setDesigNameBn(dto.getDesigNameBn());
            companyDesignation.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            companyDesignation.setActive(dto.getActive());

            companyDesignation.setCreatedAt(new Date());
            companyDesignation.setCreatedBy(userData.getId());

            return companyDesignation;
        }

    }

    private CompanyDesignationResponseDto transferToDTO(CompanyDesignation entity) {
        CompanyDesignationResponseDto dto = new CompanyDesignationResponseDto();
        dto.setId(entity.getId());

        dto.setCompanyId(entity.getCompanyId());
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            dto.setCompanyName(company != null ? company.getCompName() : null);
        }
        dto.setDesigName(entity.getDesigName());
        dto.setDesigNameBn(entity.getDesigNameBn());
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
