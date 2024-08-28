package com.khajana.setting.service.customer;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.customer.CustomerAddressDetailRequestDto;
import com.khajana.setting.dto.customer.CustomerAddressDetailResponseDto;
import com.khajana.setting.entity.customer.CustomerAddressDetail;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.customer.CustomerAddressDetailRepository;
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
public class CustomerAddressService {

    @Autowired
    CustomerAddressDetailRepository customerAddressRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    /*
     * public Page<CustomerAddressDto> findAllCustomerAddresss(Pageable pageable) { Page<CustomerAddress>
     * page = customerAddressRepository.findAll(pageable); return
     * page.map(this::transferToDTO); }
     */

    public SimplePage<CustomerAddressDetailResponseDto> findAllCustomerAddresss(Pageable pageable) {
        Page<CustomerAddressDetail> page = customerAddressRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CustomerAddressDetailResponseDto findCustomerAddressById(Long id) {

        CustomerAddressDetail newEntity = customerAddressRepository.findCustomerAddressDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionCustomerAddress", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCustomerAddress(CustomerAddressDetailRequestDto dto) {
        // Read user id from JWT Token
        try {
            CustomerAddressDetail newEntity = customerAddressRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCustomerAddress(Long id, CustomerAddressDetailRequestDto dto) {
        // Read user id from JWT Token
        try {
            CustomerAddressDetail newEntity = customerAddressRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CustomerAddressDetailResponseDto> getDropDown() {
        List<CustomerAddressDetail> page = customerAddressRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    // public void deleteCustomerAddress(Long id) {
    // 	customerAddressRepository.deleteCustomerAddressDetailByById(id);
    // }

    private CustomerAddressDetail transferToEntity(Long id, CustomerAddressDetailRequestDto dto) {
        CustomerAddressDetail addressDetail = new CustomerAddressDetail();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            addressDetail.setId(id);
            addressDetail.setCustomerId(id);
            addressDetail.setAddressTypeId(dto.getAddressTypeId());
//            addressDetail.setHoldingNo(dto.getHoldingNo());
//            addressDetail.setRoadNo(dto.getRoadNo());
            addressDetail.setUpazilaId(dto.getUpazilaId());
            addressDetail.setDistrictId(dto.getDistrictId());
//            addressDetail.setPoliceStationId(dto.getPoliceStationId());
            addressDetail.setAddress(dto.getAddress());
            addressDetail.setPostalCode(dto.getPostalCode());
            addressDetail.setUpazilaId(dto.getUpazilaId());
            addressDetail.setDistrictId(dto.getDistrictId());
            addressDetail.setCountryId(dto.getCountryId());
            addressDetail.setDefault(true);
            addressDetail.setActive(true);
            addressDetail.setCreatedAt(new Date());
            addressDetail.setCreatedBy(userData.getId());
            addressDetail.setUpdatedAt(new Date());
            addressDetail.setUpdatedBy(userData.getId());
            // addressDetails.add(addressDetail);

            customerAddressRepository.save(addressDetail);

            return addressDetail;
        } else {
            addressDetail.setCustomerId(id);
            addressDetail.setAddressTypeId(dto.getAddressTypeId());
//            addressDetail.setHoldingNo(dto.getHoldingNo());
//            addressDetail.setRoadNo(dto.getRoadNo());
            addressDetail.setUpazilaId(dto.getUpazilaId());
            addressDetail.setDistrictId(dto.getDistrictId());
//            addressDetail.setPoliceStationId(dto.getPoliceStationId());
            addressDetail.setAddress(dto.getAddress());
            addressDetail.setPostalCode(dto.getPostalCode());
            addressDetail.setUpazilaId(dto.getUpazilaId());
            addressDetail.setDistrictId(dto.getDistrictId());
            addressDetail.setCountryId(dto.getCountryId());
            addressDetail.setDefault(true);
            addressDetail.setActive(true);
            addressDetail.setCreatedAt(new Date());
            addressDetail.setCreatedBy(userData.getId());
            addressDetail.setUpdatedAt(new Date());
            addressDetail.setUpdatedBy(userData.getId());
            // addressDetails.add(addressDetail);

            customerAddressRepository.save(addressDetail);

            return addressDetail;
        }
    }

    private CustomerAddressDetailResponseDto transferToDTO(CustomerAddressDetail entity) {
        CustomerAddressDetailResponseDto dto = new CustomerAddressDetailResponseDto();
        dto.setId(entity.getId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setAddressTypeId(entity.getAddressTypeId());
//        dto.setHoldingNo(entity.getHoldingNo());
//        dto.setRoadNo(entity.getHoldingNo());
        dto.setUpazilaId(entity.getUpazilaId());
        dto.setDistrictId(entity.getDistrictId());
//        dto.setPoliceStationId(entity.getPoliceStationId());
        dto.setAddress(entity.getAddress());
        dto.setPostalCode(entity.getPostalCode());
        dto.setUpazilaId(entity.getUpazilaId());
        dto.setDistrictId(entity.getDistrictId());
        dto.setCountryId(entity.getCountryId());
        dto.setIsDefault(entity.isDefault());
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
