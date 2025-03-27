package com.sazzad.erpReport.dto.listOfFgHavingIoc;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOfFgHavingIocDto {

    private Integer sl;
    private Integer no;
    private String effectiveDate;
    private String itemName;
    private String costingUom;
    private Double amount;
    private String remarks;

}
