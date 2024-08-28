package com.khajana.setting.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemGroupMasterGroupResponseDto {
    private Long id;
    private String itmGrpName;
    private String itmGrpNameBn;

}