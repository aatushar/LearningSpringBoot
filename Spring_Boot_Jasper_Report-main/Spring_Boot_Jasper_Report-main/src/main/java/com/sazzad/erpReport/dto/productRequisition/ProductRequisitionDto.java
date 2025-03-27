package com.sazzad.erpReport.dto.productRequisition;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequisitionDto {
    private Integer sl;
    private String requisitionDate;
    private String storeName;
    private String itemName;
    private String uom;
    private Double qty;
    private String remarks;
}
