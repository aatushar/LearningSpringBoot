package com.khajana.setting.service.issue;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.issue.*;
import com.khajana.setting.dto.receive.ReceiveChildForWastageResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.companybranch.Company;
import com.khajana.setting.entity.companybranch.CompanyBranch;
import com.khajana.setting.entity.currency.Currency;
import com.khajana.setting.entity.fy.FiscalYearInfo;
import com.khajana.setting.entity.issue.IssueChild;
import com.khajana.setting.entity.issue.IssueMaster;
import com.khajana.setting.entity.issue.StockChild;
import com.khajana.setting.entity.issue.StockMaster;
import com.khajana.setting.entity.item.Item;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.entity.product.ProductType;
import com.khajana.setting.entity.transactiontype.TransactionSourceType;
import com.khajana.setting.entity.transactiontype.TransactionType;
import com.khajana.setting.entity.vat.VatMonthInfo;
import com.khajana.setting.entity.vatratetype.VatRateType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyBranchRepository;
import com.khajana.setting.repository.companybranch.CompanyRepository;
import com.khajana.setting.repository.companystore.CompanyStoreRepository;
import com.khajana.setting.repository.currency.CurrencyRepository;
import com.khajana.setting.repository.fy.FiscalYearInfoRepository;
import com.khajana.setting.repository.issue.DamageManagementChildRepository;
import com.khajana.setting.repository.issue.DamageManagementRepository;
import com.khajana.setting.repository.issue.StockChildRepository;
import com.khajana.setting.repository.issue.StockMasterRepository;
import com.khajana.setting.repository.item.ItemRepository;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.repository.product.ProductTypeRepository;
import com.khajana.setting.repository.transactiontype.TransactionSourceTypeRepository;
import com.khajana.setting.repository.transactiontype.TransactionSubTypeRepository;
import com.khajana.setting.repository.transactiontype.TransactionTypeRepository;
import com.khajana.setting.repository.vat.VatMonthInfoRepository;
import com.khajana.setting.repository.vatratetype.VatRateTypeRepository;
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
public class DamageManagementService {

    @Autowired
    DamageManagementRepository issueIssueMasterRepo;
    @Autowired
    UserCredentialRepository userCredentials;
    @Autowired
    DamageManagementChildRepository damageManagementChildRepository;
    @Autowired
    VatRateTypeRepository vatRateTypeRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ProductTypeRepository productTypeRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyBranchRepository companyBranchRepository;
    @Autowired
    CompanyStoreRepository companyStoreRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    FiscalYearInfoRepository fiscalYearInfoRepository;
    @Autowired
    VatMonthInfoRepository vatMonthInfoRepository;

    @Autowired
    TransactionSourceTypeRepository transactionSourceTypeRepository;
    @Autowired
    TransactionTypeRepository transactionTypeRepository;
    @Autowired
    TransactionSubTypeRepository transactionSubTypeRepository;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    @Autowired
    StockMasterRepository stockMasterRepository;

    @Autowired
    StockChildRepository stockChildRepository;

