package com.sazzad.erpReport.dto.allWaiterDailySales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllWaiterDailySalesDto {
    private int sl;
    private String date;
    private double robiulAmount;
    private double akashAmount;
    private double raselAmount;
    private double iqbalAmount;
    private double humayonAmount;
    private double totalAmount;
}
