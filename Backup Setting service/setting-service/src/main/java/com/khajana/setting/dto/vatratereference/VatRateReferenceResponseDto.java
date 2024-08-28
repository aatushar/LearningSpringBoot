package com.khajana.setting.dto.vatratereference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatRateReferenceResponseDto {

    private Long id;
    private String vatRateRefName;
    private String vatRateRefNameBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}