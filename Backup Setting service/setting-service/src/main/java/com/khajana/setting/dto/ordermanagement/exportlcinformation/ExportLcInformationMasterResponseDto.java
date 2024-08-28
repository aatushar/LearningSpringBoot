package com.khajana.setting.dto.ordermanagement.exportlcinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportLcInformationMasterResponseDto {
    // private String updatedBy;
    List<ExportLcInformationChildResponseDto> items;
    private Long id;
    private String lcNo;
    private String lcDate;
    private String groupCode;
    private Long customerId;
    private String customerName;
    private Long companyId;
    private String companyName;
    private Long openBankId;
    private String openBankName;
    private Long lienBankId;
    private String lienBankName;
    private String lienDate;
    private Long currencyId;
    private String currencyName;
    private Double lcAmt;
    private String shipDate;
    private String expDate;
    private String remarks;
    private Long lcForId;
    private String lcForName;
    private Double maximportLimit;
    private Boolean isClosed;
    private String createdAt;
    // private String updatedAt;
    private String createdBy;
}
