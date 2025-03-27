package com.sazzad.artilleryReport.dto.dailySalesSummaryByItem;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySalesSummaryByItemDto {

    private String companyName;
    private String storeName;
    private String itemName;
    private String uom;
    private Double sellQty;
    private Double sellAmount;
    private Double discount;
    private Double payableAmount;


}
