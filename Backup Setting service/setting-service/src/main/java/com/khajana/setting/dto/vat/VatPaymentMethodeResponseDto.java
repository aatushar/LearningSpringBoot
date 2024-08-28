package com.khajana.setting.dto.vat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatPaymentMethodeResponseDto {

    private Long id;
    private Long tranSubTypeId;
    private String trnsSubTypeName;
    private String trnsSubTypeNameBn;
    private String vatPaymentMethodName;
    private String vatPaymentMethodNameBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}
