package com.khajana.setting.service.vat;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.hscode.HsCodeResponseDto;
import com.khajana.setting.dto.hscode.HsCodeVatStructureResponseDto;
import com.khajana.setting.dto.vat.VatStructureParentRequestDto;
import com.khajana.setting.dto.vat.VatStructureRequestDto;
import com.khajana.setting.dto.vat.VatStructureResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.fy.FiscalYearInfo;
import com.khajana.setting.entity.hscode.HsCode;
import com.khajana.setting.entity.product.ProductCategory;
import com.khajana.setting.entity.product.ProductType;
import com.khajana.setting.entity.transactiontype.TransactionSubType;
import com.khajana.setting.entity.uom.Uom;
import com.khajana.setting.entity.vat.VatStructure;
import com.khajana.setting.entity.vatratereference.VatRateReference;
import com.khajana.setting.entity.vatratetype.VatRateType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.fy.FiscalYearInfoRepository;
import com.khajana.setting.repository.hscode.HsCodeRepository;
import com.khajana.setting.repository.product.ProductCategoryRepository;
import com.khajana.setting.repository.product.ProductTypeRepository;
import com.khajana.setting.repository.transactiontype.TransactionSubTypeRepository;
import com.khajana.setting.repository.uom.UomRepository;
import com.khajana.setting.repository.vat.VatStructureRepository;
import com.khajana.setting.repository.vatratereference.VatRateReferenceRepository;
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
public class VatStructureService {
    @Autowired
    VatStructureRepository vatStructureRepo;
    @Autowired

    HsCodeRepository hsCodeRepository;
    @Autowired
    VatRateReferenceRepository vatRateReferenceRepository;
    @Autowired
    TransactionSubTypeRepository transactionSubTypeRepository;
    @Autowired
    VatRateTypeRepository vatRateTypeRepository;
    @Autowired
    FiscalYearInfoRepository fiscalYearInfoRepository;
    @Autowired
    UomRepository uomRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    UserCredentialRepository userCredentials;
    CustomUserDetails userData = ContextUser.getLoginUserData();

