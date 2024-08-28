package com.khajana.setting.dto.businessinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessInformationResponseDto {

    private Long id;
    private String businessInfoName;

    private String businessInfoNameBn;

    private Double seqNo;

    private Boolean active;

    private String createdAt;

    private String updatedAt;

    private String createdBy;

    private String updatedBy;

}
