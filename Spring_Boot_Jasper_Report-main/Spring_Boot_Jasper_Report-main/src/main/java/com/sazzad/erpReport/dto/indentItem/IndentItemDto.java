package com.sazzad.erpReport.dto.indentItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndentItemDto {
    private int sl;
    private String itemName;
    private String uom;
    private int qty;
    private int price;
    private int totalAmount;
    private String remarks;



}
