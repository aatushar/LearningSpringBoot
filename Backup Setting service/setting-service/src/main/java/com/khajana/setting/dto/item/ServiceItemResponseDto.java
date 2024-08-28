package com.khajana.setting.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceItemResponseDto {
    private Long itemInfoId;
    private String displayItmName;
    private String displayItmNameBn;
    private Long uomId;
    private String uomName;
    private Double currentRate;
    private Boolean isRebateable;
    private Boolean isTradeItem;
}