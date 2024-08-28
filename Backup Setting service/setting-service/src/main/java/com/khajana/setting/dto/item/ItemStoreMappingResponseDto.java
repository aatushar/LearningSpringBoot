package com.khajana.setting.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemStoreMappingResponseDto {
    private Long id;
    private Long itemInfoId;
    private String displayItmName;
    private String displayItmNameBn;
    private Long prodTypeId;
    private String prodTypeName;
    private String prodTypeNameBn;
    private Long itmSubGrpId;
    private String itmSubGrpName;
    private String itmSubGrpNameBn;
    private Long branchId;
    private String branchName;
    private Long storeId;
    private String storeName;
    private String createdBy;
    private String createdAt;
    private String updatedAt;
    private String updatedBy;
}