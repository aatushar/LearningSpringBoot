package com.khajana.setting.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictRequestDto {

    private Long countryId;
    private String longitude;
    private String lat;
    private String name;
    private String nameBn;
    private Double seqNo;
    private Boolean active;
}