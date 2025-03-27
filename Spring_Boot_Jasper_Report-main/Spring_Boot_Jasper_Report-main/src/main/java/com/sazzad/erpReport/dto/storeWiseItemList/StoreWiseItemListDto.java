package com.sazzad.erpReport.dto.storeWiseItemList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreWiseItemListDto {
    private Integer sl;
    private String barcode;
    private Integer itemCode;
    private String subGroup;
    private String itemName;
    private String itemNameBn;
    private Double itemRate;
    private String remarks;
}
