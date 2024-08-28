package com.khajana.setting.dto.receive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveChildForWastageResponseDto {
    private Long itemInfoId;
    private String displayItmName;
    private String displayItmNameBn;
    private Long uomId;
    private String uomShortCode;
    private Double cdPercent;
    private Double cdAmount;
    private Double rdPercent;
    private Double rdAmount;
    private Double sdPercent;
    private Double sdAmount;
    private Double vatPercent;
    private Double vatAmount;
    private Double aitPercent;
    private Double aitAmount;
    private Double atPercent;
    private Double atAmount;
    private Double issueQty;
    private Double itemRate;
    private Double issueRate;
    private Long vatRateTypeId;
    private String vatRateTypeName;
}
