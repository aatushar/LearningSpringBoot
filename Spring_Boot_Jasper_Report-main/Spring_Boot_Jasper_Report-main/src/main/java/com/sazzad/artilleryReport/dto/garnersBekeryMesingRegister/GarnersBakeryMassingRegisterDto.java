package com.sazzad.artilleryReport.dto.garnersBekeryMesingRegister;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GarnersBakeryMassingRegisterDto {

    private String itemName;
    private Double qty;
    private Double rate;
    private Double totalAmount;
    private String remarks;
}