package com.sazzad.erpReport.dto.kitchenWiseSales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenWiseSalesDto {
    private int sl;
    private String date;
    private double orchidAmount;
    private double happyZoneAmount;
    private double miniKitchenAmount;
    private double fuchkaZoneAmount;
    private double lotusAmount;
    private double totalAmount;
}