package com.khajana.setting.service.product;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.product.ProductCategoryRequestDto;
import com.khajana.setting.dto.product.ProductCategoryResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.product.ProductCategory;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
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
public class ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<ProductCategoryResponseDto> findAllProductCategorys(Pageable pageable) {
        Page<ProductCategory> page = productCategoryRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public ProductCategoryResponseDto findProductCategoryById(Long id) {

        ProductCategory newEntity = productCategoryRepository.findProductCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addProductCategory(ProductCategoryRequestDto dto) {
        try {
            boolean typeExists = productCategoryRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Product Category not allowed.", "error");
            }
            ProductCategory newEntity = productCategoryRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateProductCategory(Long id, ProductCategoryRequestDto dto) {
        try {
            boolean nameExistsForOtherId = productCategoryRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Product Category not allowed", "error");
            }
            ProductCategory newEntity = productCategoryRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<ProductCategoryResponseDto> getDropDown() {
        List<ProductCategory> page = productCategoryRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteProductCategory(Long id) {
        productCategoryRepository.deleteProductCategoryById(id);
    }

    private ProductCategory transferToEntity(Long id, ProductCategoryRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        ProductCategory productCategory = new ProductCategory();
        if (id != null && id > 0) {
            ProductCategory newEntity = productCategoryRepository.findProductCategoryById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            productCategory.setId(newEntity.getId());
            productCategory.setName(dto.getName());
            productCategory.setNameBn(dto.getNameBn());
            productCategory.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            productCategory.setActive(dto.getActive());
            productCategory.setUpdatedAt(new Date());
            productCategory.setUpdatedBy(userData.getId());

            return productCategory;
        } else {
            productCategory.setName(dto.getName());
            productCategory.setNameBn(dto.getNameBn());
            productCategory.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            productCategory.setActive(dto.getActive());
            productCategory.setCreatedAt(new Date());
            productCategory.setCreatedBy(userData.getId());
            return productCategory;
        }

    }

    private ProductCategoryResponseDto transferToDTO(ProductCategory entity) {
        ProductCategoryResponseDto dto = new ProductCategoryResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
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
}
