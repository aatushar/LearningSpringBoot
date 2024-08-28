package com.khajana.setting.dto.ordermanagement.utilizationdeclaration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilizationDeclarationChildResponseDto {

    private Long id;
    private Long udRegisterId;
    private Long bblcId;
    private String remarks;
}
