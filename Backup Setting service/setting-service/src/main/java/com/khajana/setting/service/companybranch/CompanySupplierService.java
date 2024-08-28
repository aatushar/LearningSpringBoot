package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companybranch.*;
import com.khajana.setting.dto.receive.ReceiveMasterFromSupplierResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.address.AddressType;
import com.khajana.setting.entity.companybranch.*;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.entity.vat.VatRegistrationType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.address.AddressTypeRepository;
import com.khajana.setting.repository.companybranch.*;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.repository.vat.VatRegistrationTypeRepository;
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
public class CompanySupplierService {

    @Autowired
    CompanySupplierRepository companySupplierRepository;

    @Autowired
    CompanySupplierAddressDetailRepository companySupplierAddressRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    VatRegistrationTypeRepository vatRegistrationTypeRepository;

    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    AddressTypeRepository addressTypeRepository;

    @Autowired
    CompanySupplierBankDetailRepository companySupplierBankDetailRepository;
    @Autowired
    CompanySupplierContactInfoRepository companySupplierContactInfoRepository;
    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    public SimplePage<CompanySupplierResponseDto> findAllCompanySuppliers(String filter, Pageable pageable) {
        Page<CompanySupplier> page;
        if (StringUtils.isNotEmpty(filter)) {

            page = companySupplierRepository.findAllCompanySupplierBySupplierNameContaining(filter, pageable);
        } else {

            page = companySupplierRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CompanySupplierResponseDto findCompanySupplierById(Long id) {

        CompanySupplier newEntity = companySupplierRepository.findCompanySupplierById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public CompanySupplierReceiveResponseDto getRecievedBySupplier(Long id) {

        CompanySupplier newEntity = companySupplierRepository.findCompanySupplierById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToIssueOfSupplierDTO(newEntity);
    }

    public ApiResponse addCompanySupplier(@Valid CompanySupplierRequestDto companySupplierRequestDto) {
        try {
            CustomUserDetails userData = ContextUser.getLoginUserData();

            /* Save CompanySupplier Data */
            CompanySupplier companySupplier = new CompanySupplier();
            companySupplier.setVatRegId(companySupplierRequestDto.getVatRegId());
            companySupplier.setCompanyId(companySupplierRequestDto.getCompanyId());
            companySupplier.setSupplierTypeId(companySupplierRequestDto.getSupplierTypeId());
            companySupplier.setSupplierBinNumber(companySupplierRequestDto.getSupplierBinNumber());
            companySupplier.setSupplierBinNumberBn(companySupplierRequestDto.getSupplierBinNumberBn());
            companySupplier.setSupplierName(companySupplierRequestDto.getSupplierName());
            companySupplier.setSupplierNameBn(companySupplierRequestDto.getSupplierNameBn());
            companySupplier.setRegistrationStatus(companySupplierRequestDto.getRegistrationStatus());
            companySupplier.setEmail(companySupplierRequestDto.getEmail());
            companySupplier.setEmailVerifiedAt(null);
            companySupplier.setActive(companySupplierRequestDto.getActive());
            companySupplier.setCreatedAt(new Date());
            companySupplier.setCreatedBy(userData.getId());

            CompanySupplier companySupplierCreated = companySupplierRepository.save(companySupplier);

            /* Save CompanySupplier Address Data */
            for (CompanySupplierAddressDetailRequestDto addressDto : companySupplierRequestDto.getAddresses()) {

                CompanySupplierAddressDetail addressDetail = new CompanySupplierAddressDetail();
                addressDetail.setSupplierId(companySupplierCreated.getId());
                addressDetail.setAddressTypeId(addressDto.getAddressTypeId());

                addressDetail.setAddress(addressDto.getAddress());
                addressDetail.setPostalCode(addressDto.getPostalCode());
                addressDetail.setUpazilaId(addressDto.getUpazilaId());
                addressDetail.setDistrictId(addressDto.getDistrictId());
                addressDetail.setCountryId(addressDto.getCountryId());

                addressDetail.setActive(true);
                addressDetail.setCreatedAt(new Date());
                addressDetail.setCreatedBy(userData.getId());

                companySupplierAddressRepository.save(addressDetail);
            }

            /* Save Bank Details Data */

            for (CompanySupplierBankDetailRequestDto bankDetailDto : companySupplierRequestDto.getBankDetails()) {
                CompanySupplierBankDetail bankDetail = new CompanySupplierBankDetail();
                bankDetail.setSupplierId(companySupplier.getId());
                bankDetail.setBankId(bankDetailDto.getBankId());
                bankDetail.setBankBranchId(bankDetailDto.getBankBranchId());
                bankDetail.setBankAccountTypeId(bankDetailDto.getBankAccountTypeId());
                bankDetail.setSupplierAccountNumber(bankDetailDto.getSupplierAccountNumber());
                bankDetail.setSupplierAccountNumberBn(bankDetailDto.getSupplierAccountNumberBn());
                bankDetail.setActive(true);
                bankDetail.setCreatedAt(new Date());
                bankDetail.setCreatedBy(userData.getId());
                companySupplierBankDetailRepository.save(bankDetail);
            }

            /* Save Contact Info Data */

            CompanySupplierContactInfoRequestDto contactInfoDto = companySupplierRequestDto.getContactInfos();
            CompanySupplierContactInfo contactInfo = new CompanySupplierContactInfo();
            contactInfo.setSupplierId(companySupplierCreated.getId());
            contactInfo.setSupplierCode(null);
            contactInfo.setContactPersonDisplayCode(null);
            contactInfo.setContactPersonShortName(null);
            contactInfo.setContactPerson(contactInfoDto.getContactPerson());
            contactInfo.setPhone(contactInfoDto.getPhone());
            contactInfo.setContactPersonTinNum(null);
            contactInfo.setActive(true);
            contactInfo.setCreatedAt(new Date());
            contactInfo.setCreatedBy(userData.getId());
            companySupplierContactInfoRepository.save(contactInfo);
            return new ApiResponse(200, "OK", transferToUpdateDTO(companySupplierCreated));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCompanySupplier(Long id, CompanySupplierRequestUpdateDto companySupplierRequestDto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        try {
            CompanySupplier companySupplier = new CompanySupplier();
            companySupplier.setId(id);
            companySupplier.setVatRegId(companySupplierRequestDto.getVatRegId());
            companySupplier.setCompanyId(companySupplierRequestDto.getCompanyId());
            companySupplier.setSupplierTypeId(companySupplierRequestDto.getSupplierTypeId());
            companySupplier.setSupplierBinNumber(companySupplierRequestDto.getSupplierBinNumber());
            companySupplier.setSupplierBinNumberBn(companySupplierRequestDto.getSupplierBinNumberBn());
            companySupplier.setSupplierName(companySupplierRequestDto.getSupplierName());
            companySupplier.setSupplierNameBn(companySupplierRequestDto.getSupplierNameBn());
            companySupplier.setRegistrationStatus(companySupplierRequestDto.getRegistrationStatus());
            companySupplier.setEmail(companySupplierRequestDto.getEmail());
            companySupplier.setActive(companySupplierRequestDto.getActive());
            companySupplier.setUpdatedAt(new Date());
            companySupplier.setUpdatedBy(userData.getId());
            CompanySupplier companySupplierCreated = companySupplierRepository.save(companySupplier);

            /* Update CompanySupplier Address Data */
            for (CompanySupplierAddressDetailRequestDto addressDto : companySupplierRequestDto.getAddresses()) {

                CompanySupplierAddressDetail addressDetail = new CompanySupplierAddressDetail();
                if (addressDto.getId() != null) {
                    addressDetail.setId(addressDto.getId());
                    addressDetail.setSupplierId(companySupplierCreated.getId());
                    addressDetail.setAddressTypeId(addressDto.getAddressTypeId());
                    addressDetail.setAddress(addressDto.getAddress());
                    addressDetail.setPostalCode(addressDto.getPostalCode());
                    addressDetail.setUpazilaId(addressDto.getUpazilaId());
                    addressDetail.setDistrictId(addressDto.getDistrictId());
                    addressDetail.setCountryId(addressDto.getCountryId());

                    addressDetail.setActive(true);
                    addressDetail.setUpdatedAt(new Date());
                    addressDetail.setUpdatedBy(userData.getId());
                } else {
                    addressDetail.setSupplierId(companySupplierCreated.getId());
                    addressDetail.setAddressTypeId(addressDto.getAddressTypeId());
                    addressDetail.setAddress(addressDto.getAddress());
                    addressDetail.setPostalCode(addressDto.getPostalCode());
                    addressDetail.setUpazilaId(addressDto.getUpazilaId());
                    addressDetail.setDistrictId(addressDto.getDistrictId());
                    addressDetail.setCountryId(addressDto.getCountryId());

                    addressDetail.setActive(true);
                    addressDetail.setCreatedAt(new Date());
                    addressDetail.setCreatedBy(userData.getId());

                }

                companySupplierAddressRepository.save(addressDetail);
            }

            /* Update Bank Details Data */

            for (CompanySupplierBankDetailRequestDto bankDetailDto : companySupplierRequestDto.getBankDetails()) {
                CompanySupplierBankDetail bankDetail = new CompanySupplierBankDetail();
                if (bankDetailDto.getId() != null) {
                    bankDetail.setId(bankDetailDto.getId());
                    bankDetail.setSupplierId(companySupplier.getId());
                    bankDetail.setBankId(bankDetailDto.getBankId());
                    bankDetail.setBankBranchId(bankDetailDto.getBankBranchId());
                    bankDetail.setBankAccountTypeId(bankDetailDto.getBankAccountTypeId());
                    bankDetail.setSupplierAccountNumber(bankDetailDto.getSupplierAccountNumber());
                    bankDetail.setSupplierAccountNumberBn(bankDetailDto.getSupplierAccountNumberBn());
                    bankDetail.setActive(true);
                    bankDetail.setUpdatedAt(new Date());
                    bankDetail.setUpdatedBy(userData.getId());
                } else {
                    bankDetail.setSupplierId(companySupplier.getId());
                    bankDetail.setBankId(bankDetailDto.getBankId());
                    bankDetail.setBankBranchId(bankDetailDto.getBankBranchId());
                    bankDetail.setBankAccountTypeId(bankDetailDto.getBankAccountTypeId());
                    bankDetail.setSupplierAccountNumber(bankDetailDto.getSupplierAccountNumber());
                    bankDetail.setSupplierAccountNumberBn(bankDetailDto.getSupplierAccountNumberBn());
                    bankDetail.setActive(true);
                    bankDetail.setCreatedAt(new Date());
                    bankDetail.setCreatedBy(userData.getId());

                }
                companySupplierBankDetailRepository.save(bankDetail);
            }

            /* Update Contact Info Data */
            if (companySupplierRequestDto.getContactInfos() != null) {
                CompanySupplierContactInfoUpdateRequestDto contactInfoDto = companySupplierRequestDto.getContactInfos();
                CompanySupplierContactInfo contactInfo = new CompanySupplierContactInfo();
                contactInfo.setId(contactInfoDto.getId());
                contactInfo.setSupplierId(companySupplierCreated.getId());
                contactInfo.setContactPerson(contactInfoDto.getContactPerson());
                contactInfo.setPhone(contactInfoDto.getPhone());
                contactInfo.setActive(true);
                contactInfo.setUpdatedAt(new Date());
                contactInfo.setUpdatedBy(userData.getId());
                companySupplierContactInfoRepository.save(contactInfo);
            }
            return new ApiResponse(200, "OK", transferToUpdateDTO(companySupplierCreated));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.companySupplierListDropDown();
        return result;
    }

    public List<HouseKeeping> getRecievedSupplierVatMonth() {
        List<HouseKeeping> result = houseKeepingRepository.getRecievedSupplierVatMonth();
        return result;
    }

    public void deleteCompanySupplier(Long id) {
        companySupplierRepository.deleteCompanySupplierById(id);
    }

    private CompanySupplierResponseDto transferToDTO(CompanySupplier entity) {
        CompanySupplierResponseDto dto = new CompanySupplierResponseDto();
        dto.setId(entity.getId());
        dto.setVatRegId(entity.getVatRegId());
        dto.setCompanyId(entity.getCompanyId());
        if (entity.getSupplierTypeId() != null) {
            AddressType addressType = addressTypeRepository.findById(entity.getSupplierTypeId()).orElse(null);
            dto.setSupplierTypeName(addressType.getName());
        }
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            if (company != null) {
                dto.setCompanyName(company.getCompName());
            }
        }
        if (entity.getVatRegId() != null) {
            VatRegistrationType vatRegistrationType = vatRegistrationTypeRepository.findById(entity.getVatRegId())
                    .orElse(null);
            if (vatRegistrationType != null) {
                dto.setVatRegTypeName(vatRegistrationType.getVatRegistrationName());
            }
        }
        dto.setSupplierTypeId(entity.getSupplierTypeId());
        dto.setSupplierBinNumber(entity.getSupplierBinNumber());
        dto.setSupplierBinNumberBn(entity.getSupplierBinNumberBn());
        dto.setSupplierName(entity.getSupplierName());
        dto.setSupplierNameBn(entity.getSupplierNameBn());
        dto.setRegistrationStatus(entity.getRegistrationStatus());
        dto.setEmail(entity.getEmail());
        dto.setEmailVerifiedAt(
                DateUtil.convertDateToString(entity.getEmailVerifiedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setActive(entity.getActive());
        List<CompanySupplierAddressDetailResponseDto> companySupplierAddressDetails = entity.getAddresses().stream()
                .map(companySupplierAddress -> {
                    CompanySupplierAddressDetailResponseDto companySupplierAddressDetail = new CompanySupplierAddressDetailResponseDto();
                    companySupplierAddressDetail.setId(companySupplierAddress.getId());
                    companySupplierAddressDetail.setAddressTypeId(companySupplierAddress.getAddressTypeId());
                    companySupplierAddressDetail.setCountryId(companySupplierAddress.getCountryId());
                    companySupplierAddressDetail.setAddress(companySupplierAddress.getAddress());
                    companySupplierAddressDetail.setPostalCode(companySupplierAddress.getPostalCode());
                    companySupplierAddressDetail.setUpazilaId(companySupplierAddress.getUpazilaId());
                    companySupplierAddressDetail.setDistrictId(companySupplierAddress.getDistrictId());
                    companySupplierAddressDetail.setSupplierId(companySupplierAddress.getSupplierId());
                    if (companySupplierAddress.getCompanySupplier() != null
                            && companySupplierAddress.getCompanySupplier().getSupplierName() != null) {
                        companySupplierAddressDetail
                                .setSupplierName(companySupplierAddress.getCompanySupplier().getSupplierName());
                    }
                    if (companySupplierAddress.getAddressType() != null
                            && companySupplierAddress.getAddressType().getName() != null) {
                        companySupplierAddressDetail
                                .setAddressTypeName(companySupplierAddress.getAddressType().getName());
                    }
                    if (companySupplierAddress.getUpazila() != null &&
                            companySupplierAddress.getUpazila().getName() != null) {
                        companySupplierAddressDetail.setUpazillaName(companySupplierAddress.getUpazila().getName());
                    }
                    if (companySupplierAddress.getDistrict() != null &&
                            companySupplierAddress.getDistrict().getName() != null) {
                        companySupplierAddressDetail.setDistrictName(companySupplierAddress.getDistrict().getName());
                    }
                    if (companySupplierAddress.getDistrict() != null &&
                            companySupplierAddress.getDistrict().getDivision() != null) {
                        companySupplierAddressDetail
                                .setDivisionName(companySupplierAddress.getDistrict().getDivision().getName());
                        companySupplierAddressDetail
                                .setDivisionId(companySupplierAddress.getDistrict().getDivision().getId());
                    }
                    if (companySupplierAddress.getCountry() != null &&
                            companySupplierAddress.getCountry().getName() != null) {
                        companySupplierAddressDetail.setCountryName(companySupplierAddress.getCountry().getName());
                    }
                    return companySupplierAddressDetail;
                }).collect(Collectors.toList());
        dto.setAddresses(companySupplierAddressDetails);
        List<CompanySupplierBankDetailResponseDto> companySupplierBankDetails = entity.getBankDetails().stream()
                .map(companySupplierBank -> {
                    CompanySupplierBankDetailResponseDto companySupplierBankDetail = new CompanySupplierBankDetailResponseDto();
                    companySupplierBankDetail.setId(companySupplierBank.getId());
                    companySupplierBankDetail.setBankAccountTypeId(companySupplierBank.getBankAccountTypeId());
                    companySupplierBankDetail.setBankBranchId(companySupplierBank.getBankBranchId());
                    companySupplierBankDetail.setSupplierAccountNumber(companySupplierBank.getSupplierAccountNumber());
                    companySupplierBankDetail
                            .setSupplierAccountNumberBn(companySupplierBank.getSupplierAccountNumberBn());
                    companySupplierBankDetail.setSupplierId(companySupplierBank.getSupplierId());
                    companySupplierBankDetail.setBankId(companySupplierBank.getBankId());
                    if (companySupplierBank.getCompanySupplier() != null
                            && companySupplierBank.getCompanySupplier().getSupplierName() != null) {
                        companySupplierBankDetail
                                .setSupplierName(companySupplierBank.getCompanySupplier().getSupplierName());
                    }
                    if (companySupplierBank.getBankInfo() != null &&
                            companySupplierBank.getBankInfo().getBankName() != null) {
                        companySupplierBankDetail.setBankName(companySupplierBank.getBankInfo().getBankName());
                    }
                    if (companySupplierBank.getBankBranchInfo() != null
                            && companySupplierBank.getBankBranchInfo().getBankBranchName() != null) {
                        companySupplierBankDetail
                                .setBankBranchName(companySupplierBank.getBankBranchInfo().getBankBranchName());
                    }
                    if (companySupplierBank.getBankAccountType() != null
                            && companySupplierBank.getBankAccountType().getBankAccountTypeName() != null) {
                        companySupplierBankDetail.setBankAccountTypeName(
                                companySupplierBank.getBankAccountType().getBankAccountTypeName());
                    }
                    return companySupplierBankDetail;
                }).collect(Collectors.toList());
        dto.setBankDetails(companySupplierBankDetails);
        if (entity.getContactInfo() != null && entity.getContactInfo().getId() != null) {
            CompanySupplierContactInfoResponseReturnDto companySupplierContactInfoResponseReturnDto = new CompanySupplierContactInfoResponseReturnDto();
            companySupplierContactInfoResponseReturnDto.setId(entity.getContactInfo().getId());
            companySupplierContactInfoResponseReturnDto.setSupplierId(entity.getContactInfo().getSupplierId());
            companySupplierContactInfoResponseReturnDto.setContactPerson(entity.getContactInfo().getContactPerson());
            companySupplierContactInfoResponseReturnDto.setPhone(entity.getContactInfo().getPhone());
            dto.setContactInfos(companySupplierContactInfoResponseReturnDto);
        }
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

    private CompanySupplierUpdateResponseDto transferToUpdateDTO(CompanySupplier entity) {
        CompanySupplierUpdateResponseDto dto = new CompanySupplierUpdateResponseDto();
        dto.setId(entity.getId());
        dto.setVatRegId(entity.getVatRegId());
        dto.setCompanyId(entity.getCompanyId());
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            if (company != null) {
                dto.setCompanyName(company.getCompName());
            }
        }
        dto.setSupplierTypeId(entity.getSupplierTypeId());
        dto.setSupplierBinNumber(entity.getSupplierBinNumber());
        dto.setSupplierBinNumberBn(entity.getSupplierBinNumberBn());
        dto.setSupplierName(entity.getSupplierName());
        dto.setSupplierNameBn(entity.getSupplierNameBn());
        dto.setRegistrationStatus(entity.getRegistrationStatus());
        dto.setEmail(entity.getEmail());
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

    private CompanySupplierReceiveResponseDto transferToIssueOfSupplierDTO(CompanySupplier entity) {
        CompanySupplierReceiveResponseDto dto = new CompanySupplierReceiveResponseDto();
        dto.setId(entity.getId());
        dto.setSupplierBinNumber(entity.getSupplierBinNumber());
        dto.setSupplierBinNumberBn(entity.getSupplierBinNumberBn());
        dto.setSupplierName(entity.getSupplierName());
        dto.setSupplierNameBn(entity.getSupplierNameBn());
        List<ReceiveMasterFromSupplierResponseDto> receives = entity.getReceives().stream().map(received -> {
            ReceiveMasterFromSupplierResponseDto receive = new ReceiveMasterFromSupplierResponseDto();
            receive.setReceiveMasterId(received.getId());
            receive.setSupplierId(entity.getId());
            receive.setSupplierBinNumber(entity.getSupplierBinNumber());
            receive.setSupplierBinNumberBn(entity.getSupplierBinNumberBn());
            receive.setSupplierAccountNumber(receive.getSupplierAccountNumber());
            receive.setChallanDate(
                    DateUtil.convertDateToString(received.getChallanDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
            receive.setReceiveDate(
                    DateUtil.convertDateToString(received.getReceiveDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
            receive.setChallanNumber(received.getChallanNumber());
            receive.setChallanNumberBn(received.getChallanNumberBn());
            receive.setVatAmount(NumberFormat.get2DigitDecimal(received.getTotalVatAmount()));
            receive.setRecvAmtWithtaxLocalCurr(NumberFormat.get2DigitDecimal(received.getRecvAmtWithtaxLocalCurr()));
            receive.setRecvAmtWotaxLocalCurr(NumberFormat.get2DigitDecimal(received.getRecvAmtWotaxLocalCurr()));
            return receive;
        }).collect(Collectors.toList());
        dto.setReceives(receives);
        return dto;
    }
}
