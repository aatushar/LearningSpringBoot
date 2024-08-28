package com.khajana.setting.service.companystore;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companystore.StoreMappingDtoByUser;
import com.khajana.setting.dto.companystore.UserCompanyStoreMappingRequestDto;
import com.khajana.setting.dto.companystore.UserCompanyStoreMappingRequestDto.StoreMapping;
import com.khajana.setting.dto.companystore.UserCompanyStoreMappingResponseDto;
import com.khajana.setting.dto.companystore.UserCompanyStoreMappingUpsertResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.companybranch.Company;
import com.khajana.setting.entity.companybranch.CompanyBranch;
import com.khajana.setting.entity.companystore.CompanyStore;
import com.khajana.setting.entity.companystore.UserCompanyStoreMapping;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companystore.CompanyStoreRepository;
import com.khajana.setting.repository.companystore.UserCompanyStoreMappingRepository;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.ConstantValue;
import com.khajana.setting.utils.ContextUser;
import com.khajana.setting.utils.DateUtil;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCompanyStoreMappingService {

    @Autowired
    UserCompanyStoreMappingRepository userCompanyStoreMappingRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    CompanyStoreRepository companyStoreRepository;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    public SimplePage<UserCompanyStoreMappingResponseDto> findAllUserCompanyStoreMappings(Pageable pageable) {
        Page<UserCompanyStoreMapping> page = userCompanyStoreMappingRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public UserCompanyStoreMappingUpsertResponseDto findUserCompanyStoreMappingById(Long id) {

        User user = userCredentials.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToFindByIdDTO(user);
    }

    public ApiResponse addUserCompanyStoreMapping(UserCompanyStoreMappingRequestDto dto) {
        try {
            CustomUserDetails userData = ContextUser.getLoginUserData();
            for (StoreMapping storeMapping : dto.getStores()) {
                UserCompanyStoreMapping userCompanyStoreMapping = new UserCompanyStoreMapping();
                userCompanyStoreMapping.setUserId(dto.getUserId());
                userCompanyStoreMapping.setStoreId(storeMapping.getStoreId());
                userCompanyStoreMapping.setIsDefault(storeMapping.getIsDefault());
                userCompanyStoreMapping.setCreatedAt(new Date());
                userCompanyStoreMapping.setCreatedBy(userData.getId());
                userCompanyStoreMappingRepository.save(userCompanyStoreMapping);
            }
            return new ApiResponse(200, "OK", userData);

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateUserCompanyStoreMapping(Long id, UserCompanyStoreMappingRequestDto dto) {
        // return new ApiResponse(200, "ok", dto);
        try {
            CustomUserDetails userData = ContextUser.getLoginUserData();
            for (StoreMapping storeMapping : dto.getStores()) {
                UserCompanyStoreMapping userCompanyStoreMapping = new UserCompanyStoreMapping();
                if (storeMapping.getId() != null && storeMapping.getId() > 0) {
                    userCompanyStoreMapping.setId(id);
                    userCompanyStoreMapping.setUserId(dto.getUserId());
                    userCompanyStoreMapping.setStoreId(storeMapping.getStoreId());
                    userCompanyStoreMapping.setIsDefault(storeMapping.getIsDefault());
                    userCompanyStoreMapping.setUpdatedAt(new Date());
                    userCompanyStoreMapping.setUpdatedBy(userData.getId());
                    userCompanyStoreMappingRepository.save(userCompanyStoreMapping);
                } else {

                    userCompanyStoreMapping.setUserId(dto.getUserId());
                    userCompanyStoreMapping.setStoreId(storeMapping.getStoreId());
                    userCompanyStoreMapping.setIsDefault(storeMapping.getIsDefault());
                    userCompanyStoreMapping.setCreatedAt(new Date());
                    userCompanyStoreMapping.setCreatedBy(userData.getId());

                }
                userCompanyStoreMappingRepository.save(userCompanyStoreMapping);
            }
            return new ApiResponse(200, "OK", userData);

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<UserCompanyStoreMappingResponseDto> getDropDown() {
        List<UserCompanyStoreMapping> page = userCompanyStoreMappingRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteUserCompanyStoreMapping(Long id) {
        userCompanyStoreMappingRepository.deleteUserCompanyStoreMappingById(id);
    }


    private UserCompanyStoreMappingResponseDto transferToDTO(UserCompanyStoreMapping entity) {
        UserCompanyStoreMappingResponseDto dto = new UserCompanyStoreMappingResponseDto();
        dto.setId(entity.getId());

        dto.setUserId(entity.getUserId());

        if (entity.getUser() != null && entity.getUser().getName() != null) {
            dto.setUserName(entity.getUser().getName());
        }
        if (entity.getCompanyStore() != null && entity.getCompanyStore().getSlName() != null) {
            dto.setStoreName(entity.getCompanyStore().getSlName());
            dto.setAddress(entity.getCompanyStore().getSlAddress());
        }
        if (entity.getCompanyStore() != null &&
                entity.getCompanyStore().getSlNameBn() != null) {
            dto.setStoreNameBn(entity.getCompanyStore().getSlNameBn());
        }
        if (entity.getCompanyStore().getCompanyBranch() != null
                && entity.getCompanyStore().getCompanyBranch().getBranchUnitName() != null) {
            dto.setBranchName(entity.getCompanyStore().getCompanyBranch().getBranchUnitName());
        }
        if (entity.getCompanyStore().getCompanyBranch().getCompany() != null
                && entity.getCompanyStore().getCompanyBranch().getCompany().getCompName() != null) {
            dto.setCompanyName(entity.getCompanyStore().getCompanyBranch().getCompany().getCompName());
        }
        dto.setStoreId(entity.getStoreId());
        dto.setIsDefault(entity.getIsDefault());

        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(),
                ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(),
                ConstantValue.OUT_GOING_DATE_PATTERN));
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

    private UserCompanyStoreMappingUpsertResponseDto transferToFindByIdDTO(User entity) {
        UserCompanyStoreMappingUpsertResponseDto dto = new UserCompanyStoreMappingUpsertResponseDto();

        dto.setUserId(entity.getId());
        dto.setName(entity.getName());

        if (entity.getUserCompanyStoreMapping() != null && !entity.getUserCompanyStoreMapping().isEmpty()) {
            List<StoreMappingDtoByUser> storeMappings = entity
                    .getUserCompanyStoreMapping().stream().map(mappingStores -> {
                        StoreMappingDtoByUser storeMapping = new StoreMappingDtoByUser();
                        storeMapping.setId(mappingStores.getId());
                        if (mappingStores.getCompanyStore() != null) {
                            CompanyStore companyStore = mappingStores.getCompanyStore();

                            if (companyStore.getSlName() != null) {
                                storeMapping.setStoreName(companyStore.getSlName());
                                storeMapping.setAddress(companyStore.getSlAddress());
                            }

                            if (companyStore.getSlNameBn() != null) {
                                storeMapping.setStoreNameBn(companyStore.getSlNameBn());
                            }

                            if (companyStore.getCompanyBranch() != null) {
                                CompanyBranch companyBranch = companyStore.getCompanyBranch();

                                if (companyBranch.getBranchUnitName() != null) {
                                    storeMapping.setBranchName(companyBranch.getBranchUnitName());
                                }

                                if (companyBranch.getCompany() != null) {
                                    Company company = companyBranch.getCompany();

                                    if (company.getCompName() != null) {
                                        storeMapping.setCompanyName(company.getCompName());
                                    }
                                }
                            }
                        }

                        // Set other fields
                        storeMapping.setStoreId(mappingStores.getStoreId());
                        storeMapping.setIsDefault(mappingStores.getIsDefault());

                        // Set date strings
                        storeMapping.setCreatedAt(DateUtil.convertDateToString(mappingStores.getCreatedAt(),
                                ConstantValue.OUT_GOING_DATE_PATTERN));
                        storeMapping.setUpdatedAt(DateUtil.convertDateToString(mappingStores.getUpdatedAt(),
                                ConstantValue.OUT_GOING_DATE_PATTERN));

                        // Set createdBy and updatedBy
                        if (mappingStores.getCreatedBy() != null) {
                            User createdByUser = userCredentials.findById(mappingStores.getCreatedBy()).orElse(null);
                            storeMapping.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
                        }

                        if (mappingStores.getUpdatedBy() != null) {
                            User updatedByUser = userCredentials.findById(mappingStores.getUpdatedBy()).orElse(null);
                            storeMapping.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
                        }

                        return storeMapping; // Return the mapped StoreMapping
                    })
                    .collect(Collectors.toList()); // Collect mapped StoreMappings into a list

            dto.setStores(storeMappings); // Set the list of StoreMappings to the DTO
        }

        return dto;
    }

}
