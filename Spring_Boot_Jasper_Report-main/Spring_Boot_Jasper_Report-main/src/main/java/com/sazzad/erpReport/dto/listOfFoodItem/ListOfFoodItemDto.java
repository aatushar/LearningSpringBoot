package com.sazzad.erpReport.dto.listOfFoodItem;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOfFoodItemDto {

    private Integer sl;
    private Integer no;
    private String itemCode;
    private String itemName;
    private String remarks;

}
