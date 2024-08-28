package com.khajana.setting.dto.policestation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoliceStationResponseDto {

    private Long id;
    private Long districtId;
    private String districtName;

    private String psName;
    private String psNameBn;

    private Double seqNo;

    private Boolean active;

    private String createdAt;

    private String updatedAt;

    private String createdBy;

    private String updatedBy;

}
