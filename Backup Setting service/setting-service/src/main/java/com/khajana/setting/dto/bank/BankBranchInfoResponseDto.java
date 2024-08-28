package com.khajana.setting.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankBranchInfoResponseDto {

    private Long id;
    private Long bankId;
    private String bankName;
    private String bankNameBn;
    private String bankBranchName;
    private String bankBranchNameBn;
    private String bankBranchAddress;
    private String bankBranchAddressBn;
    private String bankBranchRoutingNo;
    private String bankBranchPhoneNumber;
    private String bankBranchEmailAddress;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}