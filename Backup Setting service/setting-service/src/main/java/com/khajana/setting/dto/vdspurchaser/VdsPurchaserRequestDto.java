package com.khajana.setting.dto.vdspurchaser;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VdsPurchaserRequestDto {
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long branchId;
    @NotNull(message = "required field can not be null")
    private Date transactionDate;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long vmId;
    @NotNull(message = "required field can not be null")
    private Date publishedDate;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long supplierId;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long tcMasterId;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalRecvAmtWotaxLocalCurr;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalVatAmount;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalDeductedVatAmount;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalVdsPaidAmount;
    @NotNull(message = "required field can not be null")
    List<@Valid VdsPurchaserChildRequestDto> vdspurchasechild;
}