    public ApiResponse DamageManagement(DamageManagementMasterRequestDto dto) {
        try {
            IssueMaster newEntity = issueIssueMasterRepo.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public SimplePage<DamageManagementResponseDto> findAlltemCat(Pageable pageable) {

        TransactionType transactionType = transactionTypeRepository.findTransactionTypeByTrnsTypeName("Damage")
                .orElse(null);
        Long transactionTypeId = transactionType.getId();

        ProductType productType = productTypeRepository.findProductTypeByName("Finished Goods").orElse(null);
        Long productTypeId = productType.getId();

        Page<IssueMaster> page = issueIssueMasterRepo.findAllByTranTypeIdAndProdTypeId(transactionTypeId, productTypeId,
                pageable);

        return new SimplePage<>(
                page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(),
                pageable);
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.receiveDropdown();
        return result;
    }

    public List<HouseKeeping> getDamagedItemsHsCode() {
        List<HouseKeeping> result = houseKeepingRepository.getDamagedItemsHsCode();
        return result;
    }

    public List<ReceiveChildForWastageResponseDto> getItemsDamage(Long hsCodeId) {
        List<ReceiveChildForWastageResponseDto> result = houseKeepingRepository.getItemsDamage(hsCodeId);
        return result;
    }

    public DamageManagementFindByIdResponseDto DamageManagementById(Long id) {

        IssueMaster newEntity = issueIssueMasterRepo.findIssueMasterById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToFindByIdDTO(newEntity);
    }

    IssueMaster transferToEntity(Long id, DamageManagementMasterRequestDto dto) {
        IssueMaster issue = new IssueMaster();
        ProductType productType = productTypeRepository.findProductTypeByName("Finished Goods").orElse(null);
        Long productTypeId = productType.getId();
        TransactionSourceType transactionSourceType = transactionSourceTypeRepository
                .findSourceTypeByTranSourceTypeName("Issue").orElse(null);
        TransactionType transactionType = transactionTypeRepository.findTransactionTypeByTrnsTypeName("Damage")
                .orElse(null);
        // TransactionSubType transactionSubType = transactionSubTypeRepository.findSourceTypeByTrnsSubTypeName("Factory")
        //         .orElse(null);
        CustomUserDetails userData = ContextUser.getLoginUserData();

        issue.setProdTypeId(null);
        issue.setReceiveMasterId(null);
        issue.setProdTypeId(productTypeId);

        if (transactionSourceType != null && transactionSourceType.getId() != null) {
            issue.setTranSourceTypeId(transactionSourceType.getId());
        }
        if (transactionType != null && transactionType.getId() != null) {
            issue.setTranTypeId(transactionType.getId());
        }
        // if (transactionSubType != null && transactionSubType.getId() != null) {
        //     issue.setTranSubTypeId(transactionSubType.getId());
        // }
        issue.setCompanyId(dto.getCompanyId());
        issue.setBranchId(dto.getBranchId());
        issue.setStoreId(dto.getStoreId());
        issue.setCurrencyId(dto.getCurrencyId());
        issue.setExcgRate(dto.getExcgRate());
        issue.setCustomerId(dto.getCustomerId());
        issue.setFiscalYearId(dto.getFiscalYearId());
        issue.setVatMontId(dto.getVatMontId());
        issue.setIssueDate(dto.getIssueDate());
        issue.setDepartmentId(dto.getDepartmentId());
        issue.setTotalIsAmBeDiscount(dto.getTotalIsAmBeDiscount());
        issue.setTotalIsAmLocalCurrBeDiscount(dto.getTotalIsAmLocalCurrBeDiscount());
        issue.setTotalCdAmount(dto.getTotalCdAmount());
        issue.setTotalRdAmount(dto.getTotalRdAmount());
        issue.setTotalSdAmount(dto.getTotalSdAmount());
        issue.setTotalVatAmount(dto.getTotalVatAmount());
        issue.setTotalIssueAmtLocalCurr(dto.getTotalIssueAmtLocalCurr());
        issue.setTotalIssueAmtTransCurr(dto.getTotalIssueAmtTransCurr());
        issue.setRemarks(dto.getRemarks());
        issue.setRemarksBn("Damage Management");
        // issue.setRegStatus(1L);

        issue.setCreatedAt(new Date());
        issue.setCreatedBy(userData.getId());
        issue.setCreatedAt(new Date());
        issue.setCreatedBy(userData.getId());
        IssueMaster issueMaster = issueIssueMasterRepo.save(issue);
        issueMaster.setIssueNo("DM-" + issueMaster.getId());
        issueMaster.setIssueNoBn("DM-" + issueMaster.getId());

        if (!dto.getIssueChild().isEmpty()) {
            for (DamageManagementChildRequestDto issuechildDto : dto.getIssueChild()) {
                IssueChild issueChild = new IssueChild();
                issueChild.setIssueMasterId(issueMaster.getId());
                issueChild.setItemInfoId(issuechildDto.getItemInfoId());
                issueChild.setUomId(issuechildDto.getUomId());
                issueChild.setUomShortCode(issuechildDto.getUomShortCode());
                issueChild.setRelativeFactor(issuechildDto.getRelativeFactor());
                issueChild.setIssueQty(issuechildDto.getIssueQty());
                issueChild.setItemRate(issuechildDto.getItemRate());

                issueChild.setIssueRate(issuechildDto.getIssueRate());
                issueChild.setVatRateTypeId(issuechildDto.getVatRateTypeId());
                issueChild.setIsFixedRate(null);
                issueChild.setCdPercent(0d);
                issueChild.setCdAmount(0d);
                issueChild.setRdPercent(0d);
                issueChild.setRdAmount(0d);
                issueChild.setSdPercent(0d);
                issueChild.setSdAmount(0d);
                issueChild.setVatPercent(0d);
                issueChild.setVatAmount(0d);
                issueChild.setAitPercent(0d);
                issueChild.setAitAmount(0d);
                issueChild.setAtAmount(0d);
                issueChild.setAtPercent(0d);

                issueChild.setCreatedAt(new Date());
                issueChild.setCreatedBy(userData.getId());
                Double issueQty = issuechildDto.getIssueQty() != null ? issuechildDto.getIssueQty() : 0.0;
                Double itemRate = issuechildDto.getItemRate() != null ? issuechildDto.getItemRate() : 0.0;
                Double sdAmount = issueChild.getSdAmount() != null ? issueChild.getSdAmount() : 0.0;
                Double vatAmount = issueChild.getVatAmount() != null ? issueChild.getVatAmount() : 0.0;

                issueChild.setItemValueTranCurr(issueQty * itemRate);
                issueChild.setItemValueLocalCurr(issueQty * itemRate);
                issueChild.setTotalAmountTranCurr(issueChild.getItemValueTranCurr() + sdAmount + vatAmount);
                issueChild.setTotalAmountLocalCurr(issueChild.getItemValueTranCurr() + sdAmount + vatAmount);
                damageManagementChildRepository.save(issueChild);
                StockMaster stockMaster = new StockMaster();
                stockMaster.setReceiveMasterId(issueMaster.getId());
                stockMaster.setTranSourceTypeId(issueMaster.getTranSourceTypeId());
                stockMaster.setTranTypeId(issueMaster.getTranTypeId());
                stockMaster.setProdTypeId(issue.getProdTypeId());
                stockMaster.setCompanyId(issue.getCompanyId());
                stockMaster.setBranchId(issue.getBranchId());
                stockMaster.setCurrencyId(issue.getCurrencyId());
                stockMaster.setReceiveIssueDate(issue.getIssueDate());
                stockMaster.setFiscalYearId(issue.getFiscalYearId());
                stockMaster.setVatMonthId(issue.getVatMontId());
                stockMaster.setItemInfoId(issueChild.getItemInfoId());
                stockMaster.setUomId(issueChild.getUomId());
                stockMaster.setVatRateTypeId(issueChild.getVatRateTypeId());
                stockMaster.setChallanNumber(issue.getChallanNumber());
                stockMaster.setChallanNumberBn(issue.getChallanNumberBn());
                stockMaster.setChallanDate(issue.getChallanDate());
                stockMaster.setRemarks(issue.getRemarks());
                stockMaster.setRemarksBn(issue.getRemarksBn());
                stockMaster.setCreatedAt(new Date());
                stockMaster.setCreatedBy(userData.getId());
                StockMaster stockMaster2 = stockMasterRepository.save(stockMaster);

                StockChild stockChild = new StockChild();
                stockChild.setItemstockMasterId(stockMaster2.getId());
                stockChild.setReceiveIssueChildId(issueChild.getId());
                stockChild.setStoreId(issue.getStoreId());
                stockChild.setOpeningBalQty(0d);
                stockChild.setOpeningBalRate(0d);
                stockChild.setOpeningBalAmountWithTax(0d);
                stockChild.setReceiveQty(0d);
                stockChild.setReceiveRate(0d);
                stockChild.setReceiveAmount(0d);
                stockChild.setReceiveAmountWithTax(0d);
                stockChild.setReceiveVatPercent(0d);
                stockChild.setReceiveVatAmount(0d);
                stockChild.setReceiveSdPercent(0d);
                stockChild.setReceiveSdAmount(0d);
                stockChild.setIssueQty(issueChild.getIssueQty());
                stockChild.setIssueRate(issueChild.getIssueRate());
                stockChild.setIssueAmount(issueChild.getItemValueLocalCurr());
                stockChild.setStockIssueAmount(issueChild.getIssueMasterId() * 1d);
                stockChild.setIssueVatPercent(issueChild.getVatPercent());
                stockChild.setIssueVatAmount(issueChild.getVatAmount());
                stockChild.setIssueSdPercent(issueChild.getSdPercent());
                stockChild.setIssueSdAmount(issueChild.getSdAmount());
                stockChild.setIssueAmountWithTax(issueChild.getTotalAmountLocalCurr());
                stockChild.setClosingBalQty((stockChild.getReceiveQty()) - stockChild.getIssueQty());
                stockChild.setClosingBalRate(stockChild.getClosingBalAmount());
                stockChild.setClosingBalAmount(0d + stockChild.getReceiveAmount() - stockChild.getStockIssueAmount());
                stockChild.setClosingBalAmountWithTax(
                        stockChild.getOpeningBalAmountWithTax() + stockChild.getReceiveAmountWithTax() - 0d);

                stockChild.setCreatedAt(new Date());
                stockChild.setCreatedBy(userData.getId());
                stockChildRepository.save(stockChild);
            }
        }

        return issue;
    }

    DamageManagementResponseDto transferToDTO(IssueMaster entity) {

        DamageManagementResponseDto dto = new DamageManagementResponseDto();
        dto.setId(entity.getId());
        dto.setReceiveMasterId(entity.getReceiveMasterId());

        if (entity.getTranSourceTypeId() != null) {
            TransactionSourceType transactionSourceType = transactionSourceTypeRepository
                    .findById(entity.getTranSourceTypeId()).orElse(null);
            if (transactionSourceType != null) {
                dto.setTranSourceTypeId(transactionSourceType.getId());
                dto.setTranSoureName(transactionSourceType.getTranSourceTypeName());
            }
        }
        // if (entity.getTranSubTypeId() != null) {
        //     TransactionSubType transactionSubType = transactionSubTypeRepository.findById(entity.getTranSubTypeId())
        //             .orElse(null);
        //     if (transactionSubType != null) {
        //         dto.setTranSubTypeId(transactionSubType.getId());
        //         dto.setTranSubTypeName(transactionSubType.getTrnsSubTypeName());
        //     }
        // }
        if (entity.getTranTypeId() != null) {
            TransactionType transactionType = transactionTypeRepository.findById(entity.getTranTypeId()).orElse(null);
            if (transactionType != null) {
                dto.setTranTypeId(transactionType.getId());
                dto.setTranTypeName(transactionType.getTrnsTypeName());
            }
        }
        dto.setProdTypeId(entity.getProdTypeId());
        dto.setCompanyId(entity.getCompanyId());
        dto.setBranchId(entity.getBranchId());
        dto.setStoreId(entity.getStoreId());
        dto.setCurrencyId(entity.getCurrencyId());
        dto.setIssueNo(entity.getIssueNo());
        dto.setIssueNoBn(entity.getIssueNoBn());
        dto.setIssueDate(DateUtil.convertDateToString(entity.getIssueDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setExcgRate(entity.getExcgRate());
        dto.setCustomerId(entity.getCustomerId());
        dto.setFiscalYearId(entity.getFiscalYearId());
        dto.setVatMontId(entity.getVatMontId());
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setTotalIsAmBeDiscount(entity.getTotalIsAmBeDiscount());
        dto.setTotalIsAmLocalCurrBeDiscount(entity.getTotalIsAmLocalCurrBeDiscount());
        dto.setTotalCdAmount(entity.getTotalCdAmount());
        dto.setTotalRdAmount(entity.getTotalRdAmount());
        dto.setTotalSdAmount(entity.getTotalSdAmount());
        dto.setTotalVatAmount(entity.getTotalVatAmount());
        dto.setTotalIssueAmtLocalCurr(entity.getTotalIssueAmtLocalCurr());
        dto.setTotalIssueAmtTransCurr(entity.getTotalIssueAmtTransCurr());
        dto.setRemarks(entity.getRemarks());
        dto.setRemarksBn("Damage Management");
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }
        if (entity.getProdTypeId() != null) {
            ProductType productType = productTypeRepository.findById(entity.getProdTypeId()).orElse(null);
            dto.setProdTypeName(productType != null ? productType.getName() : null);
            dto.setProdTypeNameBn(productType != null ? productType.getNameBn() : null);
        }
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            dto.setCompName(company != null ? company.getCompName() : null);
            dto.setCompNameBn(company != null ? company.getCompNameBn() : null);
        }
        if (entity.getBranchId() != null) {
            CompanyBranch companyBranch = companyBranchRepository.findById(entity.getBranchId()).orElse(null);
            dto.setBranchUnitName(companyBranch != null ? companyBranch.getBranchUnitName() : null);
            dto.setBranchUnitNameBn(companyBranch != null ? companyBranch.getBranchUnitNameBn() : null);
        }
        if (entity.getCurrencyId() != null) {
            Currency currency = currencyRepository.findById(entity.getCurrencyId()).orElse(null);
            dto.setCurrencyDesc(currency != null ? currency.getCurrencyDesc() : null);
            dto.setCurrencyShortCode(currency != null ? currency.getCurrencyShortCode() : null);
        }
        if (entity.getFiscalYearId() != null) {
            FiscalYearInfo fiscalYearInfo = fiscalYearInfoRepository.findById(entity.getFiscalYearId()).orElse(null);
            dto.setFiscalYear(fiscalYearInfo != null ? fiscalYearInfo.getFiscalYear() : null);
            dto.setFiscalYearBn(fiscalYearInfo != null ? fiscalYearInfo.getFiscalYearBn() : null);
        }
        if (entity.getVatMontId() != null) {
            VatMonthInfo vatMonthInfo = vatMonthInfoRepository.findById(entity.getVatMontId()).orElse(null);
            dto.setVmInfo(vatMonthInfo != null ? vatMonthInfo.getVmInfo() : null);
            dto.setVmInfoBn(vatMonthInfo != null ? vatMonthInfo.getVmInfoBn() : null);
        }
        return dto;
    }

    DamageManagementFindByIdResponseDto transferToFindByIdDTO(IssueMaster entity) {

        DamageManagementFindByIdResponseDto dto = new DamageManagementFindByIdResponseDto();
        dto.setId(entity.getId());
        if (entity.getTranSourceTypeId() != null) {
            TransactionSourceType transactionSourceType = transactionSourceTypeRepository
                    .findById(entity.getTranSourceTypeId()).orElse(null);
            if (transactionSourceType != null) {
                dto.setTranSourceTypeId(transactionSourceType.getId());
                dto.setTranSoureName(transactionSourceType.getTranSourceTypeName());
            }
        }
        // if (entity.getTranSubTypeId() != null) {
        //     TransactionSubType transactionSubType = transactionSubTypeRepository.findById(entity.getTranSubTypeId())
        //             .orElse(null);
        //     if (transactionSubType != null) {
        //         dto.setTranSubTypeId(transactionSubType.getId());
        //         dto.setTranSubTypeName(transactionSubType.getTrnsSubTypeName());
        //     }
        // }
        if (entity.getTranTypeId() != null) {
            TransactionType transactionType = transactionTypeRepository.findById(entity.getTranTypeId()).orElse(null);
            if (transactionType != null) {
                dto.setTranTypeId(transactionType.getId());
                dto.setTranTypeName(transactionType.getTrnsTypeName());
            }
        }
        dto.setReceiveMasterId(entity.getReceiveMasterId());
        dto.setProdTypeId(entity.getProdTypeId());
        dto.setCompanyId(entity.getCompanyId());
        dto.setBranchId(entity.getBranchId());
        dto.setStoreId(entity.getStoreId());
        dto.setCurrencyId(entity.getCurrencyId());
        dto.setIssueNo(entity.getIssueNo());
        dto.setIssueNoBn(entity.getIssueNoBn());
        dto.setIssueDate(DateUtil.convertDateToString(entity.getIssueDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setExcgRate(entity.getExcgRate());
        dto.setCustomerId(entity.getCustomerId());
        dto.setFiscalYearId(entity.getFiscalYearId());
        dto.setVatMontId(entity.getVatMontId());
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setTotalIsAmBeDiscount(entity.getTotalIsAmBeDiscount());
        dto.setTotalIsAmLocalCurrBeDiscount(entity.getTotalIsAmLocalCurrBeDiscount());
        dto.setTotalCdAmount(entity.getTotalCdAmount());
        dto.setTotalRdAmount(entity.getTotalRdAmount());
        dto.setTotalSdAmount(entity.getTotalSdAmount());
        dto.setTotalVatAmount(entity.getTotalVatAmount());
        dto.setTotalIssueAmtLocalCurr(entity.getTotalIssueAmtLocalCurr());
        dto.setTotalIssueAmtTransCurr(entity.getTotalIssueAmtTransCurr());
        dto.setRemarks(entity.getRemarks());
        dto.setRemarksBn("Damage Management");
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }
        if (entity.getProdTypeId() != null) {
            ProductType productType = productTypeRepository.findById(entity.getProdTypeId()).orElse(null);
            dto.setProdTypeName(productType != null ? productType.getName() : null);
            dto.setProdTypeNameBn(productType != null ? productType.getNameBn() : null);
        }
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            dto.setCompName(company != null ? company.getCompName() : null);
            dto.setCompNameBn(company != null ? company.getCompNameBn() : null);
        }
        if (entity.getBranchId() != null) {
            CompanyBranch companyBranch = companyBranchRepository.findById(entity.getBranchId()).orElse(null);
            dto.setBranchUnitName(companyBranch != null ? companyBranch.getBranchUnitName() : null);
            dto.setBranchUnitNameBn(companyBranch != null ? companyBranch.getBranchUnitNameBn() : null);
        }

        if (entity.getCurrencyId() != null) {
            Currency currency = currencyRepository.findById(entity.getCurrencyId()).orElse(null);
            dto.setCurrencyDesc(currency != null ? currency.getCurrencyDesc() : null);
            dto.setCurrencyShortCode(currency != null ? currency.getCurrencyShortCode() : null);
        }
        if (entity.getFiscalYearId() != null) {
            FiscalYearInfo fiscalYearInfo = fiscalYearInfoRepository.findById(entity.getFiscalYearId()).orElse(null);
            dto.setFiscalYear(fiscalYearInfo != null ? fiscalYearInfo.getFiscalYear() : null);
            dto.setFiscalYearBn(fiscalYearInfo != null ? fiscalYearInfo.getFiscalYearBn() : null);
        }
        if (entity.getVatMontId() != null) {
            VatMonthInfo vatMonthInfo = vatMonthInfoRepository.findById(entity.getVatMontId()).orElse(null);
            dto.setVmInfo(vatMonthInfo != null ? vatMonthInfo.getVmInfo() : null);
            dto.setVmInfoBn(vatMonthInfo != null ? vatMonthInfo.getVmInfoBn() : null);
        }

        List<DamageManagementChildResponseDto> issueChilds = entity.getIssueChild().stream()
                .map(customerAddress -> {
                    DamageManagementChildResponseDto issueChild = new DamageManagementChildResponseDto();
                    issueChild.setId(customerAddress.getId());
                    issueChild.setIssueMasterId(customerAddress.getIssueMasterId());
                    issueChild.setItemInfoId(customerAddress.getItemInfoId());
                    if (customerAddress.getItemInfoId() != null) {
                        Item item = itemRepository.findById(customerAddress.getItemInfoId()).orElse(null);
                        issueChild.setDisplayItmName(item != null ? item.getDisplayItmName() : null);
                        issueChild.setDisplayItmNameBn(item != null ? item.getDisplayItmNameBn() : null);
                    }
                    issueChild.setUomId(customerAddress.getUomId());
                    issueChild.setUomShortCode(customerAddress.getUomShortCode());
                    issueChild.setRelativeFactor(customerAddress.getRelativeFactor());
                    issueChild.setItemRate(customerAddress.getItemRate());
                    issueChild.setIssueQty(customerAddress.getIssueQty());
                    issueChild.setIssueRate(customerAddress.getIssueRate());
                    issueChild.setVatRateTypeId(customerAddress.getVatRateTypeId());
                    if (customerAddress.getVatRateTypeId() != null) {
                        VatRateType vatRateType = vatRateTypeRepository.findById(customerAddress.getVatRateTypeId())
                                .orElse(null);
                        issueChild.setVatRateTypeName(vatRateType != null ? vatRateType.getVatRateTypeName() : null);
                    }
                    issueChild.setIsFixedRate(customerAddress.getIsFixedRate());
                    issueChild.setCdPercent(customerAddress.getCdPercent());
                    issueChild.setCdAmount(customerAddress.getCdAmount());
                    issueChild.setRdPercent(customerAddress.getRdPercent());
                    issueChild.setRdAmount(customerAddress.getRdAmount());
                    issueChild.setSdPercent(customerAddress.getSdPercent());
                    issueChild.setSdAmount(customerAddress.getSdAmount());
                    issueChild.setVatPercent(customerAddress.getVatPercent());
                    issueChild.setVatAmount(customerAddress.getVatAmount());
                    issueChild.setAitPercent(customerAddress.getAitPercent());
                    issueChild.setAitAmount(customerAddress.getAitAmount());
                    issueChild.setAtAmount(customerAddress.getAtAmount());
                    issueChild.setAtPercent(customerAddress.getAtPercent());
                    issueChild.setCreatedAt(
                            DateUtil.convertDateToString(customerAddress.getCreatedAt(),
                                    ConstantValue.OUT_GOING_DATE_PATTERN));

                    if (customerAddress.getCreatedBy() != null) {
                        User createdByUser = userCredentials.findById(customerAddress.getCreatedBy()).orElse(null);
                        issueChild.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
                    }
                    return issueChild;
                }).collect(Collectors.toList());
        dto.setIssueChild(issueChilds);
        return dto;
    }

}
