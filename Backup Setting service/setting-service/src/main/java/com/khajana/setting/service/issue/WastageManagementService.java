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
import com.khajana.setting.entity.transactiontype.TransactionSubType;
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
import com.khajana.setting.repository.issue.StockChildRepository;
import com.khajana.setting.repository.issue.StockMasterRepository;
import com.khajana.setting.repository.issue.WastageManagementChildRepository;
import com.khajana.setting.repository.issue.WastageManagementRepository;
import com.khajana.setting.repository.item.ItemRepository;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.repository.product.ProductTypeRepository;
import com.khajana.setting.repository.transactiontype.TransactionSourceTypeRepository;
import com.khajana.setting.repository.transactiontype.TransactionSubTypeRepository;
import com.khajana.setting.repository.transactiontype.TransactionTypeRepository;
import com.khajana.setting.repository.vat.VatMonthInfoRepository;
import com.khajana.setting.repository.vatratetype.VatRateTypeRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WastageManagementService {

    @Autowired
    WastageManagementRepository issueIssueMasterRepo;
    @Autowired
    UserCredentialRepository userCredentials;
    @Autowired
    WastageManagementChildRepository wastageManagementChildRepository;
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
    HouseKeepingRepository houseKeepingRepository;

    @Autowired
    StockMasterRepository stockMasterRepository;

    @Autowired
    StockChildRepository stockChildRepository;
    @Autowired
    TransactionSourceTypeRepository transactionSourceTypeRepository;
    @Autowired
    TransactionTypeRepository transactionTypeRepository;
    @Autowired
    TransactionSubTypeRepository transactionSubTypeRepository;

    public ApiResponse addWastageManagement(WastageManagementMasterRequestDto dto) {
        try {
            IssueMaster newEntity = issueIssueMasterRepo.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public SimplePage<WastageManagementResponseDto> findAlltemCat(Pageable pageable) {
        TransactionType transactionType = transactionTypeRepository.findTransactionTypeByTrnsTypeName("Wastage")
                .orElse(null);
        Long transactionTypeId = transactionType.getId();
        ProductType productType = productTypeRepository.findProductTypeByName("Raw Materials").orElse(null);
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

    public List<HouseKeeping> getHsCodeFromReceiveId(Long id) {
        List<HouseKeeping> result = houseKeepingRepository.getHsCodeFromReceiveId(id);
        return result;
    }

    public List<ReceiveChildForWastageResponseDto> getItemsOfWastage(Long hsCodeId, Long ReceiveMasterId) {
        List<ReceiveChildForWastageResponseDto> result = houseKeepingRepository.getItemsOfWastage(hsCodeId,
                ReceiveMasterId);
        return result;
    }

    public WastageManagementFindByIdResponseDto findWastageManagementById(Long id) {

        IssueMaster newEntity = issueIssueMasterRepo.findIssueMasterById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToFindByIdDTO(newEntity);
    }

    IssueMaster transferToEntity(Long id, WastageManagementMasterRequestDto dto) {
        IssueMaster issue = new IssueMaster();
        TransactionSourceType transactionSourceType = transactionSourceTypeRepository
                .findSourceTypeByTranSourceTypeName("Issue").orElse(null);
        TransactionType transactionType = transactionTypeRepository.findTransactionTypeByTrnsTypeName("Wastage")
                .orElse(null);
        TransactionSubType transactionSubType = transactionSubTypeRepository.findSourceTypeByTrnsSubTypeName("Factory")
                .orElse(null);

        CustomUserDetails userData = ContextUser.getLoginUserData();
        issue.setReceiveMasterId(dto.getReceiveMasterId());
        issue.setProdTypeId(dto.getProdTypeId());
        issue.setCompanyId(dto.getCompanyId());
        issue.setBranchId(dto.getBranchId());
        issue.setStoreId(dto.getStoreId());
        issue.setCurrencyId(dto.getCurrencyId());
        issue.setExcgRate(dto.getExcgRate());

        if (transactionSourceType != null && transactionSourceType.getId() != null) {
            issue.setTranSourceTypeId(transactionSourceType.getId());
        }
        if (transactionType != null && transactionType.getId() != null) {
            issue.setTranTypeId(transactionType.getId());
        }
        if (transactionSubType != null && transactionSubType.getId() != null) {
            issue.setTranSubTypeId(transactionSubType.getId());
        }

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
        issue.setRemarksBn("Wastage Management");

        issue.setCreatedAt(new Date());
        issue.setCreatedBy(userData.getId());
        issue.setCreatedAt(new Date());
        issue.setCreatedBy(userData.getId());
        IssueMaster issueMaster = issueIssueMasterRepo.save(issue);
        issueMaster.setIssueNo("WM-" + issueMaster.getId());
        issueMaster.setIssueNoBn("WM-" + issueMaster.getId());

        if (!dto.getIssueChild().isEmpty()) {
            for (WastageManagementChildRequestDto issuechildDto : dto.getIssueChild()) {
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
                issueChild.setIsFixedRate(issuechildDto.getIsFixedRate());
                issueChild.setCdPercent(issuechildDto.getCdPercent());
                issueChild.setCdAmount(issuechildDto.getCdAmount());
                issueChild.setRdPercent(issuechildDto.getRdPercent());
                issueChild.setRdAmount(issuechildDto.getRdAmount());
                issueChild.setSdPercent(issuechildDto.getSdPercent());
                issueChild.setSdAmount(issuechildDto.getSdAmount());
                issueChild.setVatPercent(issuechildDto.getVatPercent());
                issueChild.setVatAmount(issuechildDto.getVatAmount());
                issueChild.setAitPercent(issuechildDto.getAitPercent());
                issueChild.setAitAmount(issuechildDto.getAitAmount());
                issueChild.setAtAmount(issuechildDto.getAtAmount());
                issueChild.setAtPercent(issuechildDto.getAtPercent());
                Double issueQty = issuechildDto.getIssueQty() != null ? issuechildDto.getIssueQty() : 0.0;
                Double itemRate = issuechildDto.getItemRate() != null ? issuechildDto.getItemRate() : 0.0;
                Double sdAmount = issueChild.getSdAmount() != null ? issueChild.getSdAmount() : 0.0;
                Double vatAmount = issueChild.getVatAmount() != null ? issueChild.getVatAmount() : 0.0;

                issueChild.setItemValueTranCurr(issueQty * itemRate);
                issueChild.setItemValueLocalCurr(issueQty * itemRate);
                issueChild.setTotalAmountTranCurr(issueChild.getItemValueTranCurr() + sdAmount + vatAmount);
                issueChild.setTotalAmountLocalCurr(issueChild.getItemValueTranCurr() + sdAmount + vatAmount);

                issueChild.setCreatedAt(new Date());
                issueChild.setCreatedBy(userData.getId());
                wastageManagementChildRepository.save(issueChild);

                StockMaster stockMaster = new StockMaster();
                stockMaster.setReceiveMasterId(issueMaster.getId());
                stockMaster.setTranTypeId(issueMaster.getTranTypeId());
                stockMaster.setTranSubTypeId(issueMaster.getTranSubTypeId());
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

    WastageManagementResponseDto transferToDTO(IssueMaster entity) {

        WastageManagementResponseDto dto = new WastageManagementResponseDto();
        dto.setId(entity.getId());
        dto.setReceiveMasterId(entity.getReceiveMasterId());
        dto.setProdTypeId(entity.getProdTypeId());
        dto.setCompanyId(entity.getCompanyId());
        dto.setBranchId(entity.getBranchId());
        dto.setStoreId(entity.getStoreId());
        dto.setCurrencyId(entity.getCurrencyId());
        dto.setIssueNo(entity.getIssueNo());
        dto.setIssueNoBn(entity.getIssueNoBn());
        dto.setIssueDate(DateUtil.convertDateToString(entity.getIssueDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setExcgRate(NumberFormat.get2DigitDecimal(entity.getExcgRate()));
        // dto.setCustomerId(entity.getCustomerId());
        dto.setFiscalYearId(entity.getFiscalYearId());
        dto.setVatMontId(entity.getVatMontId());
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setTotalIsAmBeDiscount(NumberFormat.get2DigitDecimal(entity.getTotalIsAmBeDiscount()));
        dto.setTotalIsAmLocalCurrBeDiscount(NumberFormat.get2DigitDecimal(entity.getTotalIsAmLocalCurrBeDiscount()));
        dto.setTotalCdAmount(NumberFormat.get2DigitDecimal(entity.getTotalCdAmount()));
        dto.setTotalRdAmount(NumberFormat.get2DigitDecimal(entity.getTotalRdAmount()));
        dto.setTotalSdAmount(NumberFormat.get2DigitDecimal(entity.getTotalSdAmount()));
        dto.setTotalVatAmount(NumberFormat.get2DigitDecimal(entity.getTotalVatAmount()));
        dto.setTotalIssueAmtLocalCurr(NumberFormat.get2DigitDecimal(entity.getTotalIssueAmtLocalCurr()));
        dto.setTotalIssueAmtTransCurr(NumberFormat.get2DigitDecimal(entity.getTotalIssueAmtTransCurr()));
        dto.setRemarks(entity.getRemarks());
        dto.setRemarksBn("Wastage Management");
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (entity.getTranSourceTypeId() != null) {
            TransactionSourceType transactionSourceType = transactionSourceTypeRepository
                    .findById(entity.getTranSourceTypeId()).orElse(null);
            if (transactionSourceType != null) {
                dto.setTranSourceTypeId(transactionSourceType.getId());
                dto.setTranSoureName(transactionSourceType.getTranSourceTypeName());
            }
        }
        if (entity.getTranSubTypeId() != null) {
            TransactionSubType transactionSubType = transactionSubTypeRepository.findById(entity.getTranSubTypeId())
                    .orElse(null);
            if (transactionSubType != null) {
                dto.setTranSubTypeId(transactionSubType.getId());
                dto.setTranSubTypeName(transactionSubType.getTrnsSubTypeName());
            }
        }
        if (entity.getTranTypeId() != null) {
            TransactionType transactionType = transactionTypeRepository.findById(entity.getTranTypeId()).orElse(null);
            if (transactionType != null) {
                dto.setTranTypeId(transactionType.getId());
                dto.setTranTypeName(transactionType.getTrnsTypeName());
            }
        }

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
        // if (entity.getStoreId() != null) {
        // CompanyStore companyStore =
        // companyStoreRepository.findById(entity.getStoreId()).orElse(null);
        // dto.setSlName(companyStore != null ? companyStore.getSlName() : null);
        // dto.setSlNameBn(companyStore != null ? companyStore.getSlNameBn() : null);
        // }
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

    WastageManagementFindByIdResponseDto transferToFindByIdDTO(IssueMaster entity) {

        WastageManagementFindByIdResponseDto dto = new WastageManagementFindByIdResponseDto();
        dto.setId(entity.getId());
        dto.setReceiveMasterId(entity.getReceiveMasterId());
        dto.setProdTypeId(entity.getProdTypeId());
        dto.setCompanyId(entity.getCompanyId());
        dto.setBranchId(entity.getBranchId());
        dto.setStoreId(entity.getStoreId());
        dto.setCurrencyId(entity.getCurrencyId());
        dto.setIssueNo(entity.getIssueNo());
        dto.setIssueNoBn(entity.getIssueNoBn());
        dto.setIssueDate(DateUtil.convertDateToString(entity.getIssueDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setExcgRate(NumberFormat.get2DigitDecimal(entity.getExcgRate()));
        // dto.setCustomerId(entity.getCustomerId());
        dto.setFiscalYearId(entity.getFiscalYearId());
        dto.setVatMontId(entity.getVatMontId());
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setTotalIsAmBeDiscount(NumberFormat.get2DigitDecimal(entity.getTotalIsAmBeDiscount()));
        dto.setTotalIsAmLocalCurrBeDiscount(NumberFormat.get2DigitDecimal(entity.getTotalIsAmLocalCurrBeDiscount()));
        dto.setTotalCdAmount(NumberFormat.get2DigitDecimal(entity.getTotalCdAmount()));
        dto.setTotalRdAmount(NumberFormat.get2DigitDecimal(entity.getTotalRdAmount()));
        dto.setTotalSdAmount(NumberFormat.get2DigitDecimal(entity.getTotalSdAmount()));
        dto.setTotalVatAmount(NumberFormat.get2DigitDecimal(entity.getTotalVatAmount()));
        dto.setTotalIssueAmtLocalCurr(NumberFormat.get2DigitDecimal(entity.getTotalIssueAmtLocalCurr()));
        dto.setTotalIssueAmtTransCurr(NumberFormat.get2DigitDecimal(entity.getTotalIssueAmtTransCurr()));
        dto.setRemarks(entity.getRemarks());
        dto.setRemarksBn("Wastage Management");
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (entity.getTranSourceTypeId() != null) {
            TransactionSourceType transactionSourceType = transactionSourceTypeRepository
                    .findById(entity.getTranSourceTypeId()).orElse(null);
            if (transactionSourceType != null) {
                dto.setTranSourceTypeId(transactionSourceType.getId());
                dto.setTranSoureName(transactionSourceType.getTranSourceTypeName());
            }
        }
        if (entity.getTranSubTypeId() != null) {
            TransactionSubType transactionSubType = transactionSubTypeRepository.findById(entity.getTranSubTypeId())
                    .orElse(null);
            if (transactionSubType != null) {
                dto.setTranSubTypeId(transactionSubType.getId());
                dto.setTranSubTypeName(transactionSubType.getTrnsSubTypeName());
            }
        }
        if (entity.getTranTypeId() != null) {
            TransactionType transactionType = transactionTypeRepository.findById(entity.getTranTypeId()).orElse(null);
            if (transactionType != null) {
                dto.setTranTypeId(transactionType.getId());
                dto.setTranTypeName(transactionType.getTrnsTypeName());
            }
        }

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
        // if (entity.getStoreId() != null) {
        // CompanyStore companyStore =
        // companyStoreRepository.findById(entity.getStoreId()).orElse(null);
        // dto.setSlName(companyStore != null ? companyStore.getSlName() : null);
        // dto.setSlNameBn(companyStore != null ? companyStore.getSlNameBn() : null);
        // }
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

        List<WastageManagementChildResponseDto> issueChilds = entity.getIssueChild().stream()
                .map(customerAddress -> {
                    WastageManagementChildResponseDto issueChild = new WastageManagementChildResponseDto();
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
                    issueChild.setItemRate(NumberFormat.get2DigitDecimal(customerAddress.getItemRate()));
                    issueChild.setIssueQty(NumberFormat.get2DigitDecimal(customerAddress.getIssueQty()));
                    issueChild.setIssueRate(NumberFormat.get2DigitDecimal(customerAddress.getIssueRate()));
                    issueChild.setVatRateTypeId(customerAddress.getVatRateTypeId());
                    if (customerAddress.getVatRateTypeId() != null) {
                        VatRateType vatRateType = vatRateTypeRepository.findById(customerAddress.getVatRateTypeId())
                                .orElse(null);
                        issueChild.setVatRateTypeName(vatRateType != null ? vatRateType.getVatRateTypeName() : null);
                    }
                    issueChild.setIsFixedRate(customerAddress.getIsFixedRate());
                    issueChild.setCdPercent(NumberFormat.get2DigitDecimal(customerAddress.getCdPercent()));
                    issueChild.setCdAmount(NumberFormat.get2DigitDecimal(customerAddress.getCdAmount()));
                    issueChild.setRdPercent(NumberFormat.get2DigitDecimal(customerAddress.getRdPercent()));
                    issueChild.setRdAmount(NumberFormat.get2DigitDecimal(customerAddress.getRdAmount()));
                    issueChild.setSdPercent(NumberFormat.get2DigitDecimal(customerAddress.getSdPercent()));
                    issueChild.setSdAmount(NumberFormat.get2DigitDecimal(customerAddress.getSdAmount()));
                    issueChild.setVatPercent(NumberFormat.get2DigitDecimal(customerAddress.getVatPercent()));
                    issueChild.setVatAmount(NumberFormat.get2DigitDecimal(customerAddress.getVatAmount()));
                    issueChild.setAitPercent(NumberFormat.get2DigitDecimal(customerAddress.getAitPercent()));
                    issueChild.setAitAmount(NumberFormat.get2DigitDecimal(customerAddress.getAitAmount()));
                    issueChild.setAtAmount(NumberFormat.get2DigitDecimal(customerAddress.getAtAmount()));
                    issueChild.setAtPercent(NumberFormat.get2DigitDecimal(customerAddress.getAtPercent()));
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
