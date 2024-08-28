package com.khajana.setting.service.ordermanagement;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.ordermanagement.exportlcinformation.ExportLcInformationChildRequestDto;
import com.khajana.setting.dto.ordermanagement.exportlcinformation.ExportLcInformationChildResponseDto;
import com.khajana.setting.dto.ordermanagement.exportlcinformation.ExportLcInformationMasterRequestDto;
import com.khajana.setting.dto.ordermanagement.exportlcinformation.ExportLcInformationMasterResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.bank.BankInfo;
import com.khajana.setting.entity.companybranch.Company;
import com.khajana.setting.entity.currency.Currency;
import com.khajana.setting.entity.customer.Customer;
import com.khajana.setting.entity.item.Item;
import com.khajana.setting.entity.ordermanagement.exportlcinformation.ExportLcInformationChild;
import com.khajana.setting.entity.ordermanagement.exportlcinformation.ExportLcInformationMaster;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.entity.transactiontype.LcFor;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.bank.BankInfoRepository;
import com.khajana.setting.repository.companybranch.CompanyRepository;
import com.khajana.setting.repository.currency.CurrencyRepository;
import com.khajana.setting.repository.customer.CustomerRepository;
import com.khajana.setting.repository.item.ItemRepository;
import com.khajana.setting.repository.ordermanagement.ExportLcInformationChildRepository;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.repository.transactiontype.LcForRepository;
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
public class ExportLcInformationService {

