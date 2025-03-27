package com.sazzad.erpReport.dto.productRequisitionSummary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequisitionSummaryDto {
    private int sl;
    private String requisitionNumber;
    private String date;
    private String requestedBy;
    private String productMasterGroup;
    private String issueStatus;
    private String closeStatus;
    private String approvedBy;
    private String remarks;
}