package com.khajana.setting.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemGroupResponseDto {
    private Long id;
    private Long prodTypeId;
    private String prodTypeName;
    private Long itmMstrGrpId;
    private String itmMstrGrpName;
    private String itmGrpPrefix;
    private Long uomSetId;
    private String uomSetName;
    private String itmGrpName;
    private String itmGrpNameBn;
    private String itemGrpDes;
    private String itemGrpDesBn;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}