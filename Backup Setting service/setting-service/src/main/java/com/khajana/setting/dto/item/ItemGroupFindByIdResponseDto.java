package com.khajana.setting.dto.item;

import com.khajana.setting.dto.uom.UomFromGroupResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemGroupFindByIdResponseDto {
    private Long id;
    private Long itmMstrGrpId;
    private String itmMstrGrpName;
    private String itmGrpPrefix;
    private Long uomSetId;
    private String uomSetName;
    private String itmGrpName;
    private String itmGrpNameBn;
    private String itemGrpDes;
    private String itemGrpDesBn;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

    private List<ItemSubGroupGroupResponseDto> itemSubGroups;

    private List<UomFromGroupResponseDto> uoms;

}