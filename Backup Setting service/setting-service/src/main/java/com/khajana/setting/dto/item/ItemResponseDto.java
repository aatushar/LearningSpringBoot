package com.khajana.setting.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDto {
    private Long id;
    private Long companyId;
    private String companyName;
    private Long vatPaymentMethodId;
    private String vatPaymentMethodName;
    private String itmCode;
    private String displayItmCode;
    private String displayItmCodeBn;
    private String displayItmName;
    private String displayItmNameBn;
    private String mushakItmName;
    private String mushakItmNameBn;
    private Long countryOrigin;
    private Long hsCodeId;
    private String hsCodeName;
    private Long currencyInfoId;
    private String currencyInfoName;
    private Long uomId;
    private String uomName;
    private Long trnsUnitId;
    private String trnsUnitName;
    private Long stockUnitId;
    private String stockUnitName;
    private Long salesUnitId;
    private String salesUnitName;
    private BigDecimal currentRate;
    private Boolean isRebateable;
    private Boolean isTradeItem;
    private Boolean active;
    private String createdBy;
    private String createdAt;
    private String updatedAt;
    private String updatedBy;

    private Long itmGrpId;
    private String itmGrpName;
    private Long itmGrpSubId;
    private String itmGrpSubName;
    private Long itmMstrGrpId;
    private String itmMstrGrpName;
    private Long prodTypeId;
    private String prodTypeName;
    private Long categoryId;
    private String categoryName;
    private String countryName;
}