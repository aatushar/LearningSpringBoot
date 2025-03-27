package com.sazzad.erpReport.dto.itemWiseDailySales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemWiseDailySalesDto {
    private int sl;
    private String itemName;
    private String uom;
    private int quantity;
    private double rate;
    private double amount;


}

