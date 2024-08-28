package com.khajana.setting.dto.uom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UomResponseDto {
    private Long id;
    private Long uomSetId;
    private String uomSetName;
    private String uomSetDesc;
    private String uomSetLocalDesc;

    private String uomShortCode;
    private String uomDesc;
    private String uomLocalDesc;

    private Double relativeFactor;
    private Boolean fractionAllow;
    private Boolean active;
    ;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}
