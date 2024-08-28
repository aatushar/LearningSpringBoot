package com.khajana.setting.dto.bank;

import com.khajana.setting.entity.bank.BankBranchInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankInfoResponseDto {

    private Long id;
    private String bankName;
    private String bankNameBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

    private List<BankBranchInfo> bankBranchInfos;

}