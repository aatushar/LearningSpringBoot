package com.khajana.setting.dto.vdspurchaser;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VdsPurchaserChildRequestDto {
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long receiveMasterId;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double recvAmtWotaxLocalCurr;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double vatAmount;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double deductedVatAmount;
}