package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.CustomResponse;
import com.khajana.setting.dto.companybranch.*;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.address.Division;
import com.khajana.setting.entity.address.Upazila;
import com.khajana.setting.entity.companybranch.*;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.address.CountryRepository;
import com.khajana.setting.repository.address.DivisionRepository;
import com.khajana.setting.repository.address.UpazilaRepository;
import com.khajana.setting.repository.bank.BankBranchInfoRepository;
import com.khajana.setting.repository.bank.BankInfoRepository;
import com.khajana.setting.repository.companybranch.*;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompanyBranchService {

        @Autowired
        CompanyBranchRepository companyBranchRepository;

        @Autowired
        CompanyRepository companyRepository;
        @Autowired
        UserCredentialRepository userCredentials;
        @Autowired
        JdbcTemplate jdbcTemplate;

        @Autowired
        HouseKeepingRepository houseKeepingRepository;

        @Autowired
        CompanyBranchBankDetailRepository companyBranchBankDetailRepository;

        @Autowired
        CompanyBranchEconomyActivityRepository companyBranchEconomyActivityRepository;

        @Autowired
        CompanyBranchEconomyActivityAreaRepository companyBranchEconomyActivityAreaRepository;

        @Autowired
        CompanyBranchBankDetailService companyBranchBankDetailService;

        @Autowired
        BankInfoRepository bankInfoRepository;
        @Autowired
        BankBranchInfoRepository bankBranchInfoRepository;
        @Autowired
        CountryRepository countryRepository;
        @Autowired
        UpazilaRepository upazilaRepository;

        @Autowired
        CompanyBranchAddressDetailRepository companyBranchAddressDetailRepository;

        @Autowired
        CompanyBranchBusinessClassificationCodeRepository companyBranchBusinessClassificationCodeRepository;

        @Autowired
        DivisionRepository divisionRepository;
        // UpazilaRepository upa

        public SimplePage<CompanyBranchResponseDto> findAllCompanyBranchs(String filter, Pageable pageable) {
                Page<CompanyBranch> page;
                if (StringUtils.isNotBlank(filter)) {
                        page = companyBranchRepository.findAllCompanyBranchByBranchUnitNameContaining(filter, pageable);
                } else {
                        page = companyBranchRepository.findAll(pageable);
                }
                return new SimplePage<>(
                                page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                                page.getTotalElements(), pageable);
        }

        public CompanyBranchResponseDto findCompanyBranchById(Long id) {

                CompanyBranch newEntity = companyBranchRepository.findCompanyBranchById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("CompanyBranch", "Id", id));

                return transferToDTO(newEntity);
        }

        public ApiResponse addCompanyBranch(CompanyBranchRequestDto dto) {
                CustomUserDetails userData = ContextUser.getLoginUserData();
                try {
                        CompanyBranch companyBranch = new CompanyBranch();
                        companyBranch.setCompanyId(dto.getCompanyId());
                        companyBranch.setBranchUnitName(dto.getBranchUnitName());
                        companyBranch.setBranchUnitNameBn(dto.getBranchUnitNameBn());
                        companyBranch.setBranchUnitBinNumber(dto.getBranchUnitBinNumber());
                        companyBranch.setBranchUnitBinNumberBn(dto.getBranchUnitBinNumberBn());
                        companyBranch.setBranchUnitShortName(dto.getBranchUnitShortName());
                        companyBranch.setBranchUnitShortNameBn(dto.getBranchUnitShortNameBn());
                        companyBranch.setBranchUnitVatRegistrationType(dto.getBranchUnitVatRegistrationType());
                        companyBranch.setBranchUnitCustomOfficeArea(dto.getBranchUnitCustomOfficeArea());
                        companyBranch.setBranchUnitCustomOfficeAreaBn(dto.getBranchUnitCustomOfficeAreaBn());
                        companyBranch.setBranchUnitPhoneNumber(dto.getBranchUnitPhoneNumber());
                        companyBranch.setBranchUnitEmailAddress(dto.getBranchUnitEmailAddress());

                        companyBranch.setActive(dto.getActive());
                        companyBranch.setCreatedAt(new Date());
                        companyBranch.setCreatedBy(userData.getId());

                        CompanyBranch createdCompany = companyBranchRepository.save(companyBranch);
                        if (dto.getBankDetails() != null && !dto.getBankDetails().isEmpty()) {

                                for (CompanyBranchBankDetailRequestServiceDto companyBranchBankDetailRequestDto : dto
                                                .getBankDetails()) {
                                        CompanyBranchBankDetail companyBranchBankDetail = new CompanyBranchBankDetail();
                                        companyBranchBankDetail.setBranchId(createdCompany.getId());
                                        companyBranchBankDetail
                                                        .setBankBranchId(companyBranchBankDetailRequestDto
                                                                        .getBankBranchId());
                                        companyBranchBankDetail.setBankAccountTypeId(
                                                        companyBranchBankDetailRequestDto.getBankAccountTypeId());
                                        companyBranchBankDetail
                                                        .setCompanyAccountNumber(companyBranchBankDetailRequestDto
                                                                        .getCompanyAccountNumber());
                                        companyBranchBankDetail
                                                        .setCompanyAccountNumberBn(companyBranchBankDetailRequestDto
                                                                        .getCompanyAccountNumberBn());

                                        companyBranchBankDetail.setActive(dto.getActive());
                                        companyBranchBankDetail.setCreatedAt(new Date());
                                        companyBranchBankDetail.setCreatedBy(userData.getId());
                                        // companyBranchBankDetail.setUpdatedAt(new Date());
                                        // companyBranchBankDetail.setUpdatedBy(userData.getId());
                                        companyBranchBankDetailRepository.save(companyBranchBankDetail);
                                }
                        }
                        if (dto.getEconomicActivities() != null && !dto.getEconomicActivities().isEmpty()) {

                                for (CompanyBranchEconomicActivityRequestServiceDto companyBranchBankDetailRequestDto : dto
                                                .getEconomicActivities()) {
                                        CompanyBranchEconomicActivity companyBranchEconomicActivity = new CompanyBranchEconomicActivity();
                                        companyBranchEconomicActivity.setBranchId(createdCompany.getId());
                                        companyBranchEconomicActivity
                                                        .setEconomicActivityId(companyBranchBankDetailRequestDto
                                                                        .getEconomicActivityId());
                                        companyBranchEconomicActivity
                                                        .setSupportingDocNo(
                                                                        companyBranchBankDetailRequestDto
                                                                                        .getSupportingDocNo());
                                        companyBranchEconomicActivity
                                                        .setSupportingDocIssueDate(companyBranchBankDetailRequestDto
                                                                        .getSupportingDocIssueDate());
                                        companyBranchEconomicActivity
                                                        .setSeqNo(companyBranchBankDetailRequestDto.getSeqNo());
                                        companyBranchEconomicActivity
                                                        .setActive(companyBranchBankDetailRequestDto.getActive());

                                        companyBranchEconomicActivity.setCreatedAt(new Date());
                                        companyBranchEconomicActivity.setCreatedBy(userData.getId());
                                        // companyBranchEconomicActivity.setUpdatedAt(new Date());
                                        // companyBranchEconomicActivity.setUpdatedBy(userData.getId());

                                        companyBranchEconomyActivityRepository.save(companyBranchEconomicActivity);
                                }
                        }
                        if (dto.getAddressDetails() != null && !dto.getAddressDetails().isEmpty()) {

                                for (CompanyBranchAddressDetailRequestServiceDto companyBranchAddressDetailRequestDto : dto
                                                .getAddressDetails()) {
                                        CompanyBranchAddressDetail companyBranchAddressDetail = new CompanyBranchAddressDetail();
                                        companyBranchAddressDetail.setBranchId(createdCompany.getId());
                                        companyBranchAddressDetail.setActive(true);
                                        companyBranchAddressDetail.setAddressTypeId(
                                                        companyBranchAddressDetailRequestDto.getAddressTypeId());
                                        companyBranchAddressDetail
                                                        .setAddress(companyBranchAddressDetailRequestDto.getAddress());
                                        companyBranchAddressDetail.setDivisionId(
                                                        companyBranchAddressDetailRequestDto.getDivisionId());
                                        companyBranchAddressDetail.setUpazillaId(
                                                        companyBranchAddressDetailRequestDto.getUpazillaId());
                                        companyBranchAddressDetail
                                                        .setDistrictId(companyBranchAddressDetailRequestDto
                                                                        .getDistrictId());
                                        companyBranchAddressDetail
                                                        .setPostalCode(companyBranchAddressDetailRequestDto
                                                                        .getPostalCode());

                                        companyBranchAddressDetail.setActive(true);

                                        companyBranchAddressDetail.setCreatedAt(new Date());
                                        companyBranchAddressDetail.setCreatedBy(userData.getId());
                                        // companyBranchAddressDetail.setUpdatedAt(new Date());
                                        // companyBranchAddressDetail.setUpdatedBy(userData.getId());

                                        companyBranchAddressDetailRepository.save(companyBranchAddressDetail);
                                }
                        }
                        if (dto.getClassificationCodes() != null && !dto.getClassificationCodes().isEmpty()) {

                                for (CompanyBranchBusinessClassificationCodeRequestServiceDto companyBranchBusinessClassificationCodeRequestServiceDto : dto
                                                .getClassificationCodes()) {
                                        CompanyBranchBusinessClassificationCode companyBranchBusinessClassificationCode = new CompanyBranchBusinessClassificationCode();
                                        companyBranchBusinessClassificationCode.setBranchId(createdCompany.getId());
                                        companyBranchBusinessClassificationCode
                                                        .setHsCodeId(companyBranchBusinessClassificationCodeRequestServiceDto
                                                                        .getHsCodeId());
                                        companyBranchBusinessClassificationCode
                                                        .setCommercialDescriptionOfSupply(
                                                                        companyBranchBusinessClassificationCodeRequestServiceDto
                                                                                        .getCommercialDescriptionOfSupply());
                                        companyBranchBusinessClassificationCode.setDescriptionOfHsCode(
                                                        companyBranchBusinessClassificationCodeRequestServiceDto
                                                                        .getDescriptionOfHsCode());

                                        companyBranchBusinessClassificationCode
                                                        .setSeqNo(companyBranchBusinessClassificationCodeRequestServiceDto
                                                                        .getSeqNo());
                                        companyBranchBusinessClassificationCode
                                                        .setActive(companyBranchBusinessClassificationCodeRequestServiceDto
                                                                        .getActive());

                                        companyBranchBusinessClassificationCode.setCreatedAt(new Date());
                                        companyBranchBusinessClassificationCode.setCreatedBy(userData.getId());
                                        // companyBranchBusinessClassificationCode.setUpdatedAt(new Date());
                                        // companyBranchBusinessClassificationCode.setUpdatedBy(userData.getId());

                                        companyBranchBusinessClassificationCodeRepository
                                                        .save(companyBranchBusinessClassificationCode);
                                }
                        }
                        if (dto.getEconomicActivityAreas() != null && !dto.getEconomicActivityAreas().isEmpty()) {

                                for (CompanyBranchEconomicActivityAreaRequestServiceDto companyBranchEconomicActivityAreaRequestServiceDto : dto
                                                .getEconomicActivityAreas()) {
                                        CompanyBranchEconomicActivityArea companyBranchEconomicActivityArea = new CompanyBranchEconomicActivityArea();
                                        companyBranchEconomicActivityArea.setBranchId(createdCompany.getId());
                                        companyBranchEconomicActivityArea.setEconomicActivityAreaId(
                                                        companyBranchEconomicActivityAreaRequestServiceDto
                                                                        .getEconomicActivityAreaId());
                                        companyBranchEconomicActivityArea
                                                        .setOtherDetail(companyBranchEconomicActivityAreaRequestServiceDto
                                                                        .getOtherDetail());
                                        companyBranchEconomicActivityArea
                                                        .setSeqNo(companyBranchEconomicActivityAreaRequestServiceDto
                                                                        .getSeqNo());
                                        companyBranchEconomicActivityArea
                                                        .setActive(companyBranchEconomicActivityAreaRequestServiceDto
                                                                        .getActive());

                                        companyBranchEconomicActivityArea.setCreatedAt(new Date());
                                        companyBranchEconomicActivityArea.setCreatedBy(userData.getId());
                                        // companyBranchEconomicActivityArea.setUpdatedAt(new Date());
                                        // companyBranchEconomicActivityArea.setUpdatedBy(userData.getId());

                                        companyBranchEconomyActivityAreaRepository
                                                        .save(companyBranchEconomicActivityArea);
                                }

                        }
                        return new ApiResponse(200, "ok", transferToDTO(companyBranch));

                } catch (Exception e) {
                        return new ApiResponse(500, e.getMessage(), "error");
                }
        }

        public ApiResponse updateCompanyBranch(Long id, CompanyBranchRequestDto dto) {
                // Read user id from JWT Token
                try {
                        CompanyBranch newEntity = companyBranchRepository.save(transferToEntity(id, dto));
                        return new ApiResponse(200, "OK", transferToDTO(newEntity));

                } catch (Exception e) {
                        return new ApiResponse(500, e.getMessage(), "error");
                }
        }

        public List<HouseKeeping> companyBranchDropDown() {
                List<HouseKeeping> result = houseKeepingRepository.companyDropDown();
                return result;
        }

        public void deleteCompanyBranch(Long id) {
                companyBranchRepository.deleteCompanyBranchById(id);
        }

        private CompanyBranch transferToEntity(Long id, CompanyBranchRequestDto dto) {
                CompanyBranch companyBranch = new CompanyBranch();
                CustomUserDetails userData = ContextUser.getLoginUserData();

                if (id != null && id > 0) {
                        companyBranch.setId(id);
                        companyBranch.setCompanyId(dto.getCompanyId());
                        companyBranch.setBranchUnitName(dto.getBranchUnitName());
                        companyBranch.setBranchUnitNameBn(dto.getBranchUnitNameBn());
                        companyBranch.setBranchUnitBinNumber(dto.getBranchUnitBinNumber());
                        companyBranch.setBranchUnitBinNumberBn(dto.getBranchUnitBinNumberBn());
                        companyBranch.setBranchUnitShortName(dto.getBranchUnitShortName());
                        companyBranch.setBranchUnitShortNameBn(dto.getBranchUnitShortNameBn());
                        companyBranch.setBranchUnitVatRegistrationType(dto.getBranchUnitVatRegistrationType());
                        companyBranch.setBranchUnitCustomOfficeArea(dto.getBranchUnitCustomOfficeArea());
                        companyBranch.setBranchUnitCustomOfficeAreaBn(dto.getBranchUnitCustomOfficeAreaBn());
                        companyBranch.setBranchUnitPhoneNumber(dto.getBranchUnitPhoneNumber());
                        companyBranch.setBranchUnitEmailAddress(dto.getBranchUnitEmailAddress());
                        companyBranch.setActive(dto.getActive());
                        companyBranch.setUpdatedAt(new Date());
                        companyBranch.setUpdatedBy(userData.getId());
                        CompanyBranch createdCompany = companyBranchRepository.save(companyBranch);
                        if (dto.getBankDetails() != null && !dto.getBankDetails().isEmpty()) {

                                for (CompanyBranchBankDetailRequestServiceDto companyBranchBankDetailRequestDto : dto
                                                .getBankDetails()) {
                                        CompanyBranchBankDetail companyBranchBankDetail = new CompanyBranchBankDetail();
                                        if (companyBranchBankDetailRequestDto.getId() != null) {
                                                companyBranchBankDetail.setBranchId(createdCompany.getId());
                                                companyBranchBankDetail.setBankBranchId(
                                                                companyBranchBankDetailRequestDto.getBankBranchId());
                                                companyBranchBankDetail
                                                                .setBankAccountTypeId(companyBranchBankDetailRequestDto
                                                                                .getBankAccountTypeId());
                                                companyBranchBankDetail
                                                                .setCompanyAccountNumber(
                                                                                companyBranchBankDetailRequestDto
                                                                                                .getCompanyAccountNumber());
                                                companyBranchBankDetail
                                                                .setCompanyAccountNumberBn(
                                                                                companyBranchBankDetailRequestDto
                                                                                                .getCompanyAccountNumberBn());

                                                companyBranchBankDetail.setActive(dto.getActive());
                                                companyBranchBankDetail.setCreatedAt(new Date());
                                                companyBranchBankDetail.setCreatedBy(userData.getId());
                                                companyBranchBankDetail.setUpdatedAt(new Date());
                                                companyBranchBankDetail.setUpdatedBy(userData.getId());
                                        } else {
                                                companyBranchBankDetail
                                                                .setId(companyBranchBankDetailRequestDto.getId());
                                                companyBranchBankDetail.setBranchId(createdCompany.getId());
                                                companyBranchBankDetail.setBankBranchId(
                                                                companyBranchBankDetailRequestDto.getBankBranchId());
                                                companyBranchBankDetail
                                                                .setBankAccountTypeId(companyBranchBankDetailRequestDto
                                                                                .getBankAccountTypeId());
                                                companyBranchBankDetail
                                                                .setCompanyAccountNumber(
                                                                                companyBranchBankDetailRequestDto
                                                                                                .getCompanyAccountNumber());
                                                companyBranchBankDetail
                                                                .setCompanyAccountNumberBn(
                                                                                companyBranchBankDetailRequestDto
                                                                                                .getCompanyAccountNumberBn());

                                                companyBranchBankDetail.setActive(dto.getActive());
                                                // companyBranchBankDetail.setCreatedAt(new Date());
                                                // companyBranchBankDetail.setCreatedBy(userData.getId());
                                                companyBranchBankDetail.setUpdatedAt(new Date());
                                                companyBranchBankDetail.setUpdatedBy(userData.getId());
                                        }
                                        companyBranchBankDetailRepository.save(companyBranchBankDetail);
                                }
                        }
                        if (dto.getEconomicActivities() != null && !dto.getEconomicActivities().isEmpty()) {

                                for (CompanyBranchEconomicActivityRequestServiceDto companyBranchBankDetailRequestDto : dto
                                                .getEconomicActivities()) {
                                        CompanyBranchEconomicActivity companyBranchEconomicActivity = new CompanyBranchEconomicActivity();
                                        if (companyBranchBankDetailRequestDto.getId() != null) {
                                                companyBranchEconomicActivity.setBranchId(createdCompany.getId());
                                                companyBranchEconomicActivity
                                                                .setEconomicActivityId(companyBranchBankDetailRequestDto
                                                                                .getEconomicActivityId());
                                                companyBranchEconomicActivity
                                                                .setSupportingDocNo(companyBranchBankDetailRequestDto
                                                                                .getSupportingDocNo());
                                                companyBranchEconomicActivity
                                                                .setSupportingDocIssueDate(
                                                                                companyBranchBankDetailRequestDto
                                                                                                .getSupportingDocIssueDate());
                                                companyBranchEconomicActivity
                                                                .setSeqNo(companyBranchBankDetailRequestDto.getSeqNo());
                                                companyBranchEconomicActivity
                                                                .setActive(companyBranchBankDetailRequestDto
                                                                                .getActive());

                                                companyBranchEconomicActivity.setCreatedAt(new Date());
                                                companyBranchEconomicActivity.setCreatedBy(userData.getId());
                                                companyBranchEconomicActivity.setUpdatedAt(new Date());
                                                companyBranchEconomicActivity.setUpdatedBy(userData.getId());
                                        } else {
                                                companyBranchEconomicActivity
                                                                .setId(companyBranchBankDetailRequestDto.getId());
                                                companyBranchEconomicActivity
                                                                .setId(companyBranchBankDetailRequestDto.getId());
                                                companyBranchEconomicActivity.setBranchId(createdCompany.getId());
                                                companyBranchEconomicActivity
                                                                .setEconomicActivityId(companyBranchBankDetailRequestDto
                                                                                .getEconomicActivityId());
                                                companyBranchEconomicActivity
                                                                .setSupportingDocNo(companyBranchBankDetailRequestDto
                                                                                .getSupportingDocNo());
                                                companyBranchEconomicActivity
                                                                .setSupportingDocIssueDate(
                                                                                companyBranchBankDetailRequestDto
                                                                                                .getSupportingDocIssueDate());
                                                companyBranchEconomicActivity
                                                                .setSeqNo(companyBranchBankDetailRequestDto.getSeqNo());
                                                companyBranchEconomicActivity
                                                                .setActive(companyBranchBankDetailRequestDto
                                                                                .getActive());

                                                // companyBranchEconomicActivity.setCreatedAt(new Date());
                                                // companyBranchEconomicActivity.setCreatedBy(userData.getId());
                                                companyBranchEconomicActivity.setUpdatedAt(new Date());
                                                companyBranchEconomicActivity.setUpdatedBy(userData.getId());

                                        }

                                        companyBranchEconomyActivityRepository.save(companyBranchEconomicActivity);
                                }
                        }
                        if (dto.getAddressDetails() != null && !dto.getAddressDetails().isEmpty()) {

                                for (CompanyBranchAddressDetailRequestServiceDto companyBranchAddressDetailRequestDto : dto
                                                .getAddressDetails()) {
                                        CompanyBranchAddressDetail companyBranchAddressDetail = new CompanyBranchAddressDetail();
                                        if (companyBranchAddressDetailRequestDto.getId() != null) {
                                                companyBranchAddressDetail.setBranchId(createdCompany.getId());
                                                companyBranchAddressDetail
                                                                .setAddressTypeId(companyBranchAddressDetailRequestDto
                                                                                .getAddressTypeId());
                                                companyBranchAddressDetail.setAddress(
                                                                companyBranchAddressDetailRequestDto.getAddress());
                                                companyBranchAddressDetail.setDivisionId(
                                                                companyBranchAddressDetailRequestDto.getDivisionId());
                                                companyBranchAddressDetail.setUpazillaId(
                                                                companyBranchAddressDetailRequestDto.getUpazillaId());
                                                companyBranchAddressDetail.setDistrictId(
                                                                companyBranchAddressDetailRequestDto.getDistrictId());
                                                companyBranchAddressDetail.setPostalCode(
                                                                companyBranchAddressDetailRequestDto.getPostalCode());

                                                companyBranchAddressDetail
                                                                .setActive(true);

                                                companyBranchAddressDetail.setCreatedAt(new Date());
                                                companyBranchAddressDetail.setCreatedBy(userData.getId());
                                                companyBranchAddressDetail.setUpdatedAt(new Date());
                                                companyBranchAddressDetail.setUpdatedBy(userData.getId());
                                        } else {
                                                companyBranchAddressDetail
                                                                .setId(companyBranchAddressDetailRequestDto.getId());
                                                companyBranchAddressDetail.setBranchId(createdCompany.getId());
                                                companyBranchAddressDetail
                                                                .setAddressTypeId(companyBranchAddressDetailRequestDto
                                                                                .getAddressTypeId());
                                                // companyBranchAddressDetail.setHoldingNo(
                                                // companyBranchAddressDetailRequestDto.getHoldingNo());
                                                // companyBranchAddressDetail
                                                // .setRoadNo(companyBranchAddressDetailRequestDto
                                                // .getRoadNo());
                                                // companyBranchAddressDetail
                                                // .setPoliceStationId(companyBranchAddressDetailRequestDto
                                                // .getPoliceStationId());
                                                companyBranchAddressDetail.setDistrictId(
                                                                companyBranchAddressDetailRequestDto.getDistrictId());
                                                companyBranchAddressDetail.setPostalCode(
                                                                companyBranchAddressDetailRequestDto.getPostalCode());

                                                companyBranchAddressDetail
                                                                .setActive(true);
                                                companyBranchAddressDetail.setUpdatedAt(new Date());
                                                companyBranchAddressDetail.setUpdatedBy(userData.getId());
                                        }

                                        companyBranchAddressDetailRepository.save(companyBranchAddressDetail);
                                }
                        }
                        if (dto.getClassificationCodes() != null && !dto.getClassificationCodes().isEmpty()) {

                                for (CompanyBranchBusinessClassificationCodeRequestServiceDto companyBranchBusinessClassificationCodeRequestServiceDto : dto
                                                .getClassificationCodes()) {
                                        CompanyBranchBusinessClassificationCode companyBranchBusinessClassificationCode = new CompanyBranchBusinessClassificationCode();

                                        if (companyBranchBusinessClassificationCodeRequestServiceDto.getId() != null) {
                                                companyBranchBusinessClassificationCode
                                                                .setBranchId(createdCompany.getId());
                                                companyBranchBusinessClassificationCode
                                                                .setHsCodeId(companyBranchBusinessClassificationCodeRequestServiceDto
                                                                                .getHsCodeId());
                                                companyBranchBusinessClassificationCode
                                                                .setCommercialDescriptionOfSupply(
                                                                                companyBranchBusinessClassificationCodeRequestServiceDto
                                                                                                .getCommercialDescriptionOfSupply());
                                                companyBranchBusinessClassificationCode.setDescriptionOfHsCode(
                                                                companyBranchBusinessClassificationCodeRequestServiceDto
                                                                                .getDescriptionOfHsCode());

                                                companyBranchBusinessClassificationCode
                                                                .setSeqNo(companyBranchBusinessClassificationCodeRequestServiceDto
                                                                                .getSeqNo());
                                                companyBranchBusinessClassificationCode
                                                                .setActive(companyBranchBusinessClassificationCodeRequestServiceDto
                                                                                .getActive());

                                                companyBranchBusinessClassificationCode.setCreatedAt(new Date());
                                                companyBranchBusinessClassificationCode.setCreatedBy(userData.getId());
                                                companyBranchBusinessClassificationCode.setUpdatedAt(new Date());
                                                companyBranchBusinessClassificationCode.setUpdatedBy(userData.getId());
                                        } else {
                                                companyBranchBusinessClassificationCode
                                                                .setId(companyBranchBusinessClassificationCodeRequestServiceDto
                                                                                .getId());
                                                companyBranchBusinessClassificationCode
                                                                .setBranchId(createdCompany.getId());
                                                companyBranchBusinessClassificationCode
                                                                .setHsCodeId(companyBranchBusinessClassificationCodeRequestServiceDto
                                                                                .getHsCodeId());
                                                companyBranchBusinessClassificationCode
                                                                .setCommercialDescriptionOfSupply(
                                                                                companyBranchBusinessClassificationCodeRequestServiceDto
                                                                                                .getCommercialDescriptionOfSupply());
                                                companyBranchBusinessClassificationCode.setDescriptionOfHsCode(
                                                                companyBranchBusinessClassificationCodeRequestServiceDto
                                                                                .getDescriptionOfHsCode());

                                                companyBranchBusinessClassificationCode
                                                                .setSeqNo(companyBranchBusinessClassificationCodeRequestServiceDto
                                                                                .getSeqNo());
                                                companyBranchBusinessClassificationCode
                                                                .setActive(companyBranchBusinessClassificationCodeRequestServiceDto
                                                                                .getActive());

                                                // companyBranchBusinessClassificationCode.setCreatedAt(new Date());
                                                // companyBranchBusinessClassificationCode.setCreatedBy(userData.getId());
                                                companyBranchBusinessClassificationCode.setUpdatedAt(new Date());
                                                companyBranchBusinessClassificationCode.setUpdatedBy(userData.getId());
                                        }

                                        companyBranchBusinessClassificationCodeRepository
                                                        .save(companyBranchBusinessClassificationCode);
                                }
                        }
                        if (dto.getEconomicActivityAreas() != null && !dto.getEconomicActivityAreas().isEmpty()) {

                                for (CompanyBranchEconomicActivityAreaRequestServiceDto companyBranchEconomicActivityAreaRequestServiceDto : dto
                                                .getEconomicActivityAreas()) {
                                        CompanyBranchEconomicActivityArea companyBranchEconomicActivityArea = new CompanyBranchEconomicActivityArea();
                                        if (companyBranchEconomicActivityAreaRequestServiceDto.getId() != null) {
                                                companyBranchEconomicActivityArea.setBranchId(createdCompany.getId());
                                                companyBranchEconomicActivityArea.setEconomicActivityAreaId(
                                                                companyBranchEconomicActivityAreaRequestServiceDto
                                                                                .getEconomicActivityAreaId());
                                                companyBranchEconomicActivityArea
                                                                .setOtherDetail(companyBranchEconomicActivityAreaRequestServiceDto
                                                                                .getOtherDetail());
                                                companyBranchEconomicActivityArea
                                                                .setSeqNo(companyBranchEconomicActivityAreaRequestServiceDto
                                                                                .getSeqNo());
                                                companyBranchEconomicActivityArea
                                                                .setActive(companyBranchEconomicActivityAreaRequestServiceDto
                                                                                .getActive());

                                                companyBranchEconomicActivityArea.setCreatedAt(new Date());
                                                companyBranchEconomicActivityArea.setCreatedBy(userData.getId());
                                                companyBranchEconomicActivityArea.setUpdatedAt(new Date());
                                                companyBranchEconomicActivityArea.setUpdatedBy(userData.getId());
                                        } else {
                                                companyBranchEconomicActivityArea.setId(
                                                                companyBranchEconomicActivityAreaRequestServiceDto
                                                                                .getId());
                                                companyBranchEconomicActivityArea.setBranchId(createdCompany.getId());
                                                companyBranchEconomicActivityArea.setEconomicActivityAreaId(
                                                                companyBranchEconomicActivityAreaRequestServiceDto
                                                                                .getEconomicActivityAreaId());
                                                companyBranchEconomicActivityArea
                                                                .setOtherDetail(companyBranchEconomicActivityAreaRequestServiceDto
                                                                                .getOtherDetail());
                                                companyBranchEconomicActivityArea
                                                                .setSeqNo(companyBranchEconomicActivityAreaRequestServiceDto
                                                                                .getSeqNo());
                                                companyBranchEconomicActivityArea
                                                                .setActive(companyBranchEconomicActivityAreaRequestServiceDto
                                                                                .getActive());

                                                // companyBranchEconomicActivityArea.setCreatedAt(new Date());
                                                // companyBranchEconomicActivityArea.setCreatedBy(userData.getId());
                                                companyBranchEconomicActivityArea.setUpdatedAt(new Date());
                                                companyBranchEconomicActivityArea.setUpdatedBy(userData.getId());
                                        }

                                        companyBranchEconomyActivityAreaRepository
                                                        .save(companyBranchEconomicActivityArea);
                                }
                        }

                        return companyBranch;
                } else {

                        companyBranch.setCompanyId(dto.getCompanyId());
                        companyBranch.setBranchUnitName(dto.getBranchUnitName());
                        companyBranch.setBranchUnitNameBn(dto.getBranchUnitNameBn());
                        companyBranch.setBranchUnitBinNumber(dto.getBranchUnitBinNumber());
                        companyBranch.setBranchUnitBinNumberBn(dto.getBranchUnitBinNumberBn());
                        companyBranch.setBranchUnitShortName(dto.getBranchUnitShortName());
                        companyBranch.setBranchUnitShortNameBn(dto.getBranchUnitShortNameBn());
                        companyBranch.setBranchUnitVatRegistrationType(dto.getBranchUnitVatRegistrationType());
                        companyBranch.setBranchUnitCustomOfficeArea(dto.getBranchUnitCustomOfficeArea());
                        companyBranch.setBranchUnitCustomOfficeAreaBn(dto.getBranchUnitCustomOfficeAreaBn());
                        companyBranch.setBranchUnitPhoneNumber(dto.getBranchUnitPhoneNumber());
                        companyBranch.setBranchUnitEmailAddress(dto.getBranchUnitEmailAddress());

                        companyBranch.setActive(dto.getActive());
                        companyBranch.setCreatedAt(new Date());
                        companyBranch.setCreatedBy(userData.getId());

                        CompanyBranch createdCompany = companyBranchRepository.save(companyBranch);

                        for (@Valid
                        CompanyBranchBankDetailRequestServiceDto companyBranchBankDetailRequestDto : dto
                                        .getBankDetails()) {
                                CompanyBranchBankDetail companyBranchBankDetail = new CompanyBranchBankDetail();
                                companyBranchBankDetail.setBranchId(createdCompany.getId());
                                companyBranchBankDetail
                                                .setBankBranchId(companyBranchBankDetailRequestDto.getBankBranchId());
                                companyBranchBankDetail.setBankAccountTypeId(
                                                companyBranchBankDetailRequestDto.getBankAccountTypeId());
                                companyBranchBankDetail
                                                .setCompanyAccountNumber(companyBranchBankDetailRequestDto
                                                                .getCompanyAccountNumber());
                                companyBranchBankDetail
                                                .setCompanyAccountNumberBn(companyBranchBankDetailRequestDto
                                                                .getCompanyAccountNumberBn());
                                companyBranchBankDetail.setActive(companyBranchBankDetailRequestDto.getActive());
                        }

                        for (@Valid
                        CompanyBranchEconomicActivityRequestServiceDto companyBranchBankDetailRequestDto : dto
                                        .getEconomicActivities()) {
                                CompanyBranchEconomicActivity companyBranchEconomicActivity = new CompanyBranchEconomicActivity();
                                companyBranchEconomicActivity.setBranchId(createdCompany.getId());
                                companyBranchEconomicActivity
                                                .setEconomicActivityId(companyBranchBankDetailRequestDto
                                                                .getEconomicActivityId());
                                companyBranchEconomicActivity
                                                .setSupportingDocNo(
                                                                companyBranchBankDetailRequestDto.getSupportingDocNo());
                                companyBranchEconomicActivity
                                                .setSupportingDocIssueDate(companyBranchBankDetailRequestDto
                                                                .getSupportingDocIssueDate());
                                companyBranchEconomicActivity.setSeqNo(companyBranchBankDetailRequestDto.getSeqNo());
                                companyBranchEconomicActivity.setActive(companyBranchBankDetailRequestDto.getActive());
                        }

                        return companyBranch;
                }

        }

        private CompanyBranchResponseDto transferToDTO(CompanyBranch entity) {
                CompanyBranchResponseDto dto = new CompanyBranchResponseDto();
                dto.setId(entity.getId());
                if (entity.getCompanyId() != null) {
                        Company company = companyRepository.findCompanyById(entity.getCompanyId()).orElse(null);
                        dto.setCompanyName(company.getCompName());
                        dto.setCompanyNameBn(company.getCompNameBn());
                }
                dto.setCompanyId(entity.getCompanyId());
                dto.setBranchUnitName(entity.getBranchUnitName());
                dto.setBranchUnitNameBn(entity.getBranchUnitNameBn());
                dto.setBranchUnitBinNumber(entity.getBranchUnitBinNumber());
                dto.setBranchUnitBinNumberBn(entity.getBranchUnitBinNumberBn());
                dto.setBranchUnitShortName(entity.getBranchUnitShortName());
                dto.setBranchUnitShortNameBn(entity.getBranchUnitShortNameBn());
                dto.setBranchUnitVatRegistrationType(entity.getBranchUnitVatRegistrationType());
                dto.setBranchUnitCustomOfficeArea(entity.getBranchUnitCustomOfficeArea());
                dto.setBranchUnitCustomOfficeAreaBn(entity.getBranchUnitCustomOfficeAreaBn());
                dto.setBranchUnitPhoneNumber(entity.getBranchUnitPhoneNumber());
                dto.setBranchUnitEmailAddress(entity.getBranchUnitEmailAddress());

                dto.setActive(entity.getActive());
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

                if (entity.getCompanyBranchAddressDetails() != null
                                && !entity.getCompanyBranchAddressDetails().isEmpty()) {
                        List<CompanyBranchAddressDetailResponseDto> companyBranchAddressDetails = entity
                                        .getCompanyBranchAddressDetails().stream().map(companyBranchAddress -> {
                                                CompanyBranchAddressDetailResponseDto companyBranchAddressDetail = new CompanyBranchAddressDetailResponseDto();
                                                companyBranchAddressDetail.setId(companyBranchAddress.getId());
                                                companyBranchAddressDetail
                                                                .setBranchId(companyBranchAddress.getBranchId());
                                                companyBranchAddressDetail.setAddressTypeId(
                                                                companyBranchAddress.getAddressTypeId());
                                                companyBranchAddressDetail
                                                                .setDivisionId(companyBranchAddress.getDivisionId());
                                                companyBranchAddressDetail
                                                                .setUpazillaId(companyBranchAddress.getUpazillaId());
                                                if (companyBranchAddress.getDivisionId() != null) {
                                                        Division division = divisionRepository
                                                                        .findById(companyBranchAddress.getDivisionId())
                                                                        .orElse(null);
                                                        if (division != null && division.getName() != null) {
                                                                companyBranchAddressDetail
                                                                                .setDivisionName(division.getName());
                                                        }

                                                }
                                                if (companyBranchAddress.getUpazillaId() != null) {
                                                        Upazila upazilla = upazilaRepository
                                                                        .findById(companyBranchAddress.getUpazillaId())
                                                                        .orElse(null);
                                                        if (upazilla != null && upazilla.getName() != null) {
                                                                companyBranchAddressDetail
                                                                                .setUpazillaName(upazilla.getName());
                                                        }

                                                }
                                                companyBranchAddressDetail
                                                                .setDistrictId(companyBranchAddress.getDistrictId());
                                                companyBranchAddressDetail
                                                                .setPostalCode(companyBranchAddress.getPostalCode());
                                                if (companyBranchAddress.getDistrict().getDivision()
                                                                .getCountry() != null) {
                                                        companyBranchAddressDetail.setCountryId(
                                                                        companyBranchAddress.getDistrict().getDivision()
                                                                                        .getCountry().getId());
                                                        companyBranchAddressDetail.setCountryName(
                                                                        companyBranchAddress.getDistrict().getDivision()
                                                                                        .getCountry().getName());
                                                }
                                                if (companyBranchAddress.getCompanyBranch() != null
                                                                && companyBranchAddress.getCompanyBranch()
                                                                                .getBranchUnitName() != null) {
                                                        companyBranchAddressDetail.setBranchName(companyBranchAddress
                                                                        .getCompanyBranch().getBranchUnitName());
                                                }
                                                if (companyBranchAddress.getAddressType() != null
                                                                && companyBranchAddress.getAddressType()
                                                                                .getName() != null) {
                                                        companyBranchAddressDetail
                                                                        .setAddressTypeName(companyBranchAddress
                                                                                        .getAddressType().getName());
                                                }
                                                if (companyBranchAddress.getDistrict() != null && companyBranchAddress
                                                                .getDistrict().getName() != null) {
                                                        companyBranchAddressDetail.setDistrictName(
                                                                        companyBranchAddress.getDistrict().getName());
                                                }
                                                companyBranchAddressDetail
                                                                .setAddress(companyBranchAddress.getAddress());
                                                companyBranchAddressDetail.setActive(companyBranchAddress.getActive());
                                                return companyBranchAddressDetail;
                                        }).collect(Collectors.toList());
                        dto.setAddressDetails(companyBranchAddressDetails);
                }

                if (entity.getCompanyBranchBankDetails() != null && !entity.getCompanyBranchBankDetails().isEmpty()) {
                        List<CompanyBranchBankDetailResponseDto> companyBranchBankDetails = entity
                                        .getCompanyBranchBankDetails()
                                        .stream().map(companyBranchBank -> {
                                                CompanyBranchBankDetailResponseDto companyBranchBankDetail = new CompanyBranchBankDetailResponseDto();
                                                companyBranchBankDetail.setId(companyBranchBank.getId());
                                                companyBranchBankDetail.setBranchId(companyBranchBank.getBranchId());
                                                companyBranchBankDetail
                                                                .setBankBranchId(companyBranchBank.getBankBranchId());
                                                companyBranchBankDetail.setBankAccountTypeId(
                                                                companyBranchBank.getBankAccountTypeId());
                                                companyBranchBankDetail.setCompanyAccountNumber(
                                                                companyBranchBank.getCompanyAccountNumber());
                                                companyBranchBankDetail.setCompanyAccountNumberBn(
                                                                companyBranchBank.getCompanyAccountNumberBn());
                                                companyBranchBankDetail.setActive(companyBranchBank.getActive());
                                                if (companyBranchBank.getCompanyBranch() != null && companyBranchBank
                                                                .getCompanyBranch().getBranchUnitName() != null) {
                                                        companyBranchBankDetail.setBranchName(companyBranchBank
                                                                        .getCompanyBranch().getBranchUnitName());
                                                }
                                                if (companyBranchBank.getBankBranchInfo().getBankInfo() != null) {
                                                        companyBranchBankDetail.setBankId(companyBranchBank
                                                                        .getBankBranchInfo().getBankInfo().getId());
                                                        companyBranchBankDetail.setBankName(
                                                                        companyBranchBank.getBankBranchInfo()
                                                                                        .getBankInfo().getBankName());
                                                }
                                                if (companyBranchBank.getBankBranchInfo() != null && companyBranchBank
                                                                .getBankBranchInfo().getBankBranchName() != null) {
                                                        companyBranchBankDetail.setBankBanchName(companyBranchBank
                                                                        .getBankBranchInfo().getBankBranchName());
                                                }
                                                if (companyBranchBank.getBankAccountType() != null
                                                                && companyBranchBank.getBankAccountType()
                                                                                .getBankAccountTypeName() != null) {
                                                        companyBranchBankDetail.setBankAccountTypeName(companyBranchBank
                                                                        .getBankAccountType().getBankAccountTypeName());
                                                }
                                                return companyBranchBankDetail;
                                        }).collect(Collectors.toList());
                        dto.setBankDetails(companyBranchBankDetails);
                }
                if (entity.getCompanyBranchBusinessClassificationCodes() != null
                                && !entity.getCompanyBranchBusinessClassificationCodes().isEmpty()) {
                        List<CompanyBranchBusinessClassificationCodeResponseDto> companyBranchBusinessClassificationCodes = entity
                                        .getCompanyBranchBusinessClassificationCodes().stream()
                                        .map(companyBranchBusinessCode -> {
                                                CompanyBranchBusinessClassificationCodeResponseDto companyBranchBusinessClassificationCode = new CompanyBranchBusinessClassificationCodeResponseDto();
                                                companyBranchBusinessClassificationCode
                                                                .setId(companyBranchBusinessCode.getId());
                                                companyBranchBusinessClassificationCode.setId(entity.getId());
                                                companyBranchBusinessClassificationCode
                                                                .setBranchId(companyBranchBusinessCode.getBranchId());
                                                companyBranchBusinessClassificationCode
                                                                .setHsCodeId(companyBranchBusinessCode.getHsCodeId());
                                                companyBranchBusinessClassificationCode
                                                                .setCommercialDescriptionOfSupply(
                                                                                companyBranchBusinessCode
                                                                                                .getCommercialDescriptionOfSupply());
                                                companyBranchBusinessClassificationCode.setDescriptionOfHsCode(
                                                                companyBranchBusinessCode.getDescriptionOfHsCode());
                                                companyBranchBusinessClassificationCode
                                                                .setSeqNo(NumberFormat.get2DigitDecimal(
                                                                                companyBranchBusinessCode.getSeqNo()));
                                                companyBranchBusinessClassificationCode
                                                                .setActive(companyBranchBusinessCode.getActive());
                                                companyBranchBusinessClassificationCode
                                                                .setActive(companyBranchBusinessCode.getActive());
                                                if (companyBranchBusinessCode.getCompanyBranch() != null
                                                                && companyBranchBusinessCode.getCompanyBranch()
                                                                                .getBranchUnitName() != null) {
                                                        companyBranchBusinessClassificationCode.setBranchName(
                                                                        companyBranchBusinessCode.getCompanyBranch()
                                                                                        .getBranchUnitName());
                                                }
                                                return companyBranchBusinessClassificationCode;
                                        }).collect(Collectors.toList());
                        dto.setClassificationCodes(companyBranchBusinessClassificationCodes);
                }
                if (entity.getCompanyBranchEconomicActivityAreas() != null
                                && !entity.getCompanyBranchEconomicActivityAreas().isEmpty()) {
                        List<CompanyBranchEconomicActivityAreaResponseDto> companyBranchEconomicActivityAreas = entity
                                        .getCompanyBranchEconomicActivityAreas().stream()
                                        .map(companyBranchEcoAcArea -> {
                                                CompanyBranchEconomicActivityAreaResponseDto companyBranchEconomicActivityArea = new CompanyBranchEconomicActivityAreaResponseDto();
                                                companyBranchEconomicActivityArea.setId(companyBranchEcoAcArea.getId());
                                                companyBranchEconomicActivityArea
                                                                .setBranchId(companyBranchEcoAcArea.getBranchId());
                                                companyBranchEconomicActivityArea.setEconomicActivityAreaId(
                                                                companyBranchEcoAcArea.getEconomicActivityAreaId());
                                                companyBranchEconomicActivityArea.setOtherDetail(
                                                                companyBranchEcoAcArea.getOtherDetail());
                                                companyBranchEconomicActivityArea
                                                                .setSeqNo(NumberFormat.get2DigitDecimal(
                                                                                companyBranchEcoAcArea.getSeqNo()));
                                                companyBranchEconomicActivityArea
                                                                .setActive(companyBranchEcoAcArea.getActive());
                                                if (companyBranchEcoAcArea.getCompanyBranch() != null
                                                                && companyBranchEcoAcArea.getCompanyBranch()
                                                                                .getBranchUnitName() != null) {
                                                        companyBranchEconomicActivityArea.setBranchName(
                                                                        companyBranchEcoAcArea.getCompanyBranch()
                                                                                        .getBranchUnitName());
                                                }
                                                if (companyBranchEcoAcArea.getEconomicActivityAreaEntity() != null
                                                                && companyBranchEcoAcArea
                                                                                .getEconomicActivityAreaEntity()
                                                                                .getEconomicActivityName() != null) {
                                                        companyBranchEconomicActivityArea.setEconomicActivityAreaName(
                                                                        companyBranchEcoAcArea
                                                                                        .getEconomicActivityAreaEntity()
                                                                                        .getEconomicActivityName());
                                                }
                                                return companyBranchEconomicActivityArea;
                                        }).collect(Collectors.toList());
                        dto.setEconomicActivityAreas(companyBranchEconomicActivityAreas);
                }
                if (entity.getCompanyBranchEconomicActivities() != null
                                && !entity.getCompanyBranchEconomicActivities().isEmpty()) {
                        List<CompanyBranchEconomicActivityResponseDto> companyBranchEconomicActivities = entity
                                        .getCompanyBranchEconomicActivities().stream()
                                        .map(companyBranchEcoActivity -> {
                                                CompanyBranchEconomicActivityResponseDto companyBranchEconomicActivity = new CompanyBranchEconomicActivityResponseDto();
                                                companyBranchEconomicActivity
                                                                .setId(companyBranchEcoActivity.getId());
                                                companyBranchEconomicActivity.setId(companyBranchEcoActivity.getId());
                                                companyBranchEconomicActivity
                                                                .setBranchId(companyBranchEcoActivity.getBranchId());
                                                companyBranchEconomicActivity.setEconomicActivityId(
                                                                companyBranchEcoActivity.getEconomicActivityId());
                                                companyBranchEconomicActivity.setSupportingDocNo(
                                                                companyBranchEcoActivity.getSupportingDocNo());
                                                companyBranchEconomicActivity.setSupportingDocIssueDate(
                                                                DateUtil.convertDateToString(companyBranchEcoActivity
                                                                                .getSupportingDocIssueDate(),
                                                                                ConstantValue.OUT_GOING_DATE_PATTERN));
                                                companyBranchEconomicActivity
                                                                .setSeqNo(NumberFormat.get2DigitDecimal(
                                                                                companyBranchEcoActivity.getSeqNo()));
                                                companyBranchEconomicActivity
                                                                .setActive(companyBranchEcoActivity.getActive());
                                                if (companyBranchEcoActivity.getCompanyBranch() != null
                                                                && companyBranchEcoActivity.getCompanyBranch()
                                                                                .getBranchUnitName() != null) {
                                                        companyBranchEconomicActivity.setBranchName(
                                                                        companyBranchEcoActivity.getCompanyBranch()
                                                                                        .getBranchUnitName());
                                                }
                                                if (companyBranchEcoActivity.getEconomyActivity() != null
                                                                && companyBranchEcoActivity.getEconomyActivity()
                                                                                .getEconomicActivityName() != null) {
                                                        companyBranchEconomicActivity.setEconomicActivityName(
                                                                        companyBranchEcoActivity.getEconomyActivity()
                                                                                        .getEconomicActivityName());
                                                }
                                                return companyBranchEconomicActivity;
                                        }).collect(Collectors.toList());
                        dto.setEconomicActivities(companyBranchEconomicActivities);
                }

                return dto;
        }

        // Service class Using SP Start

        private String getUserNameById(Long userId) {

                User createdByUser = userCredentials.findById(userId).orElse(null);
                return createdByUser != null ? createdByUser.getName() : null;
        }

        public CustomResponse getAllCompanyBrunchIndexByIdUsingSp(int prodTypeId) {

                CustomResponse response = new CustomResponse();
                String sql = "EXEC [dbo].[sp_get_3B_company_brunch_details_list_by_id] @id = ?";
                List<Map<String, Object>> content = jdbcTemplate.queryForList(sql, prodTypeId);

                // todays trying start
                for (Map<String, Object> entry : content) {
                        // Update "createdBy" and "updatedBy" fields with user names
                        entry.put("createdBy", getUserNameById((Long) entry.get("createdBy")));
                        entry.put("updatedBy", getUserNameById((Long) entry.get("updatedBy")));

                        entry.put("createdAt", DateUtilForSp.formatToCustomString((Date) entry.get("createdAt")));
                        entry.put("updatedAt", DateUtilForSp.formatToCustomString((Date) entry.get("updatedAt")));
                }
                // todays end
                response.setCode(200);
                response.setMessage("Paginated results");
                CustomResponse.Result result = new CustomResponse.Result();
                result.setContent(content);
                result.setTotalElements(content.size());
                result.setTotalPages(1); // Assuming all data is fetched in one page
                result.setPage(0);
                result.setSize(content.size());
                result.setSort(Collections.singletonList("id,ASC")); // Provide appropriate sorting information if
                // needed
                response.setResult(result);
                return response;
        }

        public CustomResponse getAllCompanyBrunchIndexListUsingSp(String sortField, String sortOrder) {
                CustomResponse response = new CustomResponse();

                String sql = "EXEC sp_get_3B_company_brunch_details_list @SortField = ?, @SortOrder = ?";

                List<Map<String, Object>> content = jdbcTemplate.queryForList(sql, sortField, sortOrder);
                // todays trying start
                for (Map<String, Object> entry : content) {
                        // Update "createdBy" and "updatedBy" fields with user names
                        entry.put("createdBy", getUserNameById((Long) entry.get("createdBy")));
                        entry.put("updatedBy", getUserNameById((Long) entry.get("updatedBy")));
                        entry.put("createdAt", DateUtilForSp.formatToCustomString((Date) entry.get("createdAt")));
                        entry.put("updatedAt", DateUtilForSp.formatToCustomString((Date) entry.get("updatedAt")));
                }
                // todays end
                response.setCode(200);
                response.setMessage("Paginated results");
                CustomResponse.Result result = new CustomResponse.Result();
                result.setContent(content);
                result.setTotalElements(content.size());
                result.setTotalPages(1); // Assuming all data is fetched in one page
                result.setPage(0);
                result.setSize(content.size());
                result.setSort(Collections.singletonList(sortField + "," + sortOrder));
                response.setResult(result);

                return response;
        }

        public List<HouseKeeping> getAllCompanyBrunchIndexDropdownUsingSp() {
                return jdbcTemplate.query("EXEC [dbo].[sp_get_3B_company_brunch_details_dropdown]", (rs, rowNum) -> {
                        HouseKeeping houseKeeping = new HouseKeeping();
                        houseKeeping.setId(rs.getLong("id"));
                        houseKeeping.setType(rs.getString("type"));
                        houseKeeping.setName(rs.getString("name"));
                        houseKeeping.setNamebn(rs.getString("nameBn"));
                        return houseKeeping;
                });
        }

        // Service class Using SP Start
}
