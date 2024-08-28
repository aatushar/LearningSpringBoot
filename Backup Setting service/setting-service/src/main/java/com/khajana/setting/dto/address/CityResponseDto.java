package com.khajana.setting.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityResponseDto {

    private Long id;
    private Long countryId;
    private String countryName;
    private String countryNameBn;
    private String name;
    private String nameBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}