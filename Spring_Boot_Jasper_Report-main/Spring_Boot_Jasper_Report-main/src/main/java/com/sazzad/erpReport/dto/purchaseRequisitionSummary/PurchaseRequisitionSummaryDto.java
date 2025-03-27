package com.sazzad.erpReport.dto.purchaseRequisitionSummary;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequisitionSummaryDto {
    private Integer sl;
    private String no;
    private String date;
    private String by;
    private String productType;
    private String closeStatus;
    private String approvedBy;
    private String remarks;
}
