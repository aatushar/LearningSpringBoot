package com.khajana.setting.dto.economyactivity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EconomicActivityAreaResponseDto {
    private Long id;
    private Long economicActivityId;
    private String economicActivityName;
    private String economicActivityAreaName;
    private String economicActivityAreaNameBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
