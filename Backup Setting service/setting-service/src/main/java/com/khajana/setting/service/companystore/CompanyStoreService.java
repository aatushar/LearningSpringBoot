package com.khajana.setting.service.companystore;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companystore.CompanyStoreRequestDto;
import com.khajana.setting.dto.companystore.CompanyStoreResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.address.AddressType;
import com.khajana.setting.entity.companybranch.Company;
import com.khajana.setting.entity.companybranch.CompanyBranch;
import com.khajana.setting.entity.companystore.CompanyStore;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.address.AddressTypeRepository;
import com.khajana.setting.repository.companybranch.CompanyBranchRepository;
import com.khajana.setting.repository.companybranch.CompanyRepository;
import com.khajana.setting.repository.companystore.CompanyStoreRepository;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.ConstantValue;
import com.khajana.setting.utils.ContextUser;
import com.khajana.setting.utils.DateUtil;
import com.khajana.setting.utils.SimplePage;

import io.micrometer.common.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyStoreService {

    @Autowired
    CompanyStoreRepository companyStoreRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CompanyBranchRepository companyBranchRepository;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    @Autowired
    AddressTypeRepository addressTypeRepository;

    public SimplePage<CompanyStoreResponseDto> findAllCompanyStores(String filter, Pageable pageable) {
        Page<CompanyStore> page;
        if (StringUtils.isNotEmpty(filter)) {

            page = companyStoreRepository.findAllCompanyStoreBySlNameContaining(filter, pageable);
        } else {

            page = companyStoreRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CompanyStoreResponseDto findCompanyStoreById(Long id) {

        CompanyStore newEntity = companyStoreRepository.findCompanyStoreById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCompanyStore(CompanyStoreRequestDto dto) {
        try {
            CompanyStore newEntity = companyStoreRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> companyStoreDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.companyStoreDropDown();
        return result;
    }

    // public CompanyStoreResponseDto updateCompanyStore(Long id,
    // CompanyStoreRequestDto dto) {
    // CompanyStore newEntity = companyStoreRepository.save(transferToEntity(id,
    // dto));
    // return transferToDTO(newEntity);
    // }
    public ApiResponse updateCompanyStore(Long id, CompanyStoreRequestDto dto) {
        // Read user id from JWT Token
        try {
            CompanyStore newEntity = companyStoreRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CompanyStoreResponseDto> getDropDown() {
        List<CompanyStore> page = companyStoreRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCompanyStore(Long id) {
        companyStoreRepository.deleteCompanyStoreById(id);
    }

    private CompanyStore transferToEntity(Long id, CompanyStoreRequestDto dto) {
        CompanyStore companyStore = new CompanyStore();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            companyStore.setId(id);

            companyStore.setBranchId(dto.getBranchId());
            companyStore.setSlCode(dto.getSlCode());
            companyStore.setSlName(dto.getSlName());
            companyStore.setSlNameBn(dto.getSlNameBn());
            companyStore.setSlShortName(dto.getSlShortName());
            companyStore.setSlShortNameBn(dto.getSlShortNameBn());
            companyStore.setSlAddress(dto.getSlAddress());
            companyStore.setSlAddressBn(dto.getSlAddressBn());
            companyStore.setSlOfficerId(null);
            companyStore.setSlType(dto.getSlType());
            companyStore.setIsDefaultLocation(dto.getIsDefaultLocation());
            companyStore.setIsSalesPoint(dto.getIsSalesPoint());
            companyStore.setIsVirtualLocation(dto.getIsVirtualLocation());
            companyStore.setTicketCatId(1l);

            companyStore.setActive(dto.getActive());
            companyStore.setUpdatedAt(new Date());
            companyStore.setUpdatedBy(userData.getId());

            return companyStore;
        } else {

            companyStore.setBranchId(dto.getBranchId());
            companyStore.setSlCode(dto.getSlCode());
            companyStore.setSlName(dto.getSlName());
            companyStore.setSlNameBn(dto.getSlNameBn());
            companyStore.setSlShortName(dto.getSlShortName());
            companyStore.setSlShortNameBn(dto.getSlShortNameBn());
            companyStore.setSlAddress(dto.getSlAddress());
            companyStore.setSlAddressBn(dto.getSlAddressBn());
            companyStore.setSlOfficerId(null);
            companyStore.setSlType(dto.getSlType());
            companyStore.setIsDefaultLocation(dto.getIsDefaultLocation());
            companyStore.setIsSalesPoint(dto.getIsSalesPoint());
            companyStore.setIsVirtualLocation(dto.getIsVirtualLocation());
            companyStore.setTicketCatId(1l);

            companyStore.setActive(dto.getActive());
            companyStore.setCreatedAt(new Date());
            companyStore.setCreatedBy(userData.getId());

            return companyStore;
        }

    }

    private CompanyStoreResponseDto transferToDTO(CompanyStore entity) {
        CompanyStoreResponseDto dto = new CompanyStoreResponseDto();
        dto.setId(entity.getId());
        dto.setBranchId(entity.getBranchId());
        dto.setSlCode(entity.getSlCode());
        dto.setSlName(entity.getSlName());
        dto.setSlNameBn(entity.getSlNameBn());
        dto.setSlShortName(entity.getSlShortName());
        dto.setSlShortNameBn(entity.getSlShortNameBn());
        dto.setSlAddress(entity.getSlAddress());
        dto.setSlAddressBn(entity.getSlAddress());
        // dto.setSlOfficerId(entity.getSlOfficerId());
        dto.setSlType(entity.getSlType());
        if (entity.getSlType() != null) {
            AddressType addressType = addressTypeRepository.findById(entity.getSlType()).orElse(null);
            if (addressType != null) {
                dto.setSlTypeName(addressType.getName());
                dto.setSlTypeNameBn(addressType.getNameBn());
            }
        }
        // dto.setSlTypeName();
        dto.setIsDefaultLocation(entity.getIsDefaultLocation());
        dto.setIsSalesPoint(entity.getIsSalesPoint());
        dto.setIsVirtualLocation(entity.getIsVirtualLocation());
        dto.setTicketCatId(1l);
        if (entity.getBranchId() != null) {
            CompanyBranch companyBranch = companyBranchRepository.findById(entity.getBranchId()).orElse(null);
            dto.setCompanyBranchName(companyBranch.getBranchUnitName());
            dto.setCompanyBranchNameBn(companyBranch.getBranchUnitNameBn());
            if (companyBranch.getCompanyId() != null) {
                Company company = companyRepository.findById(companyBranch.getCompanyId()).orElse(null);
                dto.setCompanyName(company.getCompName());
            }
        }
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
