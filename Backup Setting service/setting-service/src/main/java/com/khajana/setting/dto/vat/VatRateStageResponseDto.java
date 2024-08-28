package com.khajana.setting.dto.vat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatRateStageResponseDto {

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
}
