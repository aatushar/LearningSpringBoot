package com.khajana.setting.dto.ordermanagement.exportlcinformation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
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
public class ExportLcInformationMasterRequestDto {
    @NotNull
    List<@Valid ExportLcInformationChildRequestDto> items;
    @NotNull
    @Size(max = 30, min = 1)
    private String lcNo;
    @NotNull
    private Date lcDate;
    @Nullable
    @Size(max = 30, min = 1)
    private String groupCode;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long customerId;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long companyId;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long openBankId;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long lienBankId;
    @NotNull
    private Date lienDate;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long currencyId;
    @NotNull
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double lcAmt;
    @NotNull
    private Date shipDate;
    @NotNull
    private Date expDate;
    @NotNull
    private String remarks;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long lcForId;
    @NotNull
    @DecimalMax(value = "100", inclusive = true)
    private Double maximportLimit;
    @NotNull
    private Boolean isClosed;
}
