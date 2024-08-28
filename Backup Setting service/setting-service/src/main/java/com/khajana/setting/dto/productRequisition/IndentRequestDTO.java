package com.khajana.setting.dto.productRequisition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndentRequestDTO {

    @NotNull(message = "Order Master ID is required")
    private Long orderMasterId;

    @NotNull(message = "Indent Number is required")
    private String indentNumber;

    @NotNull(message = "Indent Date is required")
    private String indentDate;

    @NotNull(message = "Indent Date is required")
    private Boolean isProductReq;

    private Long companyId;
    private Long branchId;
    private Long prodTypeId;
    private Long masterGroupId;

    @NotNull(message = "Demand Store ID is required")
    private Long demandStoreId;

    @NotNull(message = "To Store ID is required")
    private Long toStoreId;

    private Long deptId;
    private String purpose;
    private String remarks;
    private String remarksBn;

    @NotNull(message = "Submitted By is required")
    private Integer submittedBy;

    private String submittedAt;
    private Integer recommendedBy;
    private String recommendedAt;
    private Integer approvedBy;
    private String approvedAt;

    @NotNull(message = "Approved Status is required")
    private Boolean approvedStatus;

    private Boolean isForIssue;

    @NotNull(message = "Issued status is required")
    private Boolean isIssued;

    private Boolean isForPurchase;

    @NotNull(message = "Indent Closed status is required")
    private Boolean isIndentClosed;

    @NotNull(message = "Pro Req Close status is required")
    private Boolean isProReqClose;

    @Valid
    @NotNull(message = "Child list is required")
    @Size(min = 1, message = "Child list must contain at least one item")
    private List<IndentChildDto> child;
}
