package com.khajana.setting.dto.product;

import com.khajana.setting.dto.item.ItemMasterGroupProductTypeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTypeFindByIdResponseDto {

    private Long id;
    private Long productCategoryId;
    private String productCategoryName;
    private String productCategoryNameBn;
    private String name;
    private String nameBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

    private List<ItemMasterGroupProductTypeResponseDto> itemMasterGroup;

}