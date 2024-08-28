package com.khajana.setting.service.product;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.item.ItemMasterGroupProductTypeResponseDto;
import com.khajana.setting.dto.product.ProductTypeFindByIdResponseDto;
import com.khajana.setting.dto.product.ProductTypeRequestDto;
import com.khajana.setting.dto.product.ProductTypeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.product.ProductCategory;
import com.khajana.setting.entity.product.ProductType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.product.ProductCategoryRepository;
import com.khajana.setting.repository.product.ProductTypeRepository;
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
public class ProductTypeService {

    @Autowired
    ProductTypeRepository productTypeRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<ProductTypeResponseDto> findAllProductTypes(Pageable pageable) {
        Page<ProductType> page = productTypeRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public ProductTypeFindByIdResponseDto findProductTypeById(Long id) {

        ProductType newEntity = productTypeRepository.findProductTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToFindByIdDTO(newEntity);
    }

    public ApiResponse addProductType(ProductTypeRequestDto dto) {
        try {
            boolean typeExists = productTypeRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Product Type not allowed", "error");
            }
            ProductType newEntity = productTypeRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public ProductTypeResponseDto updateProductType(Long id, ProductTypeRequestDto dto) {
//        ProductType newEntity = productTypeRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
    public ApiResponse updateProductType(Long id, ProductTypeRequestDto dto) {
        try {
            boolean nameExistsForOtherId = productTypeRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Product Type not allowed", "error");
            }
            ProductType newEntity = productTypeRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }


    public List<ProductTypeResponseDto> getDropDown() {
        List<ProductType> page = productTypeRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteProductType(Long id) {
        productTypeRepository.deleteProductTypeById(id);
    }

    private ProductType transferToEntity(Long id, ProductTypeRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        ProductType productType = new ProductType();
        if (id != null && id > 0) {
            ProductCategory productCategory = productCategoryRepository.findById(dto.getProductCategoryId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("ProductCategory", "Id", dto.getProductCategoryId()));
            productType.setProductCategory(productCategory);
            productType.setId(id);
            productType.setProductCategoryId(dto.getProductCategoryId());
            productType.setName(dto.getName());
            productType.setNameBn(dto.getNameBn());
            productType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            productType.setActive(dto.getActive());
            productType.setUpdatedAt(new Date());
            productType.setUpdatedBy(userData.getId());

            return productType;
        } else {
            ProductCategory productCategory = productCategoryRepository.findById(dto.getProductCategoryId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("ProductCategory", "Id", dto.getProductCategoryId()));
            productType.setProductCategory(productCategory);
            productType.setProductCategoryId(dto.getProductCategoryId());
            productType.setName(dto.getName());
            productType.setNameBn(dto.getNameBn());
            productType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            productType.setActive(dto.getActive());
            productType.setCreatedAt(new Date());
            productType.setCreatedAt(new Date());
            productType.setCreatedBy(userData.getId());
            return productType;
        }

    }

    private ProductTypeResponseDto transferToDTO(ProductType entity) {
        ProductTypeResponseDto dto = new ProductTypeResponseDto();
        dto.setProductCategoryId(entity.getProductCategoryId());
        dto.setProductCategoryName(entity.getProductCategory().getName());
        dto.setProductCategoryNameBn(entity.getProductCategory().getNameBn());
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

    private ProductTypeFindByIdResponseDto transferToFindByIdDTO(ProductType entity) {
        ProductTypeFindByIdResponseDto dto = new ProductTypeFindByIdResponseDto();
        dto.setProductCategoryId(entity.getProductCategoryId());
        dto.setProductCategoryName(entity.getProductCategory().getName());
        dto.setProductCategoryNameBn(entity.getProductCategory().getNameBn());
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
        List<ItemMasterGroupProductTypeResponseDto> itemMasterGroups = entity.getItemMasterGroups().stream().map(mstrGrp -> {
            ItemMasterGroupProductTypeResponseDto itemMasterGroup = new ItemMasterGroupProductTypeResponseDto();
            itemMasterGroup.setId(mstrGrp.getId());
            itemMasterGroup.setItmMstrGrpName(mstrGrp.getItmMstrGrpName());
            itemMasterGroup.setItmMstrGrpNameBn(mstrGrp.getItmMstrGrpNameBn());
            return itemMasterGroup;
        }).collect(Collectors.toList());
        dto.setItemMasterGroup(itemMasterGroups);
        return dto;
    }
}
