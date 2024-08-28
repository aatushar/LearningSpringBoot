package com.khajana.setting.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemStoreMappingFindByItemIdResponseDto {
    private Long id;
    private Long itemInfoId;
    private Long itmGrpId;
    private String itmGrpName;
    private Long itmGrpSubId;
    private String itmGrpSubName;
    private Long itmMstrGrpId;
    private String itmMstrGrpName;
    private Long prodTypeId;
    private String prodTypeName;
    private Long categoryId;
    private String categoryName;


    // private String createdBy;
    // private String createdAt;
    // private String updatedAt;
    // private String updatedBy;
}