package com.khajana.setting.dto.vat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatStructureRequestDto {
    //@Nullable
    //   @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @NotNull(message = "vatRateRefId can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long vatRateRefId;
    @NotNull(message = "tranSubTypeId can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long tranSubTypeId;
    @NotNull(message = "vatRateTypeId can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long vatRateTypeId;
    @NotNull(message = "prodTypeId can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long prodTypeId;
    @NotNull(message = "effectiveDate can not be null")
    private Date effectiveDate;
    @NotNull(message = "uomId can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long uomId;
    @NotNull(message = "cd Number can not be empty")
    @DecimalMin(value = "0.00", message = "sd must be at least 0.00")
    @DecimalMax(value = "9999999.99", message = "sd must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double cd;
    @NotNull(message = "sd Number can not be empty")
    @DecimalMin(value = "0.00", message = "sd must be at least 0.00")
    @DecimalMax(value = "9999999.99", message = "sd must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double sd;
    @NotNull(message = "vat Number can not be empty")
    @DecimalMin(value = "0.00", message = "sd must be at least 0.00")
    @DecimalMax(value = "9999999.99", message = "sd must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double vat;
    @NotNull(message = "at Number can not be empty")
    @DecimalMin(value = "0.00", message = "sd must be at least 0.00")
    @DecimalMax(value = "9999999.99", message = "sd must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double at;
    @NotNull(message = "ait Number can not be empty")
    @DecimalMin(value = "0.00", message = "sd must be at least 0.00")
    @DecimalMax(value = "9999999.99", message = "sd must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double ait;
    @NotNull(message = "rd Number can not be empty")
    @DecimalMin(value = "0.00", message = "sd must be at least 0.00")
    @DecimalMax(value = "9999999.99", message = "sd must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double rd;
    @NotNull(message = "atv Number can not be empty")
    @DecimalMin(value = "0.00", message = "sd must be at least 0.00")
    @DecimalMax(value = "9999999.99", message = "sd must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double atv;
    @NotNull(message = "exd Number can not be empty")
    @DecimalMin(value = "0.00", message = "sd must be at least 0.00")
    @DecimalMax(value = "9999999.99", message = "sd must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double exd;
    @NotNull(message = "tti Number can not be empty")
    @DecimalMin(value = "0.00", message = "sd must be at least 0.00")
    @DecimalMax(value = "9999999.99", message = "sd must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double tti;
    @NotNull(message = "isFixedRate can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean isFixedRate;
    @NotNull(message = "fixedRateUomId can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long fixedRateUomId;
    @NotNull(message = "fixedRate Number can not be empty")
    @DecimalMin(value = "0.00", message = "sd must be at least 0.00")
    @DecimalMax(value = "9999999.99", message = "sd must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double fixedRate;
    @NotNull(message = "active can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;

}
