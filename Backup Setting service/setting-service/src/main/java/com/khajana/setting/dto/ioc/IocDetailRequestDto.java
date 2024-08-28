package com.khajana.setting.dto.ioc;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IocDetailRequestDto {
    @Nullable
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;
    @Nullable
    @JsonDeserialize(using = LongDeserializer.class)
    private Long iocPriceDeclarationId;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long prodTypeId;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long itemInfoId;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long consumptionUomId;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double consumptionCalculationQty;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double consumptionIocQty;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double purchaseRate;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double wastageOfCalculationQty;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double wastageOfIocQty;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double iocQty;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double wastagePercent;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double calculationAmt;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double iocAmt;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean isRebatable;
}