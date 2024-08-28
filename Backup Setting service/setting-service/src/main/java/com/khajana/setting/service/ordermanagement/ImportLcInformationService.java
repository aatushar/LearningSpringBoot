package com.khajana.setting.service.ordermanagement;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.ordermanagement.importlcinformation.ImportLcInformationChildRequestDto;
import com.khajana.setting.dto.ordermanagement.importlcinformation.ImportLcInformationChildResponseDto;
import com.khajana.setting.dto.ordermanagement.importlcinformation.ImportLcInformationMasterRequestDto;
import com.khajana.setting.dto.ordermanagement.importlcinformation.ImportLcInformationMasterResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.bank.BankInfo;
import com.khajana.setting.entity.companybranch.Company;
import com.khajana.setting.entity.companybranch.CompanySupplier;
import com.khajana.setting.entity.currency.Currency;
import com.khajana.setting.entity.item.Item;
import com.khajana.setting.entity.ordermanagement.exportlcinformation.ExportLcInformationMaster;
import com.khajana.setting.entity.ordermanagement.importlcinformation.ImportLcInformationChild;
import com.khajana.setting.entity.ordermanagement.importlcinformation.ImportLcInformationMaster;
import com.khajana.setting.entity.ordermanagement.utilizationdeclaration.UtilizationDeclarationMaster;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.entity.transactiontype.TransactionSourceType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.bank.BankInfoRepository;
import com.khajana.setting.repository.companybranch.CompanyRepository;
import com.khajana.setting.repository.companybranch.CompanySupplierRepository;
import com.khajana.setting.repository.currency.CurrencyRepository;
import com.khajana.setting.repository.inventorymethod.InventoryMethodRepo;
import com.khajana.setting.repository.item.ItemGroupRepository;
import com.khajana.setting.repository.item.ItemRepository;
import com.khajana.setting.repository.ordermanagement.ExportLcInformationMasterRepository;
import com.khajana.setting.repository.ordermanagement.ImportLcInformationChildRepository;
import com.khajana.setting.repository.ordermanagement.utilizationdeclaration.UtilizationDeclarationMasterRepository;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.repository.transactiontype.TransactionSourceTypeRepository;
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
public class ImportLcInformationService {

    @Autowired
    com.khajana.setting.repository.ordermanagement.ImportLcInformationMasterRepository ImportLcInformationMasterRepository;

    @Autowired
    ImportLcInformationChildRepository ilicr;

    @Autowired
    ItemGroupRepository itemGroupRepository;

    @Autowired
    InventoryMethodRepo inventoryMethodRepo;

    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanySupplierRepository supplierRepo;
    @Autowired
    BankInfoRepository bankInfoRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ExportLcInformationMasterRepository exportLcInformationMasterRepository;

    @Autowired
    UtilizationDeclarationMasterRepository utilizationDeclarationMasterRepository;

