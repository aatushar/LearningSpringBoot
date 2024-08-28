package com.khajana.setting.dto.uom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UomSetResponseDto {
    private Long id;
    private String uomSet;
    private String uomSetDesc;
    private String localUomSetDesc;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}