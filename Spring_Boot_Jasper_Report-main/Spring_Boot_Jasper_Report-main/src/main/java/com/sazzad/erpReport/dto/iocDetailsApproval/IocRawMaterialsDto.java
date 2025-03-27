package com.sazzad.erpReport.dto.iocDetailsApproval;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IocRawMaterialsDto {
    private Integer sl;
    private String itemName;
    private Integer qty;
    private String uom;
    private Double rate;
    private Double amount;
    private String remarks;
}
