package com.khajana.setting.dto.vat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatMonthRequestDto {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long fyId;
    @NotNull(message = "Name can not be empty")
    @Size(max = 100, message = "Name should not be greater than 100 characters")
    private String vmInfo;
    @NotNull(message = "Name BN can not be empty")
    @Size(max = 100, message = "Name BN should not be greater than 100 characters")
    private String vmInfoBn;
    @NotNull(message = "Sequence Number can not be empty")
    @DecimalMin(value = "1.00", message = "Sequence Number must be at least 1.00")
    @DecimalMax(value = "9999999.99", message = "Sequence Number must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "FromDate can not be empty")
    private Date fromDate;
    @NotNull(message = "ToDate can not be empty")
    private Date toDate;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean vmStatus;
    @NotNull(message = "active can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;

}