package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.customer.CustomerBankDetailRequestDto;
import com.khajana.setting.dto.customer.CustomerBankDetailResponseDto;
import com.khajana.setting.entity.customer.CustomerBankDetail;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.customer.CustomerBankDetailRepository;
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
public class CompanySupplierBankDetailService {

    @Autowired
    CustomerBankDetailRepository customerBankDetailRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    /*
     * public Page<CustomerBankDetailDto> findAllCustomerBankDetails(Pageable pageable) { Page<CustomerBankDetail>
     * page = customerBankDetailRepository.findAll(pageable); return
     * page.map(this::transferToDTO); }
     */

    public SimplePage<CustomerBankDetailResponseDto> findAllCustomerBankDetails(Pageable pageable) {
        Page<CustomerBankDetail> page = customerBankDetailRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CustomerBankDetailResponseDto findCustomerBankDetailById(Long id) {

        CustomerBankDetail newEntity = customerBankDetailRepository.findCustomerBankDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionCustomerBankDetail", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCustomerBankDetail(CustomerBankDetailRequestDto dto) {
        // Read user id from JWT Token
        try {
            CustomerBankDetail newEntity = customerBankDetailRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCustomerBankDetail(Long id, CustomerBankDetailRequestDto dto) {
        // Read user id from JWT Token
        try {
            CustomerBankDetail newEntity = customerBankDetailRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CustomerBankDetailResponseDto> getDropDown() {
        List<CustomerBankDetail> page = customerBankDetailRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCustomerBankDetail(Long id) {
        customerBankDetailRepository.deleteCustomerBankDetailById(id);
    }

    private CustomerBankDetail transferToEntity(Long id, CustomerBankDetailRequestDto dto) {
        CustomerBankDetail bankDetail = new CustomerBankDetail();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            bankDetail.setId(id);
            bankDetail.setCustomerId(id);
            bankDetail.setBankId(dto.getBankId());
            bankDetail.setBankBranchId(dto.getBankBranchId());
            bankDetail.setBankAccountTypeId(dto.getBankAccountTypeId());
            bankDetail.setCustomerAccountNumber(dto.getCustomerAccountNumber());
            bankDetail.setCustomerAccountNumberBn(dto.getCustomerAccountNumberBn());
            bankDetail.setActive(true);
            bankDetail.setCreatedAt(new Date());
            bankDetail.setCreatedBy(userData.getId());
            bankDetail.setUpdatedAt(new Date());
            bankDetail.setUpdatedBy(userData.getId());
            customerBankDetailRepository.save(bankDetail);

            return bankDetail;
        } else {
            bankDetail.setId(id);
            bankDetail.setCustomerId(id);
            bankDetail.setBankId(dto.getBankId());
            bankDetail.setBankBranchId(dto.getBankBranchId());
            bankDetail.setBankAccountTypeId(dto.getBankAccountTypeId());
            bankDetail.setCustomerAccountNumber(dto.getCustomerAccountNumber());
            bankDetail.setCustomerAccountNumberBn(dto.getCustomerAccountNumberBn());
            bankDetail.setActive(true);
            bankDetail.setCreatedAt(new Date());
            bankDetail.setCreatedBy(userData.getId());
            bankDetail.setUpdatedAt(new Date());
            bankDetail.setUpdatedBy(userData.getId());
            customerBankDetailRepository.save(bankDetail);

            return bankDetail;
        }
    }

    private CustomerBankDetailResponseDto transferToDTO(CustomerBankDetail entity) {
        CustomerBankDetailResponseDto dto = new CustomerBankDetailResponseDto();
        dto.setId(entity.getId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setBankId(entity.getBankId());
        dto.setBankBranchId(entity.getBankBranchId());
        dto.setBankAccountTypeId(entity.getBankAccountTypeId());
        dto.setCustomerAccountNumber(entity.getCustomerAccountNumber());
        dto.setCustomerAccountNumberBn(entity.getCustomerAccountNumberBn());
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
