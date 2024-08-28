package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companybranch.CompanySectionRequestDto;
import com.khajana.setting.dto.companybranch.CompanySectionResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.companybranch.Company;
import com.khajana.setting.entity.companybranch.CompanyDepartment;
import com.khajana.setting.entity.companybranch.CompanySection;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyDepartmentRepository;
import com.khajana.setting.repository.companybranch.CompanyRepository;
import com.khajana.setting.repository.companybranch.CompanySectionRepository;
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
public class CompanySectionService {

    @Autowired
    CompanySectionRepository companySectionRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CompanyDepartmentRepository companyDepartmentRepository;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    public SimplePage<CompanySectionResponseDto> findAllCompanySections(String filter, Pageable pageable) {
        Page<CompanySection> page;
        if (StringUtils.isNotEmpty(filter)) {

            page = companySectionRepository.findAllCompanySectionBySecNameContaining(filter, pageable);
        } else {

            page = companySectionRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CompanySectionResponseDto findCompanySectionById(Long id) {

        CompanySection newEntity = companySectionRepository.findCompanySectionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCompanySection(CompanySectionRequestDto dto) {
        try {
            CompanySection newEntity = companySectionRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCompanySection(Long id, CompanySectionRequestDto dto) {
        // Read user id from JWT Token
        try {
            CompanySection newEntity = companySectionRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.departmentListDropDown();
        return result;
    }

    public void deleteCompanySection(Long id) {
        companySectionRepository.deleteCompanySectionById(id);
    }

    private CompanySection transferToEntity(Long id, CompanySectionRequestDto dto) {
        CompanySection companySection = new CompanySection();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            companySection.setId(id);

            companySection.setCompanyId(dto.getCompanyId());
            companySection.setDepartmentId(dto.getDepartmentId());
            companySection.setSecName(dto.getSecName());
            companySection.setSecNameBn(dto.getSecNameBn());
            companySection.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            companySection.setActive(dto.getActive());

            companySection.setUpdatedAt(new Date());
            companySection.setUpdatedBy(userData.getId());

            return companySection;
        } else {

            companySection.setCompanyId(dto.getCompanyId());
            companySection.setDepartmentId(dto.getDepartmentId());
            companySection.setSecName(dto.getSecName());
            companySection.setSecNameBn(dto.getSecNameBn());
            companySection.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            companySection.setActive(dto.getActive());

            companySection.setCreatedAt(new Date());
            companySection.setCreatedBy(userData.getId());

            return companySection;
        }

    }

    private CompanySectionResponseDto transferToDTO(CompanySection entity) {
        CompanySectionResponseDto dto = new CompanySectionResponseDto();
        dto.setId(entity.getId());

        dto.setCompanyId(entity.getCompanyId());
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            dto.setCompanyName(company != null ? company.getCompName() : null);
        }
        dto.setDepartmentId(entity.getDepartmentId());
        if (entity.getDepartmentId() != null) {
            CompanyDepartment companyDepartment = companyDepartmentRepository.findById(entity.getDepartmentId())
                    .orElse(null);
            dto.setDepartmentName(companyDepartment != null ? companyDepartment.getDepartmentName() : null);
        }
        dto.setSecName(entity.getSecName());
        dto.setSecNameBn(entity.getSecNameBn());
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
