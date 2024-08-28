package com.khajana.setting.service.vat;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.vat.VatRateStageRequestDto;
import com.khajana.setting.dto.vat.VatRateStageResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.product.ProductCategory;
import com.khajana.setting.entity.vat.VatRateStage;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.product.ProductCategoryRepository;
import com.khajana.setting.repository.vat.VatRateStageRepository;
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
public class VatRateStageService {
    @Autowired
    VatRateStageRepository vatRateStageRepo;

    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<VatRateStageResponseDto> findAllVatRateStages(Pageable pageable) {

        Page<VatRateStage> page = vatRateStageRepo.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public List<VatRateStageResponseDto> getDropDown() {
        List<VatRateStage> page = vatRateStageRepo.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public VatRateStageResponseDto findVatRateStageById(Long id) {
        VatRateStage vatRateStage = vatRateStageRepo.findVatRateStageById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(vatRateStage);
    }

    public ApiResponse addVatRateStage(VatRateStageRequestDto dto) {
        try {
            boolean typeExists = vatRateStageRepo.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate VAT Rate Stage Name not allowed", "error");
            }
            VatRateStage newEntity = vatRateStageRepo.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateVatRateStage(Long id, VatRateStageRequestDto dto) {
        try {
            boolean nameExistsForOtherId = vatRateStageRepo.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate VAT Rate Stage Name not allowed", "error");
            }
            VatRateStage newEntity = vatRateStageRepo.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private VatRateStage transferToEntity(Long id, VatRateStageRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        VatRateStage vatRateStage = new VatRateStage();
        if (id != null && id > 0) {
            ProductCategory productCategory = productCategoryRepository.findById(dto.getProductCategoryId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("ProductCategory", "Id", dto.getProductCategoryId()));
            vatRateStage.setProductCategory(productCategory);
            vatRateStage.setId(id);
            vatRateStage.setProductCategoryId(dto.getProductCategoryId());
            vatRateStage.setName(dto.getName());
            vatRateStage.setNameBn(dto.getNameBn());
            vatRateStage.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            vatRateStage.setActive(dto.getActive());
            vatRateStage.setUpdatedAt(new Date());
            vatRateStage.setUpdatedBy(userData.getId());

            return vatRateStage;
        } else {
            ProductCategory productCategory = productCategoryRepository.findById(dto.getProductCategoryId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("ProductCategory", "Id", dto.getProductCategoryId()));
            vatRateStage.setProductCategory(productCategory);
            vatRateStage.setProductCategoryId(dto.getProductCategoryId());
            vatRateStage.setName(dto.getName());
            vatRateStage.setNameBn(dto.getNameBn());
            vatRateStage.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            vatRateStage.setActive(dto.getActive());
            vatRateStage.setCreatedAt(new Date());
            vatRateStage.setCreatedBy(userData.getId());
            return vatRateStage;
        }
    }

    private VatRateStageResponseDto transferToDTO(VatRateStage vatRateStage) {
        VatRateStageResponseDto dto = new VatRateStageResponseDto();
        dto.setId(vatRateStage.getId());
        dto.setProductCategoryId(vatRateStage.getProductCategoryId());
        dto.setProductCategoryName(vatRateStage.getProductCategory().getName());
        dto.setProductCategoryNameBn(vatRateStage.getProductCategory().getNameBn());
        dto.setName(vatRateStage.getName());
        dto.setNameBn(vatRateStage.getNameBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(vatRateStage.getSeqNo()));
        dto.setActive(vatRateStage.getActive());
        dto.setCreatedAt(
                DateUtil.convertDateToString(vatRateStage.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(
                DateUtil.convertDateToString(vatRateStage.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        if (vatRateStage.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(vatRateStage.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (vatRateStage.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(vatRateStage.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }
        return dto;
    }
}
