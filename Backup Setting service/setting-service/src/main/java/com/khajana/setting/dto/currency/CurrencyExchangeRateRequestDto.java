package com.khajana.setting.dto.currency;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchangeRateRequestDto {
    @JsonDeserialize(using = LongDeserializer.class)
    private Long currencyInfoId;
    @NotNull(message = "exchangeRate can not be empty")
    @Size(max = 50, message = "exchangeRate should not be greater than 50 characters")
    private String exchangeRate;
    @NotNull(message = "source can not be empty")
    private Date exchangeRateDate;

    @NotNull(message = "source can not be empty")
    @Size(max = 50, message = "source should not be greater than 50characters")
    private String source;
    @NotNull(message = "Active can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}