    @Autowired
    com.khajana.setting.repository.ordermanagement.ExportLcInformationMasterRepository ExportLcInformationMasterRepository;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    BankInfoRepository bankInfoRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    LcForRepository lcForRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ExportLcInformationChildRepository exportLcInformationChildRepository;

    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    public SimplePage<ExportLcInformationMasterResponseDto> findAllExportLcInformationMasters(Pageable pageable) {
        Page<ExportLcInformationMaster> page = ExportLcInformationMasterRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public ExportLcInformationMasterResponseDto findExportLcInformationMasterById(Long id) {

        ExportLcInformationMaster newEntity = ExportLcInformationMasterRepository.findExportLcInformationMasterById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionExportLcInformationMaster", "Id", id));

        return transferToFindByIdDTO(newEntity);
    }

    public ApiResponse addExportLcInformationMaster(ExportLcInformationMasterRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = ExportLcInformationMasterRepository.existsByLcNo(dto.getLcNo());

            if (typeExists) {
                return new ApiResponse(500, "Duplicate LC Number not allowed", "validation error");
            }
            ExportLcInformationMaster newEntity = ExportLcInformationMasterRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateExportLcInformationMaster(Long id, ExportLcInformationMasterRequestDto dto) {
        // Read user id from JWT Token
        try {
            ExportLcInformationMaster newEntity = ExportLcInformationMasterRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.ExportLcInformationMasterDropdown();
        return result;
    }

    public void deleteExportLcInformationMaster(Long id) {
        ExportLcInformationMasterRepository.deleteExportLcInformationMasterById(id);
    }

    private ExportLcInformationMaster transferToEntity(Long id, ExportLcInformationMasterRequestDto dto) {
        ExportLcInformationMaster elcm = new ExportLcInformationMaster();
        CustomUserDetails userData = ContextUser.getLoginUserData();
        elcm.setLcNo(dto.getLcNo());
        elcm.setLcDate(dto.getLcDate());
        elcm.setGroupCode(dto.getGroupCode());
        elcm.setCompanyId(dto.getCompanyId());
        elcm.setCustomerId(dto.getCustomerId());
        elcm.setOpenBankId(dto.getOpenBankId());
        elcm.setLienBankId(dto.getLienBankId());
        elcm.setLienDate(dto.getLienDate());
        elcm.setCurrencyId(dto.getCurrencyId());
        elcm.setLcAmt(dto.getLcAmt());
        elcm.setShipDate(dto.getShipDate());
        elcm.setExpDate(dto.getExpDate());
        elcm.setRemarks(dto.getRemarks());
        elcm.setLcForId(dto.getLcForId());
        elcm.setMaximportLimit(dto.getMaximportLimit());
        elcm.setIsClosed(dto.getIsClosed());
        elcm.setCreatedAt(new Date());
        elcm.setCreatedBy(userData.getId());
        ExportLcInformationMaster el = ExportLcInformationMasterRepository.save(elcm);

        for (ExportLcInformationChildRequestDto item : dto.getItems()) {
            ExportLcInformationChild elc = new ExportLcInformationChild();
            elc.setLcId(el.getId());
            elc.setItemInfoId(item.getItemInfoId());
            elc.setQty(item.getQty());
            elc.setRate(item.getRate());
            elc.setAmount(item.getAmount());
            elcm.setCreatedAt(new Date());
            elcm.setCreatedBy(userData.getId());
            exportLcInformationChildRepository.save(elc);

        }

        return elcm;
    }

    private ExportLcInformationMasterResponseDto transferToDTO(ExportLcInformationMaster entity) {
        ExportLcInformationMasterResponseDto dto = new ExportLcInformationMasterResponseDto();
        dto.setId(entity.getId());
        dto.setLcNo(entity.getLcNo());
        dto.setLcDate(DateUtil.convertDateToString(entity.getLcDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setGroupCode(entity.getGroupCode());
        dto.setCustomerId(entity.getCustomerId());
        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId()).orElse(null);
            if (customer != null && customer.getCustomerName() != null) {
                dto.setCustomerName(customer.getCustomerName());
            }
        }
        dto.setCompanyId(entity.getCompanyId());

        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId()).orElse(null);
            if (company != null && company.getCompName() != null) {
                dto.setCompanyName(company.getCompName());
            }
        }
        dto.setOpenBankId(entity.getOpenBankId());
        if (dto.getOpenBankId() != null) {
            BankInfo bank = bankInfoRepository.findById(dto.getOpenBankId()).orElse(null);
            if (bank != null && bank.getBankName() != null) {
                dto.setOpenBankName(bank.getBankName());
            }
        }
        dto.setLienBankId(entity.getLienBankId());
        if (dto.getLienBankId() != null) {
            BankInfo bank = bankInfoRepository.findById(dto.getLienBankId()).orElse(null);
            if (bank != null && bank.getBankName() != null) {
                dto.setLienBankName(bank.getBankName());
            }
        }
        dto.setLienDate(DateUtil.convertDateToString(entity.getLienDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setCurrencyId(entity.getCurrencyId());
        if (dto.getCurrencyId() != null) {
            Currency currency = currencyRepository.findById(dto.getCurrencyId()).orElse(null);
            if (currency != null && currency.getCurrencyShortCode() != null) {
                dto.setCurrencyName(currency.getCurrencyShortCode());
            }
        }
        dto.setLcAmt(entity.getLcAmt());
        dto.setShipDate(DateUtil.convertDateToString(entity.getShipDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setExpDate(DateUtil.convertDateToString(entity.getExpDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setRemarks(entity.getRemarks());
        dto.setLcForId(entity.getLcForId());
        if (dto.getLcForId() != null) {
            LcFor lcFor = lcForRepository.findById(dto.getLcForId()).orElse(null);
            if (lcFor != null && lcFor.getDescription() != null) {
                dto.setLcForName(lcFor.getDescription());
            }
        }
        dto.setMaximportLimit(entity.getMaximportLimit());
        dto.setIsClosed(entity.getIsClosed());

        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        // dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        // if (entity.getUpdatedBy() != null) {
        //     User updatedByUser = userCredentials.findById(entity.getUpdatedBy()).orElse(null);
        //     dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        // }
        return dto;
    }

    private ExportLcInformationMasterResponseDto transferToFindByIdDTO(ExportLcInformationMaster entity) {
        ExportLcInformationMasterResponseDto dto = new ExportLcInformationMasterResponseDto();
        dto.setId(entity.getId());
        dto.setLcNo(entity.getLcNo());
        dto.setLcDate(DateUtil.convertDateToString(entity.getLcDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setGroupCode(entity.getGroupCode());
        dto.setCustomerId(entity.getCustomerId());
        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId()).orElse(null);
            if (customer != null && customer.getCustomerName() != null) {
                dto.setCustomerName(customer.getCustomerName());
            }
        }
        dto.setCompanyId(entity.getCompanyId());

        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId()).orElse(null);
            if (company != null && company.getCompName() != null) {
                dto.setCompanyName(company.getCompName());
            }
        }
        dto.setOpenBankId(entity.getOpenBankId());
        if (dto.getOpenBankId() != null) {
            BankInfo bank = bankInfoRepository.findById(dto.getOpenBankId()).orElse(null);
            if (bank != null && bank.getBankName() != null) {
                dto.setOpenBankName(bank.getBankName());
            }
        }
        dto.setLienBankId(entity.getLienBankId());
        if (dto.getLienBankId() != null) {
            BankInfo bank = bankInfoRepository.findById(dto.getLienBankId()).orElse(null);
            if (bank != null && bank.getBankName() != null) {
                dto.setLienBankName(bank.getBankName());
            }
        }
        dto.setLienDate(DateUtil.convertDateToString(entity.getLienDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setCurrencyId(entity.getCurrencyId());
        if (dto.getCurrencyId() != null) {
            Currency currency = currencyRepository.findById(dto.getCurrencyId()).orElse(null);
            if (currency != null && currency.getCurrencyShortCode() != null) {
                dto.setCurrencyName(currency.getCurrencyShortCode());
            }
        }
        dto.setLcAmt(entity.getLcAmt());
        dto.setShipDate(DateUtil.convertDateToString(entity.getShipDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setExpDate(DateUtil.convertDateToString(entity.getExpDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setRemarks(entity.getRemarks());
        dto.setLcForId(entity.getLcForId());
        if (dto.getLcForId() != null) {
            LcFor lcFor = lcForRepository.findById(dto.getLcForId()).orElse(null);
            if (lcFor != null && lcFor.getDescription() != null) {
                dto.setLcForName(lcFor.getDescription());
            }
        }
        dto.setMaximportLimit(entity.getMaximportLimit());
        dto.setIsClosed(entity.getIsClosed());

        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        // dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        // if (entity.getUpdatedBy() != null) {
        //     User updatedByUser = userCredentials.findById(entity.getUpdatedBy()).orElse(null);
        //     dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        // }


        List<ExportLcInformationChildResponseDto> items = entity.getItems().stream().map(lcItem -> {
            ExportLcInformationChildResponseDto item = new ExportLcInformationChildResponseDto();
            item.setId(lcItem.getId());
            item.setLcId(lcItem.getLcId());
            item.setItemInfoId(lcItem.getItemInfoId());
            if (lcItem.getItemInfoId() != null) {
                Item itemname = itemRepository.findById(lcItem.getItemInfoId()).orElse(null);
                if (itemname != null && itemname.getDisplayItmName() != null) {
                    item.setItemInfoName(itemname.getDisplayItmName());
                }
            }
            item.setQty(lcItem.getQty());
            item.setRate(lcItem.getRate());
            item.setAmount(lcItem.getAmount());
            return item;
        }).collect(Collectors.toList());
        dto.setItems(items);
        return dto;
    }
}
