package com.khajana.setting.dto.economyactivity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EconomyActivityResponseDto {
    private Long id;
    private String economicActivityName;
    private String economicActivityNameBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}