    @Autowired
    TransactionSourceTypeRepository transactionSourceTypeRepository;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    public SimplePage<ImportLcInformationMasterResponseDto> findAllImportLcInformationMasters(Pageable pageable) {
        Page<ImportLcInformationMaster> page = ImportLcInformationMasterRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public ImportLcInformationMasterResponseDto findImportLcInformationMasterById(Long id) {

        ImportLcInformationMaster newEntity = ImportLcInformationMasterRepository.findImportLcInformationMasterById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionImportLcInformationMaster", "Id", id));

        return transferToFindByIdDTO(newEntity);
    }

    public ApiResponse addImportLcInformationMaster(ImportLcInformationMasterRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = ImportLcInformationMasterRepository.existsByImportLcNo(dto.getImportLcNo());

            if (typeExists) {
                return new ApiResponse(500, "Duplicate Import Lc Number not allowed", "validation error");
            }
            ImportLcInformationMaster newEntity = ImportLcInformationMasterRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateImportLcInformationMaster(Long id, ImportLcInformationMasterRequestDto dto) {
        // Read user id from JWT Token
        try {
            ImportLcInformationMaster newEntity = ImportLcInformationMasterRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.ImportLcInformationMasterDropdown();
        return result;
    }

    public void deleteImportLcInformationMaster(Long id) {
        ImportLcInformationMasterRepository.deleteImportLcInformationMasterById(id);
    }

    private ImportLcInformationMaster transferToEntity(Long id, ImportLcInformationMasterRequestDto dto) {
        ImportLcInformationMaster ilcm = new ImportLcInformationMaster();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        ilcm.setCompanyId(dto.getCompanyId());
        ilcm.setSupplierId(dto.getSupplierId());
        ilcm.setIsBblc(dto.getIsBblc());
        ilcm.setExportLcId(dto.getExportLcId());
        ilcm.setImportLcNo(dto.getImportLcNo());
        ilcm.setRemarks(dto.getRemarks());
        ilcm.setImportLcDate(dto.getImportLcDate());
        ilcm.setOpeningBankId(dto.getOpeningBankId());
        // ilcm.setImportLcTypeId(dto.getImportLcTypeId());
        ilcm.setCurrencyId(dto.getCurrencyId());
        ilcm.setImportLcAmt(dto.getImportLcAmt());
        ilcm.setExpiryDate(dto.getExpiryDate());
        ilcm.setTolarance(dto.getTolarance());
        ilcm.setSourcingTypeId(dto.getSourcingTypeId());
        ilcm.setPurposeId(dto.getPurposeId());
        ilcm.setAdvisingBank(dto.getAdvisingBank());
        ilcm.setApplicationDate(dto.getApplicationDate());
        ilcm.setIsApplied(dto.getIsApplied());
        ilcm.setUdRegisterId(dto.getUdRegisterId());
        ilcm.setCreatedAt(new Date());
        ilcm.setCreatedBy(userData.getId());
        ImportLcInformationMaster newIlcm = ImportLcInformationMasterRepository.save(ilcm);
        for (ImportLcInformationChildRequestDto ilcc : dto.getItems()) {
            ImportLcInformationChild il = new ImportLcInformationChild();
            il.setImportLcMasterId(newIlcm.getId());
            il.setItemInfoId(ilcc.getItemInfoId());
            il.setRate(ilcc.getRate());
            il.setImportLcAmt(ilcc.getImportLcAmt());
            ilicr.save(il);
        }

        return ilcm;
    }

    private ImportLcInformationMasterResponseDto transferToDTO(ImportLcInformationMaster entity) {
        ImportLcInformationMasterResponseDto dto = new ImportLcInformationMasterResponseDto();
        dto.setId(entity.getId());

        dto.setId(entity.getId());
        dto.setCompanyId(entity.getCompanyId());
        dto.setSupplierId(entity.getSupplierId());
        dto.setIsBblc(entity.getIsBblc());
        dto.setExportLcId(entity.getExportLcId());
        dto.setImportLcNo(entity.getImportLcNo());
        dto.setRemarks(entity.getRemarks());
        dto.setImportLcDate(DateUtil.convertDateToString(entity.getImportLcDate(),
                ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setOpeningBankId(entity.getOpeningBankId());
        dto.setCurrencyId(entity.getCurrencyId());
        dto.setImportLcAmt(entity.getImportLcAmt());
        dto.setExpiryDate(DateUtil.convertDateToString(entity.getExpiryDate(),
                ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setTolarance(entity.getTolarance());
        dto.setSourcingTypeId(entity.getSourcingTypeId());
        dto.setPurposeId(entity.getPurposeId());
        dto.setAdvisingBank(entity.getAdvisingBank());
        dto.setApplicationDate(DateUtil.convertDateToString(entity.getApplicationDate(),
                ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setIsApplied(entity.getIsApplied());
        dto.setUdRegisterId(entity.getUdRegisterId());
        if (entity.getUdRegisterId() != null) {
            UtilizationDeclarationMaster utilizationDeclarationMaster = utilizationDeclarationMasterRepository.findById(entity.getUdRegisterId()).orElse(null);
            if (utilizationDeclarationMaster != null && utilizationDeclarationMaster.getUdRegisterNo() != null) {
                dto.setUdRegisterNo(utilizationDeclarationMaster.getUdRegisterNo());
                dto.setUdRegisterDate(DateUtil.convertDateToString(utilizationDeclarationMaster.getUdRegisterDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
            }
        }
        if (entity.getExportLcId() != null) {
            ExportLcInformationMaster exportLcInformationMaster = exportLcInformationMasterRepository.findById(entity.getExportLcId()).orElse(null);
            if (exportLcInformationMaster != null && exportLcInformationMaster.getLcNo() != null) {
                dto.setExportLcNo(exportLcInformationMaster.getLcNo());
                dto.setExportLcDate(DateUtil.convertDateToString(exportLcInformationMaster.getLcDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
            }
        }
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            if (company != null && company.getCompName() != null) {
                dto.setCompanyName(company.getCompName());
            }
        }
        if (entity.getSupplierId() != null) {
            CompanySupplier supplier = supplierRepo.findById(entity.getSupplierId()).orElse(null);
            if (supplier != null && supplier.getSupplierName() != null) {
                dto.setSupplierName(supplier.getSupplierName());
            }
        }
        if (entity.getOpeningBankId() != null) {
            BankInfo bank = bankInfoRepository.findById(entity.getOpeningBankId()).orElse(null);
            if (bank != null && bank.getBankName() != null) {
                dto.setOpeningBankName(bank.getBankName());
            }
        }
        if (entity.getAdvisingBank() != null) {
            BankInfo bank = bankInfoRepository.findById(entity.getAdvisingBank()).orElse(null);
            if (bank != null && bank.getBankName() != null) {
                dto.setAdvisingBankName(bank.getBankName());
            }
        }
        if (entity.getSourcingTypeId() != null) {
            TransactionSourceType st = transactionSourceTypeRepository.findById(entity.getSourcingTypeId())
                    .orElse(null);
            if (st != null && st.getTranSourceTypeName() != null) {
                dto.setSourcingTypeName(st.getTranSourceTypeName());
            }
        }
        if (entity.getCurrencyId() != null) {
            Currency currency = currencyRepository.findById(entity.getCurrencyId()).orElse(null);
            if (currency != null && currency.getCurrencyShortCode() != null) {
                dto.setCurrencyName(currency.getCurrencyShortCode());
            }
        }

        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(),
                ConstantValue.OUT_GOING_DATE_PATTERN));
        // dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(),
        //         ConstantValue.OUT_GOING_DATE_PATTERN));

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

    private ImportLcInformationMasterResponseDto transferToFindByIdDTO(ImportLcInformationMaster entity) {
        ImportLcInformationMasterResponseDto dto = new ImportLcInformationMasterResponseDto();
        dto.setId(entity.getId());

        dto.setId(entity.getId());
        dto.setCompanyId(entity.getCompanyId());
        dto.setSupplierId(entity.getSupplierId());
        dto.setIsBblc(entity.getIsBblc());
        dto.setExportLcId(entity.getExportLcId());
        dto.setImportLcNo(entity.getImportLcNo());
        dto.setImportLcDate(DateUtil.convertDateToString(entity.getImportLcDate(),
                ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setOpeningBankId(entity.getOpeningBankId());
        dto.setCurrencyId(entity.getCurrencyId());
        dto.setImportLcAmt(entity.getImportLcAmt());
        dto.setExpiryDate(DateUtil.convertDateToString(entity.getExpiryDate(),
                ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setTolarance(entity.getTolarance());
        dto.setSourcingTypeId(entity.getSourcingTypeId());
        dto.setPurposeId(entity.getPurposeId());
        dto.setAdvisingBank(entity.getAdvisingBank());
        dto.setApplicationDate(DateUtil.convertDateToString(entity.getApplicationDate(),
                ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setIsApplied(entity.getIsApplied());
        dto.setUdRegisterId(entity.getUdRegisterId());
        if (entity.getUdRegisterId() != null) {
            UtilizationDeclarationMaster utilizationDeclarationMaster = utilizationDeclarationMasterRepository
                    .findById(entity.getUdRegisterId()).orElse(null);
            if (utilizationDeclarationMaster != null && utilizationDeclarationMaster.getUdRegisterNo() != null) {
                dto.setUdRegisterNo(utilizationDeclarationMaster.getUdRegisterNo());
                dto.setUdRegisterDate(DateUtil.convertDateToString(utilizationDeclarationMaster.getUdRegisterDate(),
                        ConstantValue.OUT_GOING_DATE_PATTERN));
            }
        }
        if (entity.getExportLcId() != null) {
            ExportLcInformationMaster exportLcInformationMaster = exportLcInformationMasterRepository
                    .findById(entity.getExportLcId()).orElse(null);
            if (exportLcInformationMaster != null && exportLcInformationMaster.getLcNo() != null) {
                dto.setExportLcNo(exportLcInformationMaster.getLcNo());
                dto.setExportLcDate(DateUtil.convertDateToString(exportLcInformationMaster.getLcDate(),
                        ConstantValue.OUT_GOING_DATE_PATTERN));
            }
        }
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            if (company != null && company.getCompName() != null) {
                dto.setCompanyName(company.getCompName());
            }
        }
        if (entity.getSupplierId() != null) {
            CompanySupplier supplier = supplierRepo.findById(entity.getSupplierId()).orElse(null);
            if (supplier != null && supplier.getSupplierName() != null) {
                dto.setSupplierName(supplier.getSupplierName());
            }
        }
        if (entity.getOpeningBankId() != null) {
            BankInfo bank = bankInfoRepository.findById(entity.getOpeningBankId()).orElse(null);
            if (bank != null && bank.getBankName() != null) {
                dto.setOpeningBankName(bank.getBankName());
            }
        }
        if (entity.getAdvisingBank() != null) {
            BankInfo bank = bankInfoRepository.findById(entity.getAdvisingBank()).orElse(null);
            if (bank != null && bank.getBankName() != null) {
                dto.setAdvisingBankName(bank.getBankName());
            }
        }
        if (entity.getSourcingTypeId() != null) {
            TransactionSourceType st = transactionSourceTypeRepository.findById(entity.getSourcingTypeId())
                    .orElse(null);
            if (st != null && st.getTranSourceTypeName() != null) {
                dto.setSourcingTypeName(st.getTranSourceTypeName());
            }
        }
        if (entity.getCurrencyId() != null) {
            Currency currency = currencyRepository.findById(entity.getCurrencyId()).orElse(null);
            if (currency != null && currency.getCurrencyShortCode() != null) {
                dto.setCurrencyName(currency.getCurrencyShortCode());
            }
        }

        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(),
                ConstantValue.OUT_GOING_DATE_PATTERN));
        // dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(),
        //         ConstantValue.OUT_GOING_DATE_PATTERN));

        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        // if (entity.getUpdatedBy() != null) {
        //     User updatedByUser = userCredentials.findById(entity.getUpdatedBy()).orElse(null);
        //     dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        // }

        List<ImportLcInformationChildResponseDto> items = entity.getItems().stream().map(lcItem -> {
            ImportLcInformationChildResponseDto item = new ImportLcInformationChildResponseDto();
            item.setId(lcItem.getId());
            // item.set(lcItem.getLcId());
            item.setItemInfoId(lcItem.getItemInfoId());
            if (lcItem.getItemInfoId() != null) {
                Item itemname = itemRepository.findById(lcItem.getItemInfoId()).orElse(null);
                if (itemname != null && itemname.getDisplayItmName() != null) {
                    item.setItemInfoName(itemname.getDisplayItmName());
                }
            }
            // item.setQty(lcItem.getQty());
            item.setRate(lcItem.getRate());
            item.setRate(lcItem.getRate());
            item.setImportLcAmt(lcItem.getImportLcAmt());
            return item;
        }).collect(Collectors.toList());
        dto.setItems(items);
        return dto;
    }
}
