package com.khajana.setting.dto.purchaseRequsition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoInsertChildDto {
    private Long indentChildId;
    private Long itemInfoId;
    private Double requisitionQuantity;
}
