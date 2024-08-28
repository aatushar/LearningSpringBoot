package com.khajana.setting.service.hscode;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.hscode.HsCodeRequestDto;
import com.khajana.setting.dto.hscode.HsCodeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.hscode.HsCode;
import com.khajana.setting.entity.product.ProductCategory;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.hscode.HsCodeRepository;
import com.khajana.setting.repository.product.ProductCategoryRepository;
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
public class HsCodeService {

    @Autowired
    HsCodeRepository hsCodeRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public SimplePage<HsCodeResponseDto> findAllHsCodes(Pageable pageable) {
        Page<HsCode> page = hsCodeRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public HsCodeResponseDto findHsCodeById(Long id) {

        HsCode newEntity = hsCodeRepository.findHsCodeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addHsCode(HsCodeRequestDto dto) {
        try {
            boolean typeExists = hsCodeRepository.existsByHsCode(dto.getHsCode());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate HsCode not allowed", "error");
            }
            HsCode newEntity = hsCodeRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public HsCodeResponseDto updateHsCode(Long id, HsCodeRequestDto dto) {
//        HsCode newEntity = hsCodeRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateHsCode(Long id, HsCodeRequestDto dto) {
        try {
            boolean nameExistsForOtherId = hsCodeRepository.existsByHsCodeAndIdNot(dto.getHsCode(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate HsCode not allowed", "error");
            }
            HsCode newEntity = hsCodeRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HsCodeResponseDto> getDropDown() {
        List<HsCode> page = hsCodeRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteHsCode(Long id) {
        hsCodeRepository.deleteHsCodeById(id);
    }

    private HsCode transferToEntity(Long id, HsCodeRequestDto dto) {
        HsCode hsCode = new HsCode();
        CustomUserDetails userData = ContextUser.getLoginUserData();
        if (id != null && id > 0) {
            HsCode newEntity = hsCodeRepository.findHsCodeById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            hsCode.setId(newEntity.getId());
            hsCode.setHsCodeChapter(dto.getHsCodeChapter());
            hsCode.setHsCodeHeading(dto.getHsCodeHeading());
            hsCode.setHsCodeSection(dto.getHsCodeSection());
            hsCode.setProductCategoryId(dto.getProductCategoryId());
            hsCode.setHsCode(dto.getHsCode());
            hsCode.setDescription(dto.getDescription());
            hsCode.setDescriptionBn(dto.getDescriptionBn());
            hsCode.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            hsCode.setActive(dto.getActive());
            hsCode.setUpdatedAt(new Date());
            hsCode.setUpdatedBy(userData.getId());

            return hsCode;
        } else {
            hsCode.setHsCodeChapter(dto.getHsCodeChapter());
            hsCode.setHsCodeHeading(dto.getHsCodeHeading());
            hsCode.setHsCodeSection(dto.getHsCodeSection());
            hsCode.setProductCategoryId(dto.getProductCategoryId());
            hsCode.setHsCode(dto.getHsCode());
            hsCode.setDescription(dto.getDescription());
            hsCode.setDescriptionBn(dto.getDescriptionBn());
            hsCode.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            hsCode.setActive(dto.getActive());
            hsCode.setCreatedBy(userData.getId());
            hsCode.setCreatedAt(new Date());

            return hsCode;
        }

    }

    private HsCodeResponseDto transferToDTO(HsCode entity) {
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
