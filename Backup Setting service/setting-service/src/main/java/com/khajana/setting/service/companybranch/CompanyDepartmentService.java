package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companybranch.CompanyDepartmentRequestDto;
import com.khajana.setting.dto.companybranch.CompanyDepartmentResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.companybranch.Company;
import com.khajana.setting.entity.companybranch.CompanyDepartment;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyDepartmentRepository;
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
public class CompanyDepartmentService {

    @Autowired
    CompanyDepartmentRepository companyDepartmentRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    public SimplePage<CompanyDepartmentResponseDto> findAllCompanyDepartments(String filter, Pageable pageable) {
        Page<CompanyDepartment> page;
        if (StringUtils.isNotEmpty(filter)) {

            page = companyDepartmentRepository.findAllCompanyDepartmentByDepartmentNameContaining(filter, pageable);
        } else {

            page = companyDepartmentRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CompanyDepartmentResponseDto findCompanyDepartmentById(Long id) {

        CompanyDepartment newEntity = companyDepartmentRepository.findCompanyDepartmentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCompanyDepartment(CompanyDepartmentRequestDto dto) {
        try {
            CompanyDepartment newEntity = companyDepartmentRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCompanyDepartment(Long id, CompanyDepartmentRequestDto dto) {
        // Read user id from JWT Token
        try {
            CompanyDepartment newEntity = companyDepartmentRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.companyListDropDown();
        return result;
    }

    public void deleteCompanyDepartment(Long id) {
        companyDepartmentRepository.deleteCompanyDepartmentById(id);
    }

    private CompanyDepartment transferToEntity(Long id, CompanyDepartmentRequestDto dto) {
        CompanyDepartment companyDepartment = new CompanyDepartment();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            companyDepartment.setId(id);

            companyDepartment.setCompanyId(dto.getCompanyId());
            companyDepartment.setDepartmentName(dto.getDepartmentName());
            companyDepartment.setDepartmentNameBn(dto.getDepartmentNameBn());
            companyDepartment.setDepartmentPrefix(dto.getDepartmentPrefix());
            companyDepartment.setDepartmentPrefixBn(dto.getDepartmentPrefixBn());
            companyDepartment.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            companyDepartment.setActive(dto.getActive());
            companyDepartment.setUpdatedAt(new Date());
            companyDepartment.setUpdatedBy(userData.getId());
        } else {

            companyDepartment.setCompanyId(dto.getCompanyId());
            companyDepartment.setDepartmentName(dto.getDepartmentName());
            companyDepartment.setDepartmentNameBn(dto.getDepartmentNameBn());
            companyDepartment.setDepartmentPrefix(dto.getDepartmentPrefix());
            companyDepartment.setDepartmentPrefixBn(dto.getDepartmentPrefixBn());
            companyDepartment.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            companyDepartment.setActive(dto.getActive());
            companyDepartment.setCreatedAt(new Date());
            companyDepartment.setCreatedBy(userData.getId());

        }
        return companyDepartment;

    }

    private CompanyDepartmentResponseDto transferToDTO(CompanyDepartment entity) {
        CompanyDepartmentResponseDto dto = new CompanyDepartmentResponseDto();
        dto.setId(entity.getId());

        dto.setCompanyId(entity.getCompanyId());
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            dto.setCompanyName(company != null ? company.getCompName() : null);
        }
        dto.setDepartmentName(entity.getDepartmentName());
        dto.setDepartmentNameBn(entity.getDepartmentNameBn());
        dto.setDepartmentPrefix(entity.getDepartmentPrefix());
        dto.setDepartmentPrefixBn(entity.getDepartmentPrefixBn());
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
