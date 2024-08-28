package com.khajana.setting.dto.receive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveChildResponseDto {
    private Long id;
    private Long receiveMasterId;
    private Long itemInfoId;
    private Long uomId;
    private String uomShortCode;
    private Double relativeFactor;
    private Long vatPaymentMethodId;
    private Long itemCatForRetailId;
    private Double gateRecvQty;
    private Double recvQuantity;
    private Double itmReceiveRate;
    private Double itemAssessableValueTransCurr;
    private Double itemAssessableValueLocalCurr;
    private Double itemValueWotaxTransCurr;
    private Double itemValueWotaxLocalCurr;
    private Long vatRateTypeId;
    private Boolean isFixedRate;
    private Double cdPercent;
    private Double cdAmount;
    private Double rdPercent;
    private Double rdAmount;
    private Double sdPercent;
    private Double sdAmount;
    private Double vatPercent;
    private Long fixedRateUomId;
    private Double fixedRate;
    private Double vatAmount;
    private Double atPercent;
    private Double atAmount;
    private Double aitPercent;
    private Double aitAmount;
    private Double totalAmtTransCurr;
    private Double totalAmtLocalCurr;
    private Date gateEntryAt;
    private Long gateEntryBy;
    private String openingStockRemarks;
    private Date createdAt;
    private Date updatedAt;
    private Long createdBy;
    private Long updatedBy;
}
