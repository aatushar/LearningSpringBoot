package com.khajana.setting.dto.currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchangeRateResponseDto {

    private Long id;
    private Long currencyInfoId;
    private String currencyShortCode;
    private String currencyDesc;
    private String exchangeRate;
    private Date exchangeRateDate;
    private String source;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}