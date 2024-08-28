package com.khajana.setting.dto.vdspurchaser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VdsPurchaserChildResponseDto {

    private Long id;
    private Long vdsPurchaserMasterId;
    private Long receiveMasterId;
    private Double recvAmtWotaxLocalCurr;
    private Double vatAmount;
    private Double deductedVatAmount;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}