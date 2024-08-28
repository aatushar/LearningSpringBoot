package com.khajana.setting.dto.ioc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IocDetailInputAddedServiceResponseDto {
    private Long id;
    private Long iocPriceDeclarationId;
    private String iocName;
    private Long prodTypeId;
    private String prodTypeName;
    private Long itemInfoId;
    private String itemInfoName;
    private String itemInfoNameBn;
    private Long consumptionUomId;
    private String consumptionUomName;
    private Double consumptionCalculationQty;
    private Double consumptionIocQty;
    private Double purchaseRate;
    private Double wastageOfCalculationQty;
    private Double wastageOfIocQty;
    private Double iocQty;
    private Double wastagePercent;
    private Double calculationAmt;
    private Double iocAmt;
    private Boolean isRebatable;
}