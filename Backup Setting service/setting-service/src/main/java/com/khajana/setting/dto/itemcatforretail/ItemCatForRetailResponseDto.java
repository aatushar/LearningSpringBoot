package com.khajana.setting.dto.itemcatforretail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCatForRetailResponseDto {
    private Long id;
    private String itemCatRetailName;
    private String itemCatRetailNameBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
