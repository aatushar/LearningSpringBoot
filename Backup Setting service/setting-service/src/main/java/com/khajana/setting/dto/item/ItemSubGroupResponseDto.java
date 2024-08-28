package com.khajana.setting.dto.item;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemSubGroupResponseDto {
    private Long id;
    private Long itmGrpId;
    private String itmGrpName;
    private Long hsCodeId;
    private String hsCodeName;
    private String itmSubGrpName;
    private String itmSubGrpNameBn;
    private String itmSubGrpPrefix;
    private Long invMethodId;
    private String invMethodName;
    private Boolean active;
    private Double seqNo;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}