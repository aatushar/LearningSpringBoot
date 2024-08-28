package com.khajana.setting.dto.fy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiscalYearInfoResponseDto {

    private Long id;
    private String fiscalYear;
    private String fiscalYearBn;
    private Double seqNo;
    private String fromDate;
    private String toDate;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}
