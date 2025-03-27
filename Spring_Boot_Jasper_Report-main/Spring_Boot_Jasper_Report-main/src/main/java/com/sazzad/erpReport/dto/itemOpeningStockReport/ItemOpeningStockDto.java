package com.sazzad.erpReport.dto.itemOpeningStockReport;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOpeningStockDto {

    public Integer sl;
    private Integer no;
    private String stockDate;
    private String itemName;
    private String uom;
    private Double qty;
    private Double rate;
    private Double amount;
    private String remarks;

}
