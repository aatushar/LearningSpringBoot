package com.khajana.setting.dto.ordermanagement.importlcinformation;

// import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportLcInformationMasterResponseDto {
    private Long id;
    private Long companyId;
    private Long supplierId;
    private Boolean isBblc;
    private Long exportLcId;
    private String exportLcNo;
    private String exportLcDate;
    private String importLcNo;
    private String importLcDate;
    private Long openingBankId;
    private Long importLcTypeId;
    private Long currencyId;
    private Double importLcAmt;
    private String expiryDate;
    private Double tolarance;
    private Long sourcingTypeId;
    private Long purposeId;
    private String nature;
    private Long advisingBank;
    private String applicationDate;
    private Boolean isApplied;
    private Long udRegisterId;
    private String udRegisterNo;
    private String udRegisterDate;

    private String createdAt;

    // private String updatedAt;

    private String createdBy;

    // private String updatedBy;


    private String remarks;
    private String companyName;
    private String supplierName;
    private String openingBankName;
    private String currencyName;
    private String sourcingTypeName;
    private String advisingBankName;


    private List<ImportLcInformationChildResponseDto> items;
}
