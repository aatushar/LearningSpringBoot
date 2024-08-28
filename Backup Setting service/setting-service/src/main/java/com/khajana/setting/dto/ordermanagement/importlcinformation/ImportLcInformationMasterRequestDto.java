package com.khajana.setting.dto.ordermanagement.importlcinformation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
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
public class ImportLcInformationMasterRequestDto {

    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long companyId;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long supplierId;
    @NotNull
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean isBblc;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long exportLcId;
    @NotNull
    @Size(max = 30)
    private String importLcNo;
    @NotNull
    @Size(max = 150)
    private String remarks;
    @NotNull
    private Date importLcDate;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long openingBankId;
    @NotNull
    // @JsonDeserialize(using = LongDeserializer.class)
    // private Long importLcTypeId;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long currencyId;
    @NotNull
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double importLcAmt;
    @NotNull
    private Date expiryDate;
    @NotNull
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double tolarance;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long sourcingTypeId;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long purposeId;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long advisingBank;
    @NotNull
    private Date applicationDate;
    @NotNull
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean isApplied;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long udRegisterId;

    @NotNull
    private List<@Valid ImportLcInformationChildRequestDto> items;
}
