package com.khajana.setting.dto.vat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatStructureResponseDto {

    private Long id;
    private Long hsCodeId;
    private String hsCodeName;
    private Long vatRateRefId;
    private String vatRateRefName;
    private Long tranSubTypeId;
    private String tranSubTypeName;
    private Long vatRateTypeId;
    private String vatRateTypeName;
    private Long prodTypeId;
    private String prodTypeName;
    private Long fiscalYearId;
    private String fiscalYear;
    private String effectiveDate;
    private Long uomId;
    private String uomName;
    private Double cd;
    private Double sd;
    private Double vat;
    private Double at;
    private Double ait;
    private Double rd;
    private Double atv;
    private Double exd;
    private Double tti;
    private Boolean isFixedRate;
    private Long fixedRateUomId;
    private Double fixedRate;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
