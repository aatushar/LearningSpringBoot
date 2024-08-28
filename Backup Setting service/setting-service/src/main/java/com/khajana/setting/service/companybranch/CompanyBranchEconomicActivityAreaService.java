package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companybranch.CompanyBranchEconomicActivityAreaRequestDto;
import com.khajana.setting.dto.companybranch.CompanyBranchEconomicActivityAreaResponseDto;
import com.khajana.setting.entity.companybranch.CompanyBranchEconomicActivityArea;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyBranchEconomyActivityAreaRepository;
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
public class CompanyBranchEconomicActivityAreaService {

    @Autowired
    CompanyBranchEconomyActivityAreaRepository companyBranchEconomicActivityAreaRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<CompanyBranchEconomicActivityAreaResponseDto> findAllCompanyBranchEconomicActivityAreas(
            Pageable pageable) {
        Page<CompanyBranchEconomicActivityArea> page = companyBranchEconomicActivityAreaRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CompanyBranchEconomicActivityAreaResponseDto findCompanyBranchEconomicActivityAreaById(Long id) {

        CompanyBranchEconomicActivityArea newEntity = companyBranchEconomicActivityAreaRepository
                .findCompanyBranchEconomicActivityAreaById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCompanyBranchEconomicActivityArea(CompanyBranchEconomicActivityAreaRequestDto dto) {
        try {
            CompanyBranchEconomicActivityArea newEntity = companyBranchEconomicActivityAreaRepository
                    .save(transferToEntity(0L, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }


    public ApiResponse updateCompanyBranchEconomicActivityArea(Long id,
                                                               CompanyBranchEconomicActivityAreaRequestDto dto) {
        // Read user id from JWT Token
        try {
            CompanyBranchEconomicActivityArea newEntity = companyBranchEconomicActivityAreaRepository
                    .save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CompanyBranchEconomicActivityAreaResponseDto> getDropDown() {
        List<CompanyBranchEconomicActivityArea> page = companyBranchEconomicActivityAreaRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCompanyBranchEconomicActivityArea(Long id) {
        companyBranchEconomicActivityAreaRepository.deleteCompanyBranchEconomicActivityAreaById(id);
    }

    private CompanyBranchEconomicActivityArea transferToEntity(Long id,
                                                               CompanyBranchEconomicActivityAreaRequestDto dto) {
        CompanyBranchEconomicActivityArea companyBranchEconomicActivityArea = new CompanyBranchEconomicActivityArea();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            companyBranchEconomicActivityArea.setId(id);

            companyBranchEconomicActivityArea.setBranchId(dto.getBranchId());
            companyBranchEconomicActivityArea.setEconomicActivityAreaId(dto.getEconomicActivityAreaId());
            companyBranchEconomicActivityArea.setOtherDetail(dto.getOtherDetail());
            companyBranchEconomicActivityArea.setSeqNo(dto.getSeqNo());
            companyBranchEconomicActivityArea.setActive(dto.getActive());

            companyBranchEconomicActivityArea.setCreatedAt(new Date());
            companyBranchEconomicActivityArea.setCreatedBy(userData.getId());
            companyBranchEconomicActivityArea.setUpdatedAt(new Date());
            companyBranchEconomicActivityArea.setUpdatedBy(userData.getId());

            return companyBranchEconomicActivityArea;
        } else {

            companyBranchEconomicActivityArea.setBranchId(dto.getBranchId());
            companyBranchEconomicActivityArea.setEconomicActivityAreaId(dto.getEconomicActivityAreaId());
            companyBranchEconomicActivityArea.setOtherDetail(dto.getOtherDetail());
            companyBranchEconomicActivityArea.setSeqNo(dto.getSeqNo());
            companyBranchEconomicActivityArea.setActive(dto.getActive());

            companyBranchEconomicActivityArea.setCreatedAt(new Date());
            companyBranchEconomicActivityArea.setCreatedBy(userData.getId());
            companyBranchEconomicActivityArea.setUpdatedAt(new Date());
            companyBranchEconomicActivityArea.setUpdatedBy(userData.getId());

            return companyBranchEconomicActivityArea;
        }

    }

    private CompanyBranchEconomicActivityAreaResponseDto transferToDTO(CompanyBranchEconomicActivityArea entity) {
        CompanyBranchEconomicActivityAreaResponseDto dto = new CompanyBranchEconomicActivityAreaResponseDto();
        dto.setId(entity.getId());

        dto.setBranchId(entity.getBranchId());
        dto.setEconomicActivityAreaId(entity.getEconomicActivityAreaId());
        dto.setOtherDetail(entity.getOtherDetail());
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
