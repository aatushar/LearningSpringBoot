package com.khajana.setting.dto.fy;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
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
public class FiscalYearInfoRequestDto {
    @NotNull(message = "fiscalYear can not be empty")
    @Size(max = 100, message = "fiscalYear should not be greater than 100 characters")
    private String fiscalYear;

    @NotNull(message = "fiscalYearBn can not be empty")
    @Size(max = 100, message = "fiscalYearBn should not be greater than 100 characters")
    private String fiscalYearBn;

    @NotNull(message = "Sequence Number can not be empty")
    @DecimalMin(value = "1.00", message = "Sequence Number must be at least 1.00")
    @DecimalMax(value = "9999999.99", message = "Sequence Number must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;

    @NotNull(message = "fromDate can not be null")
    private Date fromDate;

    @NotNull(message = "toDate can not be null")
    private Date toDate;


    @NotNull(message = "Active can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;

}
