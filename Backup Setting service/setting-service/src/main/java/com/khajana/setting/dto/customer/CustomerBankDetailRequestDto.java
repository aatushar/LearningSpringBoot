package com.khajana.setting.dto.customer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBankDetailRequestDto {
    @Nullable
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long bankId;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long bankBranchId;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long bankAccountTypeId;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 16, message = "length can be maximum of 16 digit account number")
    private String customerAccountNumber;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 16, message = "length can be maximum of 16 digit account number BN")
    private String customerAccountNumberBn;
}
