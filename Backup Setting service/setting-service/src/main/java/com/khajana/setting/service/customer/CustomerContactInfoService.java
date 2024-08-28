package com.khajana.setting.service.customer;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.customer.CustomerContactInfoRequestDto;
import com.khajana.setting.dto.customer.CustomerContactInfoResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.customer.CustomerContactInfo;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.customer.CustomerContactInfoRepository;
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
public class CustomerContactInfoService {

    @Autowired
    CustomerContactInfoRepository customerContactInfoRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    /*
     * public Page<CustomerContactInfoDto> findAllCustomerContactInfos(Pageable pageable) { Page<CustomerContactInfo>
     * page = customerContactInfoRepository.findAll(pageable); return
     * page.map(this::transferToDTO); }
     */

    public SimplePage<CustomerContactInfoResponseDto> findAllCustomerContactInfos(Pageable pageable) {
        Page<CustomerContactInfo> page = customerContactInfoRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CustomerContactInfoResponseDto findCustomerContactInfoById(Long id) {

        CustomerContactInfo newEntity = customerContactInfoRepository.findCustomerContactInfoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionCustomerContactInfo", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCustomerContactInfo(CustomerContactInfoRequestDto dto) {
        // Read user id from JWT Token
        try {
            CustomerContactInfo newEntity = customerContactInfoRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCustomerContactInfo(Long id, CustomerContactInfoRequestDto dto) {
        // Read user id from JWT Token
        try {
            CustomerContactInfo newEntity = customerContactInfoRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CustomerContactInfoResponseDto> getDropDown() {
        List<CustomerContactInfo> page = customerContactInfoRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCustomerContactInfo(Long id) {
        customerContactInfoRepository.deleteCustomerContactInfoById(id);
    }

    private CustomerContactInfo transferToEntity(Long id, CustomerContactInfoRequestDto dto) {
        CustomerContactInfo customerContactInfo = new CustomerContactInfo();
        CustomUserDetails userData = ContextUser.getLoginUserData();
        if (id != null && id > 0) {
            customerContactInfo.setId(id);
            customerContactInfo.setCustomerId(id);
            customerContactInfo.setCustomerCode(null);
            customerContactInfo.setContactPersonDisplayCode(null);
            customerContactInfo.setContactPersonShortName(null);
            customerContactInfo.setContactPerson(dto.getContactPerson());
            customerContactInfo.setContactPersonPhone(null);
            customerContactInfo.setContactPersonTinNum(null);
            customerContactInfo.setActive(true);
            customerContactInfo.setCreatedAt(new Date());
            customerContactInfo.setCreatedBy(userData.getId());
            customerContactInfo.setUpdatedAt(new Date());
            customerContactInfo.setUpdatedBy(userData.getId());
            customerContactInfoRepository.save(customerContactInfo);

            return customerContactInfo;
        } else {
            customerContactInfo.setCustomerId(id);
            customerContactInfo.setCustomerCode(null);
            customerContactInfo.setContactPersonDisplayCode(null);
            customerContactInfo.setContactPersonShortName(null);
            customerContactInfo.setContactPerson(dto.getContactPerson());
            customerContactInfo.setContactPersonPhone(null);
            customerContactInfo.setContactPersonTinNum(null);
            customerContactInfo.setActive(true);
            customerContactInfo.setCreatedAt(new Date());
            customerContactInfo.setCreatedBy(userData.getId());
            customerContactInfo.setUpdatedAt(new Date());
            customerContactInfo.setUpdatedBy(userData.getId());
            customerContactInfoRepository.save(customerContactInfo);

            return customerContactInfo;
        }
    }

    private CustomerContactInfoResponseDto transferToDTO(CustomerContactInfo entity) {
        CustomerContactInfoResponseDto dto = new CustomerContactInfoResponseDto();
        dto.setId(entity.getId());
        dto.setContactPerson(entity.getContactPerson());
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
