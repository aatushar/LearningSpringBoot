package com.khajana.setting.dto.item;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemMasterGroupResponseDto {
    private Long id;
    private Long prodTypeId;
    private String prodTypeName;
    private String itmMstrGrpName;
    private String itmMstrGrpNameBn;
    private String itmMstrGrpPrefix;
    private String itemMstrGrpDes;
    private String itemMstrGrpDesBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}