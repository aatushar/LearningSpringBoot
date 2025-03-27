package com.sazzad.erpReport.dto.orderWiseDailySales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderWiseDailySalesDto {
    private int sl;
    private String customerName;
    private String mobileNo;
    private String orderNo;
    private String salesType;
    private String waiterName;
    private String orderDateTime;
    private double amount;



}
