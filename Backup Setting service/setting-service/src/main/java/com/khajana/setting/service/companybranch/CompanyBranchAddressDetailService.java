package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companybranch.CompanyBranchAddressDetailRequestDto;
import com.khajana.setting.dto.companybranch.CompanyBranchAddressDetailResponseDto;
import com.khajana.setting.entity.companybranch.CompanyBranchAddressDetail;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyBranchAddressDetailRepository;
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
public class CompanyBranchAddressDetailService {

    @Autowired
    CompanyBranchAddressDetailRepository companyBranchAddressDetailRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<CompanyBranchAddressDetailResponseDto> findAllCompanyBranchAddressDetails(
            Pageable pageable) {
        Page<CompanyBranchAddressDetail> page = companyBranchAddressDetailRepository
                .findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CompanyBranchAddressDetailResponseDto findCompanyBranchAddressDetailById(Long id) {

        CompanyBranchAddressDetail newEntity = companyBranchAddressDetailRepository
                .findCompanyBranchAddressDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCompanyBranchAddressDetail(
            CompanyBranchAddressDetailRequestDto dto) {
        try {
            CompanyBranchAddressDetail newEntity = companyBranchAddressDetailRepository
                    .save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCompanyBranchAddressDetail(Long id,
                                                        CompanyBranchAddressDetailRequestDto dto) {
        // Read user id from JWT Token
        try {
            CompanyBranchAddressDetail newEntity = companyBranchAddressDetailRepository
                    .save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CompanyBranchAddressDetailResponseDto> getDropDown() {
        List<CompanyBranchAddressDetail> page = companyBranchAddressDetailRepository
                .findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCompanyBranchAddressDetail(Long id) {
        companyBranchAddressDetailRepository.deleteCompanyBranchAddressDetailById(id);
    }

    private CompanyBranchAddressDetail transferToEntity(Long id,
                                                        CompanyBranchAddressDetailRequestDto dto) {
        CompanyBranchAddressDetail companyBranchAddressDetail = new CompanyBranchAddressDetail();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            companyBranchAddressDetail.setId(id);

            companyBranchAddressDetail.setBranchId(dto.getBranchId());
            companyBranchAddressDetail.setAddressTypeId(dto.getAddressTypeId());
            // companyBranchAddressDetail.setHoldingNo(dto.getHoldingNo());
            // companyBranchAddressDetail.setRoadNo(dto.getRoadNo());
            // companyBranchAddressDetail.setPoliceStationId(dto.getPoliceStationId());
            companyBranchAddressDetail.setDistrictId(dto.getDistrictId());
            companyBranchAddressDetail.setPostalCode(dto.getPostalCode());

            companyBranchAddressDetail.setActive(dto.getActive());

            companyBranchAddressDetail.setCreatedAt(new Date());
            companyBranchAddressDetail.setCreatedBy(userData.getId());
            companyBranchAddressDetail.setUpdatedAt(new Date());
            companyBranchAddressDetail.setUpdatedBy(userData.getId());

            return companyBranchAddressDetail;
        } else {

            companyBranchAddressDetail.setBranchId(dto.getBranchId());
            companyBranchAddressDetail.setAddressTypeId(dto.getAddressTypeId());
            // companyBranchAddressDetail.setHoldingNo(dto.getHoldingNo());
            // companyBranchAddressDetail.setRoadNo(dto.getRoadNo());
            // companyBranchAddressDetail.setPoliceStationId(dto.getPoliceStationId());
            companyBranchAddressDetail.setDistrictId(dto.getDistrictId());
            companyBranchAddressDetail.setPostalCode(dto.getPostalCode());

            companyBranchAddressDetail.setActive(dto.getActive());

            companyBranchAddressDetail.setCreatedAt(new Date());
            companyBranchAddressDetail.setCreatedBy(userData.getId());
            companyBranchAddressDetail.setUpdatedAt(new Date());
            companyBranchAddressDetail.setUpdatedBy(userData.getId());

            return companyBranchAddressDetail;
        }

    }

    private CompanyBranchAddressDetailResponseDto transferToDTO(
            CompanyBranchAddressDetail entity) {
        CompanyBranchAddressDetailResponseDto dto = new CompanyBranchAddressDetailResponseDto();
        dto.setId(entity.getId());

        dto.setBranchId(entity.getBranchId());
        dto.setAddressTypeId(entity.getAddressTypeId());
        // dto.setHoldingNo(entity.getHoldingNo());
        // dto.setRoadNo(entity.getRoadNo());
        // dto.setPoliceStationId(entity.getPoliceStationId());
        dto.setDistrictId(entity.getDistrictId());
        dto.setPostalCode(entity.getPostalCode());

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
