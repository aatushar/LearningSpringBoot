package com.sazzad.erpReport.dto.IssueReturnQty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueReturnQtyDto {

private Integer sl;
private String itemName;
private String uom;
private Integer indentQty;
private Integer issueQty;
private Integer returnQty;
private String remarks;

}
