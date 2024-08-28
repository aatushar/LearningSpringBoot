package com.khajana.setting.dto.ordermanagement.importlcinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportLcInformationChildResponseDto {

    private Long id;
    private Long itemInfoId;
    private String itemInfoName;
    private Double rate;
    private Double importLcAmt;
}
