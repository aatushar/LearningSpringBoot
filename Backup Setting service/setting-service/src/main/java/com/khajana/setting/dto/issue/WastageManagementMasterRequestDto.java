package com.khajana.setting.dto.issue;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WastageManagementMasterRequestDto {
    @NotNull(message = "required field can not be empty")
    List<@Valid WastageManagementChildRequestDto> issueChild;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long receiveMasterId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long prodTypeId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long companyId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long branchId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long storeId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long currencyId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double excgRate;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long fiscalYearId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long vatMontId;
    @NotNull(message = "required field can not be empty")
    private Date issueDate;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long departmentId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalIsAmBeDiscount;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalIsAmLocalCurrBeDiscount;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalCdAmount;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalRdAmount;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalSdAmount;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalVatAmount;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalIssueAmtTransCurr;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalIssueAmtLocalCurr;
    @NotNull(message = "required field can not be empty")
    private String remarks;
    @NotNull(message = "required field can not be empty")
    private String remarksBn;
}