    public SimplePage<VatStructureResponseDto> findAllVatStructures(Pageable pageable) {

        Page<VatStructure> page = vatStructureRepo.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public List<VatStructureResponseDto> getDropDown() {
        List<VatStructure> page = vatStructureRepo.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public HsCodeVatStructureResponseDto findVatStructureById(Long id) {
        VatStructure vatStructure = vatStructureRepo.findVatStructureById(id).orElse(null);
        HsCode hsCode = hsCodeRepository.findHsCodeById(vatStructure.getHsCodeId())
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return mapToDto(hsCode);
    }

    public ApiResponse addVatStructure(VatStructureParentRequestDto dto) {
        try {
            HsCode newEntity = hsCodeRepository.save(transferToEntity(dto));
            return new ApiResponse(200, "ok", transferToHsCodeDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateVatStructure(VatStructureParentRequestDto dto) {
        try {
            HsCode newEntity = hsCodeRepository.save(transferToEntity(dto));
            return new ApiResponse(200, "ok", transferToHsCodeDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private HsCode transferToEntity(VatStructureParentRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        HsCode hsCode = hsCodeRepository.findById(dto.getHsCodeId()).orElse(null);
        if (!dto.getVatStructures().isEmpty()) {
            for (VatStructureRequestDto vatStructureRequestDto : dto.getVatStructures()) {
                VatStructure vatStructure = new VatStructure();
                if (vatStructureRequestDto.getId() != null) {
                    vatStructure.setId(vatStructureRequestDto.getId());
                    vatStructure.setHsCodeId(dto.getHsCodeId());
                    vatStructure.setVatRateRefId(vatStructureRequestDto.getVatRateRefId());
                    vatStructure.setTranSubTypeId(vatStructureRequestDto.getTranSubTypeId());
                    vatStructure.setVatRateTypeId(vatStructureRequestDto.getVatRateTypeId());
                    vatStructure.setFiscalYearId(dto.getFiscalYearId());
                    vatStructure.setProdTypeId(vatStructureRequestDto.getProdTypeId());
                    vatStructure.setUomId(vatStructureRequestDto.getUomId());
                    vatStructure.setIsFixedRate(vatStructureRequestDto.getIsFixedRate());
                    vatStructure.setFixedRateUomId(vatStructureRequestDto.getFixedRateUomId());
                    vatStructure.setEffectiveDate(vatStructureRequestDto.getEffectiveDate());
                    vatStructure.setCd(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getCd()));
                    vatStructure.setRd(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getRd()));
                    vatStructure.setSd(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getSd()));
                    vatStructure.setAit(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getAit()));
                    vatStructure.setAtv(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getAtv()));
                    vatStructure.setExd(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getExd()));
                    vatStructure.setTti(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getTti()));
                    vatStructure.setVat(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getVat()));
                    vatStructure.setAt(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getAt()));
                    vatStructure.setFixedRate(vatStructureRequestDto.getFixedRate());
                    vatStructure.setActive(vatStructureRequestDto.getActive());
                    vatStructure.setUpdatedBy(userData.getId());
                    vatStructure.setUpdatedAt(new Date());
                    vatStructureRepo.save(vatStructure);
                } else {
                    vatStructure.setHsCodeId(dto.getHsCodeId());
                    vatStructure.setVatRateRefId(vatStructureRequestDto.getVatRateRefId());
                    vatStructure.setTranSubTypeId(vatStructureRequestDto.getTranSubTypeId());
                    vatStructure.setVatRateTypeId(vatStructureRequestDto.getVatRateTypeId());
                    vatStructure.setFiscalYearId(dto.getFiscalYearId());
                    vatStructure.setProdTypeId(vatStructureRequestDto.getProdTypeId());
                    vatStructure.setIsFixedRate(vatStructureRequestDto.getIsFixedRate());
                    vatStructure.setUomId(vatStructureRequestDto.getUomId());
                    vatStructure.setFixedRateUomId(vatStructureRequestDto.getFixedRateUomId());
                    vatStructure.setEffectiveDate(vatStructureRequestDto.getEffectiveDate());
                    vatStructure.setCd(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getCd()));
                    vatStructure.setRd(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getRd()));
                    vatStructure.setSd(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getSd()));
                    vatStructure.setAit(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getAit()));
                    vatStructure.setAtv(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getAtv()));
                    vatStructure.setExd(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getExd()));
                    vatStructure.setTti(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getTti()));
                    vatStructure.setVat(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getVat()));
                    vatStructure.setAt(NumberFormat.get2DigitDecimal(vatStructureRequestDto.getAt()));
                    vatStructure.setFixedRate(vatStructureRequestDto.getFixedRate());
                    vatStructure.setActive(vatStructureRequestDto.getActive());
                    vatStructure.setCreatedBy(userData.getId());
                    vatStructure.setCreatedAt(new Date());
                    vatStructureRepo.save(vatStructure);
                }
            }
        }
        return hsCode;
    }

    private VatStructureResponseDto transferToDTO(VatStructure vatStructure) {
        VatStructureResponseDto dto = new VatStructureResponseDto();
        dto.setId(vatStructure.getId());
        dto.setHsCodeId(vatStructure.getHsCodeId());
        if (vatStructure.getHsCodeId() != null) {
            HsCode hsName = hsCodeRepository.findById(vatStructure.getHsCodeId())
                    .orElse(null);
            dto.setHsCodeName(hsName != null ? hsName.getHsCode() : null);
        }

        dto.setVatRateRefId(vatStructure.getVatRateRefId());
        if (vatStructure.getVatRateRefId() != null) {
            VatRateReference vatRateReference = vatRateReferenceRepository.findById(vatStructure.getVatRateRefId())
                    .orElse(null);
            dto.setVatRateRefName(vatRateReference != null ? vatRateReference.getVatRateRefName() : null);
        }
        dto.setTranSubTypeId(vatStructure.getTranSubTypeId());
        if (vatStructure.getTranSubTypeId() != null) {
            TransactionSubType transactionSubType = transactionSubTypeRepository
                    .findById(vatStructure.getTranSubTypeId()).orElse(null);
            dto.setTranSubTypeName(transactionSubType != null ? transactionSubType.getTrnsSubTypeName() : null);
        }
        dto.setVatRateTypeId(vatStructure.getVatRateTypeId());
        if (vatStructure.getVatRateTypeId() != null) {
            VatRateType vatRateType = vatRateTypeRepository.findById(vatStructure.getVatRateTypeId()).orElse(null);
            dto.setVatRateTypeName(vatRateType != null ? vatRateType.getVatRateTypeName() : null);
        }
        dto.setFiscalYearId(vatStructure.getFiscalYearId());
        if (vatStructure.getFiscalYearId() != null) {
            FiscalYearInfo fiscalYear = fiscalYearInfoRepository.findById(vatStructure.getFiscalYearId()).orElse(null);
            dto.setFiscalYear(fiscalYear != null ? fiscalYear.getFiscalYear() : null);
        }
        dto.setProdTypeId(vatStructure.getProdTypeId());
        if (vatStructure.getProdTypeId() != null) {
            ProductType productType = productTypeRepository.findById(vatStructure.getProdTypeId()).orElse(null);
            dto.setProdTypeName(productType != null ? productType.getName() : null);
        }
        dto.setUomId(vatStructure.getUomId());
        if (vatStructure.getUomId() != null) {
            Uom uomName = uomRepository.findById(vatStructure.getUomId()).orElse(null);
            dto.setUomName(uomName != null ? uomName.getUomDesc() : null);
        }
        dto.setFixedRateUomId(vatStructure.getFixedRateUomId());
        dto.setEffectiveDate(
                DateUtil.convertDateToString(vatStructure.getEffectiveDate(), ConstantValue.ONLY_DATE));
        dto.setCd(NumberFormat.get2DigitDecimal(vatStructure.getCd()));
        dto.setRd(NumberFormat.get2DigitDecimal(vatStructure.getRd()));
        dto.setSd(NumberFormat.get2DigitDecimal(vatStructure.getSd()));
        dto.setAit(NumberFormat.get2DigitDecimal(vatStructure.getAit()));
        dto.setAtv(NumberFormat.get2DigitDecimal(vatStructure.getAtv()));
        dto.setExd(NumberFormat.get2DigitDecimal(vatStructure.getExd()));
        dto.setTti(NumberFormat.get2DigitDecimal(vatStructure.getTti()));
        dto.setVat(NumberFormat.get2DigitDecimal(vatStructure.getVat()));
        dto.setAt(NumberFormat.get2DigitDecimal(vatStructure.getAt()));
        dto.setFixedRate(vatStructure.getFixedRate());
        dto.setIsFixedRate(vatStructure.getIsFixedRate());
        dto.setActive(vatStructure.getActive());
        dto.setCreatedAt(
                DateUtil.convertDateToString(vatStructure.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(
                DateUtil.convertDateToString(vatStructure.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        if (vatStructure.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(vatStructure.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (vatStructure.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(vatStructure.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }

        return dto;
    }

    private HsCodeVatStructureResponseDto mapToDto(HsCode hsCode) {
        HsCodeVatStructureResponseDto dto = new HsCodeVatStructureResponseDto();
        dto.setId(hsCode.getId());
        dto.setHsCode(hsCode.getHsCode());
        dto.setSeqNo(hsCode.getSeqNo());
        dto.setActive(hsCode.getActive());

        List<VatStructureResponseDto> vatStructureResponseDtos = hsCode.getVatStructures().stream()
                .map(vatStructure -> transferToDTO(vatStructure))
                .collect(Collectors.toList());

        dto.setVatStructures(vatStructureResponseDtos);
        return dto;
    }

    private HsCodeResponseDto transferToHsCodeDTO(HsCode entity) {
        HsCodeResponseDto dto = new HsCodeResponseDto();
        dto.setId(entity.getId());
        dto.setHsCodeChapter(entity.getHsCodeChapter());
        dto.setHsCodeHeading(entity.getHsCodeHeading());
        dto.setHsCodeSection(entity.getHsCodeHeading());
        dto.setProductCategoryId(entity.getProductCategoryId());
        if (entity.getProductCategoryId() != null) {
            ProductCategory productCategory = productCategoryRepository.findProductCategoryById(entity.getProductCategoryId()).orElse(null);
            dto.setProductCategoryName(productCategory != null ? productCategory.getName() : null);
        }
        dto.setHsCode(entity.getHsCode());
        dto.setDescription(entity.getDescription());
        dto.setDescriptionBn(entity.getDescriptionBn());

        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setActive(entity.getActive());

        dto.setCreatedAt(
                DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(
                DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

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
}
