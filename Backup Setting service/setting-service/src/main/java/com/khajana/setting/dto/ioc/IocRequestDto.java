package com.khajana.setting.dto.ioc;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IocRequestDto {
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long itemInfoId;
    @NotNull(message = "required field can not be null or empty")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String prcDeclName;
    @NotNull(message = "required field can not be null or empty")
    private Date effectiveFrom;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long iocUomId;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long branchId;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double calculationQty;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double iocQty;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalRmCost;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalInputSvcCost;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double totalValueAdditionCost;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double grandTotalCost;
    @NotNull(message = "required field can not be null or empty")
    private String remarks;
    @NotNull(message = "required field can not be null or empty")
    private Date dateOfSubmission;
    @NotNull(message = "required field can not be null or empty")
    private Date approvedByNbr;

    @NotNull
    private List<@Valid IocDetailRequestDto> iocDetails;
}