package com.khajana.setting.dto.vat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatRegistrationTypeResponseDto {
    private Long id;
    private String vatRegistrationName;
    private String vatRegistrationNameBn;
    private Double seqNo;
    private Boolean active;
    private Boolean reqBin;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}