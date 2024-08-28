package com.khajana.setting.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemFindByIdResponseDto {
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

    private String countryName;

    private Boolean active;
    private String createdBy;
    private String createdAt;
    private String updatedAt;
    private String updatedBy;

    private ItemStoreMappingFindByItemIdResponseDto mapper;

}