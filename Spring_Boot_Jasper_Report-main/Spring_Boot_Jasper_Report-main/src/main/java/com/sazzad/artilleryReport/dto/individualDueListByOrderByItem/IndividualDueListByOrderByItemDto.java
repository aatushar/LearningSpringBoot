package com.sazzad.artilleryReport.dto.individualDueListByOrderByItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualDueListByOrderByItemDto {
    private String companyName;
    private String storeName;
    private String orderNo;
    private String orderDate;
    private String itemName;
    private String qty;
    private Double salesAmount;
    private Double discount;
    private Double payableAmount;
    private Double paidAmount;
    private Double dueAmount;
    private String remarks;
}
