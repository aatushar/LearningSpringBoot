package com.khajana.setting.service.customer;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.customer.*;
import com.khajana.setting.dto.issue.IssueMasterVdsResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.companybranch.Company;
import com.khajana.setting.entity.customer.Customer;
import com.khajana.setting.entity.customer.CustomerAddressDetail;
import com.khajana.setting.entity.customer.CustomerBankDetail;
import com.khajana.setting.entity.customer.CustomerContactInfo;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyRepository;
import com.khajana.setting.repository.customer.CustomerAddressDetailRepository;
import com.khajana.setting.repository.customer.CustomerBankDetailRepository;
import com.khajana.setting.repository.customer.CustomerContactInfoRepository;
import com.khajana.setting.repository.customer.CustomerRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerAddressDetailRepository customerAddressRepository;

    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CustomerBankDetailRepository customerBankDetailRepository;
    @Autowired
    CustomerContactInfoRepository customerContactInfoRepository;

    public SimplePage<CustomerResponseDto> findAllCustomers(String filter, Pageable pageable) {
        Page<Customer> page;
        if (StringUtils.isNotEmpty(filter)) {

            page = customerRepository.findAllCustomerByCustomerNameContaining(filter, pageable);
        } else {

            page = customerRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CustomerResponseDto findCustomerById(Long id) {

        Customer newEntity = customerRepository.findCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public CustomerIssueResponseDto findIssuedCustomerById(Long id) {

        Customer newEntity = customerRepository.findIssuedCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToIssuedCustomerDTO(newEntity);
    }

    public ApiResponse addCustomer(@Valid CustomerRequestDto customerRequestDto) {
        try {
            CustomUserDetails userData = ContextUser.getLoginUserData();

            /* Save Customer Data */
            Customer customer = new Customer();
            customer.setVatRegId(customerRequestDto.getVatRegId() != null ? customerRequestDto.getVatRegId() : null);
            customer.setCompanyId(customerRequestDto.getCompanyId());
            customer.setCustomerTypeId(customerRequestDto.getCustomerTypeId());
            customer.setCountryId(customerRequestDto.getCountryId());
            customer.setCustomerBinNumber(customerRequestDto.getCustomerBinNumber());
            customer.setCustomerBinNumberBn(customerRequestDto.getCustomerBinNumberBn());
            customer.setCustomerName(customerRequestDto.getCustomerName());
            customer.setCustomerNameBn(customerRequestDto.getCustomerNameBn());
            customer.setRegistrationStatus(customerRequestDto.getRegistrationStatus());
            customer.setEmailAddress(customerRequestDto.getEmailAddress());
            customer.setEmailVerifiedAt(new Date());
            customer.setActive(customerRequestDto.getActive());
            customer.setCreatedAt(new Date());
            customer.setCreatedBy(userData.getId());

            Customer customerCreated = customerRepository.save(customer);

            /* Save Customer Address Data */
            if (!customerRequestDto.getAddresses().isEmpty()) {
                for (CustomerAddressDetailRequestDto addressDto : customerRequestDto.getAddresses()) {

                    CustomerAddressDetail addressDetail = new CustomerAddressDetail();
                    addressDetail.setCustomerId(customerCreated.getId());
                    addressDetail.setAddressTypeId(addressDto.getAddressTypeId());
                    addressDetail.setAddress(addressDto.getAddress());
                    addressDetail.setPostalCode(addressDto.getPostalCode());
                    addressDetail.setDivisionId(addressDto.getDivisionId());
                    addressDetail.setUpazilaId(addressDto.getUpazilaId());
                    addressDetail.setDistrictId(addressDto.getDistrictId());
                    addressDetail.setCountryId(addressDto.getCountryId());
                    addressDetail.setDefault(true);
                    addressDetail.setActive(true);
                    addressDetail.setCreatedAt(new Date());
                    addressDetail.setCreatedBy(userData.getId());
                    // addressDetails.add(addressDetail);

                    customerAddressRepository.save(addressDetail);
                }
            }

            /* Save Bank Details Data */
            if (!customerRequestDto.getBankDetails().isEmpty()) {
                for (CustomerBankDetailRequestDto bankDetailDto : customerRequestDto.getBankDetails()) {
                    CustomerBankDetail bankDetail = new CustomerBankDetail();
                    bankDetail.setCustomerId(customer.getId());
                    bankDetail.setBankId(bankDetailDto.getBankId());
                    bankDetail.setBankBranchId(bankDetailDto.getBankBranchId());
                    bankDetail.setBankAccountTypeId(bankDetailDto.getBankAccountTypeId());
                    bankDetail.setCustomerAccountNumber(bankDetailDto.getCustomerAccountNumber());
                    bankDetail.setCustomerAccountNumberBn(bankDetailDto.getCustomerAccountNumberBn());
                    bankDetail.setActive(true);
                    bankDetail.setCreatedAt(new Date());
                    bankDetail.setCreatedBy(userData.getId());
                    customerBankDetailRepository.save(bankDetail);
                }
            }

            /* Save Contact Info Data */
            if (customerRequestDto.getContactInfos() != null) {
                CustomerContactInfoRequestDto contactInfoDto = customerRequestDto.getContactInfos();
                CustomerContactInfo contactInfo = new CustomerContactInfo();
                contactInfo.setCustomerId(customerCreated.getId());
                contactInfo.setCustomerCode(null);
                contactInfo.setContactPersonDisplayCode(null);
                contactInfo.setContactPersonShortName(null);
                contactInfo.setContactPerson(contactInfoDto.getContactPerson());
                contactInfo.setContactPersonPhone(contactInfoDto.getContactPersonPhone());
                contactInfo.setContactPersonTinNum(null);
                contactInfo.setActive(true);
                contactInfo.setCreatedAt(new Date());
                contactInfo.setCreatedBy(userData.getId());
                customerContactInfoRepository.save(contactInfo);
            }
            return new ApiResponse(200, "ok", transferToUpdateDTO(customerCreated));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCustomer(Long id, CustomerRequestUpdateDto customerRequestDto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        try {
            Customer customer = new Customer();
            customer.setId(id);
            customer.setVatRegId(customerRequestDto.getVatRegId() != null ? customerRequestDto.getVatRegId() : null);
            customer.setCompanyId(customerRequestDto.getCompanyId());
            customer.setCustomerTypeId(customerRequestDto.getCustomerTypeId());
            customer.setCountryId(customerRequestDto.getCountryId());
            customer.setCustomerBinNumber(customerRequestDto.getCustomerBinNumber());
            customer.setCustomerBinNumberBn(customerRequestDto.getCustomerBinNumberBn());
            customer.setCustomerName(customerRequestDto.getCustomerName());
            customer.setCustomerNameBn(customerRequestDto.getCustomerNameBn());
            customer.setRegistrationStatus(customerRequestDto.getRegistrationStatus());
            customer.setEmailAddress(customerRequestDto.getEmailAddress());
            customer.setActive(customerRequestDto.getActive());
            customer.setUpdatedAt(new Date());
            customer.setUpdatedBy(userData.getId());
            Customer customerCreated = customerRepository.save(customer);

            /* Update Customer Address Data */
            if (!customerRequestDto.getAddresses().isEmpty()) {
                for (CustomerAddressDetailRequestDto addressDto : customerRequestDto.getAddresses()) {

                    CustomerAddressDetail addressDetail = new CustomerAddressDetail();
                    if (addressDto.getId() != null) {
                        addressDetail.setId(addressDto.getId());
                        addressDetail.setCustomerId(customerCreated.getId());
                        addressDetail.setAddressTypeId(addressDto.getAddressTypeId());
                        addressDetail.setAddress(addressDto.getAddress());
                        addressDetail.setPostalCode(addressDto.getPostalCode());
                        addressDetail.setDivisionId(addressDto.getDivisionId());
                        addressDetail.setUpazilaId(addressDto.getUpazilaId());
                        addressDetail.setDistrictId(addressDto.getDistrictId());
                        // addressDetail.setPoliceStationId(addressDto.getPoliceStationId());
                        addressDetail.setCountryId(addressDto.getCountryId());
                        addressDetail.setDefault(true);
                        addressDetail.setActive(true);
                        addressDetail.setUpdatedAt(new Date());
                        addressDetail.setUpdatedBy(userData.getId());
                    } else {
                        addressDetail.setCustomerId(customerCreated.getId());
                        addressDetail.setAddressTypeId(addressDto.getAddressTypeId());
                        addressDetail.setAddress(addressDto.getAddress());
                        addressDetail.setPostalCode(addressDto.getPostalCode());
                        addressDetail.setDivisionId(addressDto.getDivisionId());
                        addressDetail.setUpazilaId(addressDto.getUpazilaId());
                        addressDetail.setDistrictId(addressDto.getDistrictId());
                        // addressDetail.setPoliceStationId(addressDto.getPoliceStationId());
                        addressDetail.setCountryId(addressDto.getCountryId());
                        addressDetail.setDefault(true);
                        addressDetail.setActive(true);
                        addressDetail.setCreatedAt(new Date());
                        addressDetail.setCreatedBy(userData.getId());

                    }
                    // addressDetails.add(addressDetail);

                    customerAddressRepository.save(addressDetail);
                }
            }

            /* Update Bank Details Data */
            if (!customerRequestDto.getBankDetails().isEmpty()) {
                for (CustomerBankDetailRequestDto bankDetailDto : customerRequestDto.getBankDetails()) {
                    CustomerBankDetail bankDetail = new CustomerBankDetail();
                    if (bankDetailDto.getId() != null) {
                        bankDetail.setId(bankDetailDto.getId());
                        bankDetail.setCustomerId(customer.getId());
                        bankDetail.setBankId(bankDetailDto.getBankId());
                        bankDetail.setBankBranchId(bankDetailDto.getBankBranchId());
                        bankDetail.setBankAccountTypeId(bankDetailDto.getBankAccountTypeId());
                        bankDetail.setCustomerAccountNumber(bankDetailDto.getCustomerAccountNumber());
                        bankDetail.setCustomerAccountNumberBn(bankDetailDto.getCustomerAccountNumberBn());
                        bankDetail.setActive(true);
                        bankDetail.setUpdatedAt(new Date());
                        bankDetail.setUpdatedBy(userData.getId());
                    } else {
                        bankDetail.setCustomerId(customer.getId());
                        bankDetail.setBankId(bankDetailDto.getBankId());
                        bankDetail.setBankBranchId(bankDetailDto.getBankBranchId());
                        bankDetail.setBankAccountTypeId(bankDetailDto.getBankAccountTypeId());
                        bankDetail.setCustomerAccountNumber(bankDetailDto.getCustomerAccountNumber());
                        bankDetail.setCustomerAccountNumberBn(bankDetailDto.getCustomerAccountNumberBn());
                        bankDetail.setActive(true);
                        bankDetail.setCreatedAt(new Date());
                        bankDetail.setCreatedBy(userData.getId());

                    }
                    customerBankDetailRepository.save(bankDetail);
                }
            }

            /* Update Contact Info Data */
            if (customerRequestDto.getContactInfos() != null) {
                CustomerContactInfoUpdateRequestDto contactInfoDto = customerRequestDto.getContactInfos();

                CustomerContactInfo contactInfo = new CustomerContactInfo();
                contactInfo.setId(contactInfoDto.getId());
                contactInfo.setCustomerId(customer.getId());
                contactInfo.setCustomerCode(null);
                contactInfo.setContactPersonDisplayCode(null);
                contactInfo.setContactPersonShortName(null);
                contactInfo.setContactPerson(contactInfoDto.getContactPerson());
                contactInfo.setContactPersonPhone(contactInfoDto.getContactPersonPhone());
                contactInfo.setContactPersonTinNum(null);
                contactInfo.setActive(true);
                contactInfo.setUpdatedAt(new Date());
                contactInfo.setUpdatedBy(userData.getId());
                customerContactInfoRepository.save(contactInfo);
            }
            return new ApiResponse(200, "ok", transferToUpdateDTO(customerCreated));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CustomerResponseDto> getDropDown() {
        List<Customer> page = customerRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteCustomerById(id);
    }

    private CustomerResponseDto transferToDTO(Customer entity) {
        CustomerResponseDto dto = new CustomerResponseDto();
        dto.setId(entity.getId());
        dto.setCompanyId(entity.getCompanyId());
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            dto.setCompanyName(company.getCompName());
        }
        dto.setCustomerTypeId(entity.getCustomerTypeId());
        dto.setCustomerTypeName("Regular");
        dto.setCountryId(entity.getCountryId());
        dto.setCustomerBinNumber(entity.getCustomerBinNumber());
        dto.setCustomerBinNumberBn(entity.getCustomerBinNumberBn());
        dto.setCustomerName(entity.getCustomerName());
        dto.setCustomerNameBn(entity.getCustomerNameBn());
        dto.setRegistrationStatus(entity.getRegistrationStatus());
        dto.setEmailAddress(entity.getEmailAddress());
        dto.setVatRegTypeName(entity.getVatReg() != null ? entity.getVatReg().getVatRegistrationName() : null);
        dto.setEmailVerifiedAt(
                DateUtil.convertDateToString(entity.getEmailVerifiedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setActive(entity.getActive());
        List<CustomerAddressDetailResponseDto> customerAddressDetails = entity.getAddresses().stream()
                .map(customerAddress -> {
                    CustomerAddressDetailResponseDto customerAddressDetail = new CustomerAddressDetailResponseDto();
                    customerAddressDetail.setId(customerAddress.getId());
                    customerAddressDetail.setAddressTypeId(customerAddress.getAddressTypeId());
                    customerAddressDetail.setCountryId(customerAddress.getCountryId());
                    customerAddressDetail.setAddress(customerAddress.getAddress());
                    customerAddressDetail.setPostalCode(customerAddress.getPostalCode());
                    customerAddressDetail.setUpazilaId(customerAddress.getUpazilaId());
                    customerAddressDetail.setDistrictId(customerAddress.getDistrictId());
                    // customerAddressDetail.setPoliceStationId(customerAddress.getPoliceStationId());
                    customerAddressDetail.setCustomerId(customerAddress.getCustomerId());
                    if (customerAddress.getCustomer() != null
                            && customerAddress.getCustomer().getCustomerName() != null) {
                        customerAddressDetail.setCustomerName(customerAddress.getCustomer().getCustomerName());
                    }
                    if (customerAddress.getAddressType() != null
                            && customerAddress.getAddressType().getName() != null) {
                        customerAddressDetail.setAddressTypeName(customerAddress.getAddressType().getName());
                    }
                    if (customerAddress.getUpazila() != null && customerAddress.getUpazila().getName() != null) {
                        customerAddressDetail.setUpazillaName(customerAddress.getUpazila().getName());
                    }
                    if (customerAddress.getDistrict() != null && customerAddress.getDistrict().getName() != null) {
                        customerAddressDetail.setDistrictName(customerAddress.getDistrict().getName());
                        customerAddressDetail.setDivisionName(customerAddress.getDistrict().getDivision().getName());
                        customerAddressDetail.setDivisionId(customerAddress.getDistrict().getDivision().getId());
                    }
                    if (customerAddress.getCountry() != null && customerAddress.getCountry().getName() != null) {
                        customerAddressDetail.setCountryName(customerAddress.getCountry().getName());
                    }
                    // if (customerAddress.getPoliceStation() != null
                    //         && customerAddress.getPoliceStation().getName() != null) {
                    //     customerAddressDetail.setPoliceStationName(customerAddress.getPoliceStation().getName());
                    // }
                    return customerAddressDetail;
                }).collect(Collectors.toList());
        dto.setAddresses(customerAddressDetails);
        List<CustomerBankDetailResponseDto> customerBankDetails = entity.getBankDetails().stream().map(customerBank -> {
            CustomerBankDetailResponseDto customerBankDetail = new CustomerBankDetailResponseDto();
            customerBankDetail.setId(customerBank.getId());
            customerBankDetail.setBankAccountTypeId(customerBank.getBankAccountTypeId());
            customerBankDetail.setBankBranchId(customerBank.getBankBranchId());
            customerBankDetail.setCustomerAccountNumber(customerBank.getCustomerAccountNumber());
            customerBankDetail.setCustomerAccountNumberBn(customerBank.getCustomerAccountNumberBn());
            customerBankDetail.setCustomerId(customerBank.getCustomerId());
            customerBankDetail.setBankId(customerBank.getBankId());
            if (customerBank.getCustomer() != null
                    && customerBank.getCustomer().getCustomerName() != null) {
                customerBankDetail.setCustomerName(customerBank.getCustomer().getCustomerName());
            }
            if (customerBank.getBankInfo() != null && customerBank.getBankInfo().getBankName() != null) {
                customerBankDetail.setBankName(customerBank.getBankInfo().getBankName());
            }
            if (customerBank.getBankBranchInfo() != null
                    && customerBank.getBankBranchInfo().getBankBranchName() != null) {
                customerBankDetail.setBankBranchName(customerBank.getBankBranchInfo().getBankBranchName());
            }
            if (customerBank.getBankAccountType() != null
                    && customerBank.getBankAccountType().getBankAccountTypeName() != null) {
                customerBankDetail.setBankAccountTypeName(customerBank.getBankAccountType().getBankAccountTypeName());
            }
            return customerBankDetail;
        }).collect(Collectors.toList());
        dto.setBankDetails(customerBankDetails);
        if (entity.getContactInfo() != null && entity.getContactInfo().getId() != null
                && entity.getContactInfo().getContactPerson() != null) {
            CustomerContactInfoResponseReturnDto customerContactInfoResponseReturnDto = new CustomerContactInfoResponseReturnDto();
            customerContactInfoResponseReturnDto.setId(entity.getContactInfo().getId());
            customerContactInfoResponseReturnDto.setCustomerId(entity.getContactInfo().getCustomerId());
            customerContactInfoResponseReturnDto.setContactPerson(entity.getContactInfo().getContactPerson());
            customerContactInfoResponseReturnDto.setContactPersonPhone(entity.getContactInfo().getContactPersonPhone());
            dto.setContactInfos(customerContactInfoResponseReturnDto);
        }
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

    private CustomerUpdateResponseDto transferToUpdateDTO(Customer entity) {
        CustomerUpdateResponseDto dto = new CustomerUpdateResponseDto();
        dto.setId(entity.getId());
        dto.setCompanyId(entity.getCompanyId());
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            dto.setCompanyName(company.getCompName());
        }
        dto.setCustomerTypeId(entity.getCustomerTypeId());
        dto.setCountryId(entity.getCountryId());
        dto.setCustomerBinNumber(entity.getCustomerBinNumber());
        dto.setCustomerBinNumberBn(entity.getCustomerBinNumberBn());
        dto.setCustomerName(entity.getCustomerName());
        dto.setCustomerNameBn(entity.getCustomerNameBn());
        dto.setRegistrationStatus(entity.getRegistrationStatus());
        dto.setEmailAddress(entity.getEmailAddress());
        dto.setEmailVerifiedAt(
                DateUtil.convertDateToString(entity.getEmailVerifiedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
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

    private CustomerIssueResponseDto transferToIssuedCustomerDTO(Customer entity) {
        CustomerIssueResponseDto dto = new CustomerIssueResponseDto();
        dto.setId(entity.getId());
        dto.setCustomerName(entity.getCustomerName());
        dto.setCustomerNameBn(entity.getCustomerNameBn());
        List<IssueMasterVdsResponseDto> issues = entity.getIssueMasters().stream()
                .filter(issuess -> issuess.getIsVdsApplicable() == 1 && issuess.getIsVdsDone() == 0)
                .map(issued -> {
                    IssueMasterVdsResponseDto issue = new IssueMasterVdsResponseDto();
                    issue.setIssueMasterId(issued.getId());
                    issue.setChallanDate(
                            DateUtil.convertDateToString(issued.getChallanDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
                    issue.setChallanNumber(issued.getChallanNumber());
                    issue.setChallanNumberBn(issued.getChallanNumberBn());
                    issue.setVatAmount(NumberFormat.get2DigitDecimal(issued.getTotalVatAmount()));
                    issue.setTotalAmountLocalCurr(NumberFormat.get2DigitDecimal(issued.getTotalIssueAmtLocalCurr()));
                    return issue;
                }).collect(Collectors.toList());
        dto.setVdsSellerChildRequestDtos(issues);
        return dto;
    }
}
