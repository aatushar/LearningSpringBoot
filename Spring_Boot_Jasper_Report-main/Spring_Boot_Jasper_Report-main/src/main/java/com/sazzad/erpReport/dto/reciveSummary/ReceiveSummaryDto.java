package com.sazzad.erpReport.dto.reciveSummary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveSummaryDto {
    private Integer sl;
    private Integer no;
    private String receiveDate;
    private String supplier;
    private String productMasterGroup;
    private Double poAmount;
    private Double receiveAmount;
    private String remarks;
}
