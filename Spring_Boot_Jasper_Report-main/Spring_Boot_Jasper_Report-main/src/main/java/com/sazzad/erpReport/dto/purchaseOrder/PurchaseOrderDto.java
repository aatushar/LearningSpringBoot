package com.sazzad.erpReport.dto.purchaseOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDto {

    private Integer sl;
    private String itemName;
    private Double qty;
    private String uom;
    private Double rate;
    private Double amount;
    private String remarks;

}
