package com.sazzad.erpReport.dto.purchaseRequsition;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequisitionDto {

    private Integer sl;
    private String requisitionNo;
    private String requisitionDate;
    private String itemName;
    private String uom;
    private Double rate;
    private Double oty;
    private Double amount;
    private String remarks;

}
