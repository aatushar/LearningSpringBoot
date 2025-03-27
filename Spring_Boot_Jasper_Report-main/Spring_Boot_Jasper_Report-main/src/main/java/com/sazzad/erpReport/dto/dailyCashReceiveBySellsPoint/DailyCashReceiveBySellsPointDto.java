package com.sazzad.erpReport.dto.dailyCashReceiveBySellsPoint;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyCashReceiveBySellsPointDto {

    private long sl;
    private long no;
    private String date;
    private String salesPoint;
    private Double salesAmount;
    private Double depositAmount;
    private String depositedBy;
    private String remarks;
}
