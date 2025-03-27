package com.sazzad.erpReport.dto.waiterWiseDailySales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaiterWiseDailySalesDto {
    private int sl;
    private String waiterName;
    private String orderDateTime;
    private double amount;


}