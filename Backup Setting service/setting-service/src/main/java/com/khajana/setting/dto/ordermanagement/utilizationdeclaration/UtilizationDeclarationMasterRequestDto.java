package com.khajana.setting.dto.ordermanagement.utilizationdeclaration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
public class UtilizationDeclarationMasterRequestDto {

    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long lcId;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long customerId;
    // @NotNull
    @Size(max = 100)
    private String udRegisterNo;

    @NotNull
    private Date udRegisterDate;

    // @Nullable
    // private String buyerCode;

    @NotNull
    private String remarks;

    @NotNull
    private List<@Valid UtilizationDeclarationChildRequestDto> items;
}
