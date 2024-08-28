package com.khajana.setting.service.businessinformation;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.businessinformation.BusinessInformationRequestDto;
import com.khajana.setting.dto.businessinformation.BusinessInformationResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.businessinformation.BusinessInformation;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.businessinformation.BusinessInfoRepo;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class BusinessInfoService {

    @Autowired
    BusinessInfoRepo businessInfoRepo;
    @Autowired
    UserCredentialRepository userCredentials;

    /*
     * public Page<CurrencyDto> findAllCurrencys(Pageable pageable) { Page<Currency>
     * page = currencyRepository.findAll(pageable); return
     * page.map(this::transferToDTO); }
     */

    public SimplePage<BusinessInformationResponseDto> findAllBusinessInformation(Pageable pageable) {
        Page<BusinessInformation> page = businessInfoRepo.findAll(pageable);
        //    if (pageable.getPageNo() <= 0) {
//        CustomKeyValue error = new CustomKeyValue("pageNo", "Page no. can't be 0");
//        return new ApiResponse(400, "Bad Request", Collections.singletonList(error), null);
//    }
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public BusinessInformationResponseDto findBusinessInformationById(Long id) {

        BusinessInformation newEntity = businessInfoRepo.findBusinessById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BusinessInformation", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addBusinessInfo(BusinessInformationRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = businessInfoRepo.existsByBusinessInfoName(dto.getBusinessInfoName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Business Information Name is not allowed", "error");
            }
            BusinessInformation newEntity = businessInfoRepo.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateBusinessInfo(Long id, BusinessInformationRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean nameExistsForOtherId = businessInfoRepo.existsByBusinessInfoNameAndIdNot(dto.getBusinessInfoName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Business Information Name is not allowed", "error");
            }
            BusinessInformation newEntity = businessInfoRepo.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

//    public List<CurrencyResponseDto> getDropDown() {
//        List<Currency> page = currencyRepository.findAll();
//        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
//    }

    public void deleteBusinessInformation(Long id) {
        businessInfoRepo.deleteBusinessById(id);
    }

    private BusinessInformation transferToEntity(Long id, BusinessInformationRequestDto dto) {
        BusinessInformation currency = new BusinessInformation();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            BusinessInformation newEntity = businessInfoRepo.findBusinessById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("BusinessInformation", "Id", id));
            newEntity.setId(newEntity.getId());
            newEntity.setBusinessInfoName(dto.getBusinessInfoName());
            newEntity.setBusinessInfoNameBn(dto.getBusinessInfoNameBn());
            newEntity.setSeqNo(dto.getSeqNo());
            newEntity.setActive(dto.getActive());
            newEntity.setUpdatedAt(new Date());
            newEntity.setUpdatedBy(userData.getId());

            return newEntity;
        } else {
            currency.setBusinessInfoName(dto.getBusinessInfoName());
            currency.setBusinessInfoNameBn(dto.getBusinessInfoNameBn());
            currency.setSeqNo(dto.getSeqNo());
            currency.setActive(dto.getActive());
            currency.setCreatedAt(new Date());
            currency.setCreatedBy(userData.getId());

            return currency;
        }
    }

    private BusinessInformationResponseDto transferToDTO(BusinessInformation entity) {

        BusinessInformationResponseDto dto = new BusinessInformationResponseDto();
        dto.setId(entity.getId());
        dto.setBusinessInfoName(entity.getBusinessInfoName());
        dto.setBusinessInfoNameBn(entity.getBusinessInfoNameBn());
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
