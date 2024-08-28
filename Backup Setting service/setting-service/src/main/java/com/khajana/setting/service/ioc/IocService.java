package com.khajana.setting.service.ioc;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.ioc.*;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.companybranch.Company;
import com.khajana.setting.entity.companybranch.CompanyBranch;
import com.khajana.setting.entity.ioc.Ioc;
import com.khajana.setting.entity.ioc.IocDetails;
import com.khajana.setting.entity.item.Item;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.entity.product.ProductType;
import com.khajana.setting.entity.uom.Uom;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyBranchRepository;
import com.khajana.setting.repository.companybranch.CompanyRepository;
import com.khajana.setting.repository.ioc.IocDetailRepository;
import com.khajana.setting.repository.ioc.IocRepository;
import com.khajana.setting.repository.item.ItemRepository;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.repository.product.ProductTypeRepository;
import com.khajana.setting.repository.uom.UomRepository;
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
public class IocService {

    @Autowired
    IocRepository iocRepository;

    @Autowired
    IocDetailRepository iocDetailRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    UomRepository uomRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CompanyBranchRepository companyBranchRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    @Autowired
    UserCredentialRepository userCredentials;
    /*
     * public Page<IocDto> findAllIocs(Pageable pageable) { Page<Ioc>
     * page = iocRepository.findAll(pageable); return
     * page.map(this::transferToDTO); }
     */

