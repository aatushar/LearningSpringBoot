package com.khajana.setting.dto.vatratetype;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatRateTypeResponseDto {

    private Long id;
    private String vatRateTypeName;
    private String vatRateTypeNameBn;
    private Double seqNo;
    private Boolean active;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

}