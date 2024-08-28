package com.khajana.setting.dto.ioc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IocResponseFindByIdDto {
    private Long id;
    private Long branchId;
    private String branchUnitName;
    private Long companyId;
    private String companyName;
    private String prcDeclName;
    private String prcDeclNumber;
    private String effectiveFrom;
    private Long itemInfoId;
    private String itemInfoName;
    private Long iocUomId;
    private String iocUomName;
    private Double calculationQty;
    private Double iocQty;
    private Double totalRmCost;
    private Double totalInputSvcCost;
    private Double totalValueAdditionCost;
    private Double grandTotalCost;
    private String remarks;
    private String dateOfSubmission;
    private String approvedByNbr;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

    private List<IocDetailResponseDto> iocDetails;
}