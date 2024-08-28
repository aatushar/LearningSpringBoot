package com.khajana.setting.dto.vat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatRebateTypeResponseDto {

    private Long id;
    private String vatRebateName;
    private String vatRebateNameBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
