package com.sazzad.erpReport.dto.itemStockSummary;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemStockSummaryDto {
    private long sl;
    private long no;
    private String itemName;
    private String uom;
    private Double qty;
    private Double rate;
    private Double amount;
    private String remarks;
}