    public SimplePage<IocResponseDto> findAllIocs(Pageable pageable) {
        Page<Ioc> page = iocRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public IocResponseFindByIdDto findIocById(Long id) {

        Ioc newEntity = iocRepository.findIocById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionIoc", "Id", id));

        return transferToFindByIdDTO(newEntity);
    }

    public ApiResponse addIoc(IocRequestDto dto) {
        // Read user id from JWT Token
        try {
            Ioc newEntity = iocRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateIoc(Long id, IocRequestDto dto) {
        // Read user id from JWT Token
        try {
            Ioc newEntity = iocRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.iocDropdown();
        return result;
    }

    public List<HouseKeeping> iocProductTypeUomDropdown(Long ItemInfoId) {
        List<HouseKeeping> result = houseKeepingRepository.iocProductTypeUomDropdown(ItemInfoId);
        return result;
    }

    public void deleteIoc(Long id) {
        iocRepository.deleteIocById(id);
    }

    private Ioc transferToEntity(Long id, IocRequestDto dto) {
        Ioc ioc = new Ioc();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            ioc.setId(id);
            ioc.setItemInfoId(dto.getItemInfoId());
            ioc.setBranchId(dto.getBranchId());
            ioc.setItemInfoId(1L);
            ioc.setPrcDeclName(dto.getPrcDeclName());
            ioc.setIocUomId(dto.getIocUomId());
            ioc.setCalculationQty(dto.getCalculationQty());
            ioc.setIocQty(dto.getIocQty());
            ioc.setTotalRmCost(dto.getTotalRmCost());
            ioc.setTotalInputSvcCost(dto.getTotalInputSvcCost());
            ioc.setTotalValueAdditionCost(dto.getTotalValueAdditionCost());
            ioc.setGrandTotalCost(dto.getGrandTotalCost());
            ioc.setRemarks(dto.getRemarks());
            ioc.setDateOfSubmission(dto.getDateOfSubmission());
            ioc.setApprovedByNbr(dto.getApprovedByNbr());
            ioc.setEffectiveFrom(dto.getEffectiveFrom());
            ioc.setUpdatedAt(new Date());
            ioc.setUpdatedBy(userData.getId());

            Ioc iocSaved = iocRepository.save(ioc);
            iocSaved.setPrcDeclNumber("PD-" + ioc.getId());

            if (!dto.getIocDetails().isEmpty()) {
                for (IocDetailRequestDto iocDetailRequestDto : dto.getIocDetails()) {
                    IocDetails iocDetails = new IocDetails();

                    if (iocDetailRequestDto.getId() != null) {

                        iocDetails.setId(iocDetailRequestDto.getId());
                        iocDetails.setIocPriceDeclarationId(iocSaved.getId());
                        iocDetails.setProdTypeId(iocDetailRequestDto.getProdTypeId());
                        iocDetails.setItemInfoId(iocDetailRequestDto.getItemInfoId());
                        iocDetails.setConsumptionUomId(iocDetailRequestDto.getConsumptionUomId());
                        iocDetails.setConsumptionCalculationQty(iocDetailRequestDto.getConsumptionCalculationQty());
                        iocDetails.setConsumptionIocQty(iocDetailRequestDto.getConsumptionIocQty());
                        iocDetails.setPurchaseRate(iocDetailRequestDto.getPurchaseRate());
                        iocDetails.setWastageOfIocQty(iocDetailRequestDto.getWastageOfIocQty());
                        iocDetails.setIocQty(iocDetailRequestDto.getIocQty());
                        iocDetails.setWastageOfCalculationQty(iocDetailRequestDto.getWastageOfCalculationQty());
                        iocDetails.setWastagePercent(iocDetailRequestDto.getWastagePercent());
                        iocDetails.setCalculationAmt(iocDetailRequestDto.getCalculationAmt());
                        iocDetails.setIocAmt(iocDetailRequestDto.getIocAmt());
                        iocDetails.setIsRebatable(iocDetailRequestDto.getIsRebatable());

                        // iocDetails.setCreatedAt(new Date());
                        // iocDetails.setCreatedBy(userData.getId());
                        iocDetails.setUpdatedAt(new Date());
                        iocDetails.setUpdatedBy(userData.getId());

                        iocDetailRepository.save(iocDetails);
                    } else {
                        iocDetails.setIocPriceDeclarationId(iocSaved.getId());
                        iocDetails.setProdTypeId(iocDetailRequestDto.getProdTypeId());
                        iocDetails.setItemInfoId(iocDetailRequestDto.getItemInfoId());
                        iocDetails.setConsumptionUomId(iocDetailRequestDto.getConsumptionUomId());
                        iocDetails.setConsumptionCalculationQty(iocDetailRequestDto.getConsumptionCalculationQty());
                        iocDetails.setConsumptionIocQty(iocDetailRequestDto.getConsumptionIocQty());
                        iocDetails.setPurchaseRate(iocDetailRequestDto.getPurchaseRate());
                        iocDetails.setWastageOfIocQty(iocDetailRequestDto.getWastageOfIocQty());
                        iocDetails.setIocQty(iocDetailRequestDto.getIocQty());
                        iocDetails.setWastageOfCalculationQty(iocDetailRequestDto.getWastageOfCalculationQty());
                        iocDetails.setWastagePercent(iocDetailRequestDto.getWastagePercent());
                        iocDetails.setCalculationAmt(iocDetailRequestDto.getCalculationAmt());
                        iocDetails.setIocAmt(iocDetailRequestDto.getIocAmt());
                        iocDetails.setIsRebatable(iocDetailRequestDto.getIsRebatable());

                        iocDetails.setCreatedAt(new Date());
                        iocDetails.setCreatedBy(userData.getId());
                        // iocDetails.setUpdatedAt(new Date());
                        // iocDetails.setUpdatedBy(userData.getId());

                        iocDetailRepository.save(iocDetails);
                    }
                }
            }

            Item item = itemRepository.findById(ioc.getItemInfoId()).orElse(null);

            Double iocRate = ioc.getGrandTotalCost(); // Assuming ioc.getGrandTotalCost() returns a Double value

            // Calculate 7.5% less than iocRate
            Double iocMinRate = iocRate - (0.075 * iocRate);

            // Calculate 7.5% more than iocRate
            Double iocMaxRate = iocRate + (0.075 * iocRate);
            item.setIocRate(iocRate);
            item.setIocMinRate(iocMinRate);
            item.setIocMaxRate(iocMaxRate);
            item.setIocRefId(ioc.getId());
            item.setUpdatedAt(new Date());
            item.setUpdatedBy(userData.getId());
            return ioc;
        } else {

            ioc.setBranchId(dto.getBranchId());
            ioc.setItemInfoId(1L);
            ioc.setPrcDeclName(dto.getPrcDeclName());

            ioc.setItemInfoId(dto.getItemInfoId());
            ioc.setIocUomId(dto.getIocUomId());
            ioc.setCalculationQty(dto.getCalculationQty());
            ioc.setIocQty(dto.getIocQty());
            ioc.setTotalRmCost(dto.getTotalRmCost());
            ioc.setTotalInputSvcCost(dto.getTotalInputSvcCost());
            ioc.setTotalValueAdditionCost(dto.getTotalValueAdditionCost());
            ioc.setGrandTotalCost(dto.getGrandTotalCost());
            ioc.setRemarks(dto.getRemarks());
            ioc.setDateOfSubmission(dto.getDateOfSubmission());
            ioc.setApprovedByNbr(dto.getApprovedByNbr());
            ioc.setEffectiveFrom(dto.getEffectiveFrom());

            ioc.setCreatedAt(new Date());
            ioc.setCreatedBy(userData.getId());

            Ioc iocSaved = iocRepository.save(ioc);
            iocSaved.setPrcDeclNumber("PD-000" + iocSaved.getId());

            if (!dto.getIocDetails().isEmpty()) {
                for (IocDetailRequestDto iocDetailRequestDto : dto.getIocDetails()) {
                    IocDetails iocDetails = new IocDetails();

                    iocDetails.setIocPriceDeclarationId(iocSaved.getId());
                    iocDetails.setProdTypeId(iocDetailRequestDto.getProdTypeId());
                    iocDetails.setItemInfoId(iocDetailRequestDto.getItemInfoId());
                    iocDetails.setConsumptionUomId(iocDetailRequestDto.getConsumptionUomId());
                    iocDetails.setConsumptionCalculationQty(iocDetailRequestDto.getConsumptionCalculationQty());
                    iocDetails.setConsumptionIocQty(iocDetailRequestDto.getConsumptionIocQty());
                    iocDetails.setPurchaseRate(iocDetailRequestDto.getPurchaseRate());
                    iocDetails.setWastageOfIocQty(iocDetailRequestDto.getWastageOfIocQty());
                    iocDetails.setIocQty(iocDetailRequestDto.getIocQty());
                    iocDetails.setWastageOfCalculationQty(iocDetailRequestDto.getWastageOfCalculationQty());
                    iocDetails.setWastagePercent(iocDetailRequestDto.getWastagePercent());
                    iocDetails.setCalculationAmt(iocDetailRequestDto.getCalculationAmt());
                    iocDetails.setIocAmt(iocDetailRequestDto.getIocAmt());
                    iocDetails.setIsRebatable(iocDetailRequestDto.getIsRebatable());
                    // iocDetails.setSeqNo(iocDetailRequestDto.getSeqNo());

                    iocDetails.setCreatedAt(new Date());
                    iocDetails.setCreatedBy(userData.getId());

                    iocDetailRepository.save(iocDetails);
                }
            }

            Item item = itemRepository.findById(ioc.getItemInfoId()).orElse(null);

            Double iocRate = ioc.getGrandTotalCost(); // Assuming ioc.getGrandTotalCost() returns a Double value

            // Calculate 7.5% less than iocRate
            Double iocMinRate = iocRate - (0.075 * iocRate);

            // Calculate 7.5% more than iocRate
            Double iocMaxRate = iocRate + (0.075 * iocRate);
            item.setIocRate(iocRate);
            item.setIocMinRate(iocMinRate);
            item.setIocMaxRate(iocMaxRate);
            item.setIocRefId(ioc.getId());
            item.setUpdatedAt(new Date());
            item.setUpdatedBy(userData.getId());
            return ioc;
        }
    }

    private IocResponseDto transferToDTO(Ioc entity) {
        IocResponseDto dto = new IocResponseDto();
        dto.setId(entity.getId());

        dto.setItemInfoId(entity.getItemInfoId());
        if (entity.getItemInfoId() != null) {
            Item item = itemRepository.findById(entity.getItemInfoId()).orElse(null);
            dto.setItemInfoName(item != null ? item.getDisplayItmName() != null ? item.getDisplayItmName() : null : null);
        }
        dto.setPrcDeclName(entity.getPrcDeclName());
        dto.setPrcDeclNumber(entity.getPrcDeclNumber());
        dto.setIocUomId(entity.getIocUomId());
        // if (dto.getIocUomId() != null) {
        //     Uom uom = uomRepository.findById(dto.getIocUomId()).orElse(null);
        //     dto.setIocUomName(uom != null ? uom.getUomShortCode() : null);
        // }
        dto.setIocUomName(entity.getUom2() != null ? entity.getUom2().getUomShortCode() != null ? entity.getUom2().getUomShortCode() : null : null);
        if (entity.getCalculationQty() != null) {
            dto.setCalculationQty(NumberFormat.get2DigitDecimal(entity.getCalculationQty()));
        }
        if (entity.getIocQty() != null) {
            dto.setIocQty(NumberFormat.get2DigitDecimal(entity.getIocQty()));
        }
        if (entity.getTotalInputSvcCost() != null) {
            dto.setTotalInputSvcCost(NumberFormat.get2DigitDecimal(entity.getTotalInputSvcCost()));
        }
        if (entity.getTotalValueAdditionCost() != null) {
            dto.setTotalValueAdditionCost(NumberFormat.get2DigitDecimal(entity.getTotalValueAdditionCost()));
        }
        if (entity.getGrandTotalCost() != null) {
            dto.setGrandTotalCost(NumberFormat.get2DigitDecimal(entity.getGrandTotalCost()));
        }
        dto.setRemarks(entity.getRemarks());
        dto.setTotalRmCost(entity.getTotalRmCost());
        dto.setDateOfSubmission(
                DateUtil.convertDateToString(entity.getDateOfSubmission(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setApprovedByNbr(
                DateUtil.convertDateToString(entity.getApprovedByNbr(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setEffectiveFrom(
                DateUtil.convertDateToString(entity.getEffectiveFrom(), ConstantValue.OUT_GOING_DATE_PATTERN));

        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }
        if (entity.getBranchId() != null) {
            CompanyBranch companyBranch = companyBranchRepository.findById(entity.getBranchId()).orElse(null);
            dto.setBranchId(companyBranch != null ? companyBranch.getId() : null);
            dto.setBranchUnitName(companyBranch != null ? companyBranch.getBranchUnitName() : null);
            if (companyBranch != null && companyBranch.getCompanyId() != null) {
                Company company = companyRepository.findById(companyBranch.getCompanyId()).orElse(null);
                dto.setCompanyId(company != null ? company.getId() : null);
                dto.setCompanyName(company != null ? company.getCompName() : null);
            }
        }

        if (entity.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(entity.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }
        return dto;
    }

    private IocResponseFindByIdDto transferToFindByIdDTO(Ioc entity) {
        IocResponseFindByIdDto dto = new IocResponseFindByIdDto();
        dto.setId(entity.getId());
        dto.setPrcDeclName(entity.getPrcDeclName());
        dto.setItemInfoId(entity.getItemInfoId());
        if (entity.getItemInfoId() != null) {
            Item item = itemRepository.findById(entity.getItemInfoId()).orElse(null);
            dto.setItemInfoName(item != null ? item.getDisplayItmName() != null ? item.getDisplayItmName() : null : null);
        }
        dto.setTotalRmCost(entity.getTotalRmCost());
        dto.setPrcDeclNumber(entity.getPrcDeclNumber());
        dto.setIocUomId(entity.getIocUomId());
        if (dto.getIocUomId() != null) {
            Uom uom = uomRepository.findById(dto.getIocUomId()).orElse(null);
            dto.setIocUomName(uom != null ? uom.getUomShortCode() : null);
        }
        if (entity.getCalculationQty() != null) {
            dto.setCalculationQty(NumberFormat.get2DigitDecimal(entity.getCalculationQty()));
        }
        if (entity.getIocQty() != null) {
            dto.setIocQty(NumberFormat.get2DigitDecimal(entity.getIocQty()));
        }
        if (entity.getTotalInputSvcCost() != null) {
            dto.setTotalInputSvcCost(NumberFormat.get2DigitDecimal(entity.getTotalInputSvcCost()));
        }
        if (entity.getTotalValueAdditionCost() != null) {
            dto.setTotalValueAdditionCost(NumberFormat.get2DigitDecimal(entity.getTotalValueAdditionCost()));
        }
        if (entity.getGrandTotalCost() != null) {
            dto.setGrandTotalCost(NumberFormat.get2DigitDecimal(entity.getGrandTotalCost()));
        }
        dto.setRemarks(entity.getRemarks());
        dto.setDateOfSubmission(
                DateUtil.convertDateToString(entity.getDateOfSubmission(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setApprovedByNbr(
                DateUtil.convertDateToString(entity.getApprovedByNbr(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setEffectiveFrom(
                DateUtil.convertDateToString(entity.getEffectiveFrom(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (entity.getBranchId() != null) {
            CompanyBranch companyBranch = companyBranchRepository.findById(entity.getBranchId()).orElse(null);
            dto.setBranchId(companyBranch != null ? companyBranch.getId() : null);
            dto.setBranchUnitName(companyBranch != null ? companyBranch.getBranchUnitName() : null);
            if (companyBranch != null && companyBranch.getCompanyId() != null) {
                Company company = companyRepository.findById(companyBranch.getCompanyId()).orElse(null);
                dto.setCompanyId(company != null ? company.getId() : null);
                dto.setCompanyName(company != null ? company.getCompName() : null);
            }
        }
        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (entity.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(entity.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }
        if (!entity.getIocDetails().isEmpty()) {
            List<IocDetailResponseDto> iocDetails = entity.getIocDetails().stream().map(ioc -> {
                IocDetailResponseDto iocDetail = new IocDetailResponseDto();
                iocDetail.setId(ioc.getId());
                iocDetail.setIocPriceDeclarationId(ioc.getIocPriceDeclarationId());
                if (ioc.getIocPriceDeclarationId() != null) {
                    Ioc iocUpdate = iocRepository.findById(ioc.getIocPriceDeclarationId()).orElse(null);
                    iocDetail.setIocName(iocUpdate != null ? iocUpdate.getPrcDeclName() : null);
                }
                if (ioc.getProdTypeId() != null) {
                    ProductType productType = productTypeRepository.findById(ioc.getProdTypeId()).orElse(null);
                    iocDetail.setProdTypeName(productType != null ? productType.getName() : null);
                }
                if (ioc.getItemInfoId() != null) {
                    Item item = itemRepository.findById(ioc.getItemInfoId()).orElse(null);
                    iocDetail.setItemInfoName(item != null ? item.getDisplayItmName() : null);
                    iocDetail.setItemInfoNameBn(item != null ? item.getDisplayItmNameBn() : null);
                }
                if (ioc.getConsumptionUomId() != null) {
                    Uom uom = uomRepository.findById(ioc.getConsumptionUomId()).orElse(null);
                    iocDetail.setConsumptionUomName(uom != null ? uom.getUomDesc() : null);
                }
                iocDetail.setProdTypeId(ioc.getProdTypeId());
                iocDetail.setItemInfoId(ioc.getItemInfoId());
                iocDetail.setConsumptionUomId(ioc.getConsumptionUomId());
                iocDetail.setConsumptionCalculationQty(ioc.getConsumptionCalculationQty());
                iocDetail.setConsumptionIocQty(ioc.getConsumptionIocQty());
                iocDetail.setWastageOfCalculationQty(ioc.getWastageOfCalculationQty());
                iocDetail.setWastageOfIocQty(ioc.getWastageOfIocQty());
                iocDetail.setPurchaseRate(ioc.getPurchaseRate());
                iocDetail.setIocQty(ioc.getIocQty());
                iocDetail.setWastagePercent(ioc.getWastagePercent());
                iocDetail.setCalculationAmt(ioc.getCalculationAmt());
                iocDetail.setIocAmt(ioc.getIocAmt());
                iocDetail.setIsRebatable(ioc.getIsRebatable());
                return iocDetail;
            }).collect(Collectors.toList());
            dto.setIocDetails(iocDetails);
        }
        return dto;
    }
}
