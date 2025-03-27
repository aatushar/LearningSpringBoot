package com.sazzad.artilleryReport.dto.dailySellsListByOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySellsListByOrderDTO {

    private String companyName;
    private String storeName;
    private String orderNo;
    private String orderDate;
    private Double salesAmount;
    private Double discount;
    private Double payableAmount;
    private Double paidAmount;
    private Double dueAmount;
    private String remarks;
}

            