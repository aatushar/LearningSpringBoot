package com.khajana.setting.dto.productRequisition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndentChildBossDto {
    private Long id;
    private Long itemInfoId;
    private String displayItemName;
    private String displayItemNameBn;
    private Long uomId;
    private String uomShortCode;
    private Double relativeFactor;
    private Double indentQuantity;
    private Double consumOrderQty;
    private String indentRemarks;
    private Date requiredDate;
    private String indentRemarksBn;

    // Getters and setters
}

