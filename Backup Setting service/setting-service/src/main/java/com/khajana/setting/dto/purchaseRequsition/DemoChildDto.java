package com.khajana.setting.dto.purchaseRequsition;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class DemoChildDto {
    private Long purchaseReqChildId;
    private Long purchaseReqMasterId;
    private Long itemInfoId;
    private String displayItemName;
    private String displayItemNameBn;
    private Long uomId;
    private String uomShortCode;
    private Double relativeFactor;
    private Double rate;
    private Double reqQuantity;
    private Date requiredDate;
    private String reqRemarks;
    private String reqRemarksBn;
}
