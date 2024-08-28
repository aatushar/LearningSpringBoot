package com.khajana.setting.dto.ordermanagement.exportlcinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportLcInformationChildResponseDto {

    private Long id;

    private Long lcId;

    private Long itemInfoId;
    private String itemInfoName;

    private Double qty;

    private Double rate;

    private Double amount;
}
