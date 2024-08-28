package com.khajana.setting.dto.vat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatMonthResponseDto {

    private Long id;
    private Long fyId;
    private String fiscalYear;
    private String fiscalYearBn;
    private String vmInfo;
    private String vmInfoBn;
    private Double seqNo;
    private String fromDate;
    private String toDate;
    private Boolean vmStatus;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}