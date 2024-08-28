package com.khajana.setting.dto.issue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueMasterVdsResponseDto {
    private Long issueMasterId; // from id
    private String challanNumber;
    private String challanNumberBn;
    private String challanDate;
    private Double vatAmount;
    private Double totalAmountLocalCurr;
}
