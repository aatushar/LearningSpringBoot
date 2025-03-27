package com.sazzad.erpReport.dto.issueReturn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueReturnDto {
    private Integer sl;
    private String itemName;
    private String uom;
    private Double rate;
    private Integer issueQty;
    private Integer returnQty;
    private Double issueAmount;
    private Double returnAmount;
    private String remarks;
}
