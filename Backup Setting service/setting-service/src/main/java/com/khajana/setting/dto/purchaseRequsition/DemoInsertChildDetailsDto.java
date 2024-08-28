package com.khajana.setting.dto.purchaseRequsition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoInsertChildDetailsDto {

    private Long itemInfoId;
    private Long uomId;
    private Double relativeFactor;
    private Double rate;
    private Double reqQuantity;
    private Date requiredDate;
    private String remarks;
    private String remarksBn;
}
