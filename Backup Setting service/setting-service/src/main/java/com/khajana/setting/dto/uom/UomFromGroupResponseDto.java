package com.khajana.setting.dto.uom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UomFromGroupResponseDto {
    private Long id;
    private String uomShortCode;
    private String uomDesc;

}
