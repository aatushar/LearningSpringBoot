package com.sazzad.erpReport.dto.purchaseOrderSummary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderSummaryDto {

    private Integer sl;
    private Integer no;
    private String date;
    private String supplier;
    private String productMasterGroup;
    private String deliveryDate;
    private Double poAmount;
    private String closeStatus;
    private String remarks;
}
