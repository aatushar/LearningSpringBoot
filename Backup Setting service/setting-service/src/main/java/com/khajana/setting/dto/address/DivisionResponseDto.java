package com.khajana.setting.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DivisionResponseDto {

    private Long id;
    private Long countryId;
    private String countryName;
    private String countryNameBn;
    private String longitude;
    private String lat;
    private String name;
    private String nameBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
    private List<DistrictDivisionResponseDto> districts;

}