package com.khajana.setting.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictResponseDto {

    private Long id;
    private Long divisionId;
    private String divisionName;
    private String divisionNameBn;
    private String longitude;
    private String lat;
    private String name;
    private String nameBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;

    private List<UpazilaDistrictResponseDto> upazilas;
    private List<PoliceStationDistrictResponseDto> policeStations;
}