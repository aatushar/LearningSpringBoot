package com.khajana.setting.dto.issue;

// import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
// import com.khajana.setting.deserializer.BooleanDeserializer;
// import com.khajana.setting.deserializer.DoubleDeserializer;
// import jakarta.validation.constraints.Max;
// import jakarta.validation.constraints.Min;
// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueChildRequestDto {
    private Long id;
    private Long issueMasterId;
    private Long cardId;
    private Long itemInfoId;
    private Long uomId;
    private String uomShortCode;
    private Float relativeFactor;
    private Long vatPaymentMethodId;
    private Long itemCatForRetailId;
    private Double issueQty;
    private Double discountPercent;
    private Double itemRate;
    private Double issueRate;
    private Double mrpValue;
    private Double discountAmount;
    private Double itemValueTranCurr;
    private Double itemValueLocalCurr;
    private Long vatRateTypeId;
    private Long isFixedRate;
    private Double cdPercent;
    private Double cdAmount;
    private Double rdPercent;
    private Double rdAmount;
    private Integer indentChildId;
    private Double sdPercent;
    private Double sdAmount;
    private Double vatPercent;
    private Long fixedRateUomId;
    private Double fixedRate;
    private Double vatAmount;
    private Double aitPercent;
    private Double aitAmount;
    private Double atPercent;
    private Double atAmount;
    private Double totalAmountTranCurr;
    private Double totalAmountLocalCurr;
    private Long trnUnitId;
    private Long inventoryMethodId;
    private Double itmTradeRate;
    private Double itmWholesaleRate;
    private Double itmExportRate;
}
