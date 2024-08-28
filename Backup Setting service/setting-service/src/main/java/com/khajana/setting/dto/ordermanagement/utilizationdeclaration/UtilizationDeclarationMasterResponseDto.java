package com.khajana.setting.dto.ordermanagement.utilizationdeclaration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilizationDeclarationMasterResponseDto {
    private Long id;
    private Long customerId;
    private String customerName;
    private Long lcId;
    private String lcNo;
    private String lcDate;
    private String udRegisterNo;
    private String udRegisterDate;
    // private String buyerCode;
    private String remarks;

    private String createdAt;

    // private String updatedAt;

    private String createdBy;

    // private String updatedBy;


    private List<UtilizationDeclarationChildResponseDto> items;
}
