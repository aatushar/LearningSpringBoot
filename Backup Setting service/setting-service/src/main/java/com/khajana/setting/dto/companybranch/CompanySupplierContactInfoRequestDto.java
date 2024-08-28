package com.khajana.setting.dto.companybranch;

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
public class CompanySupplierContactInfoRequestDto {
    @Nullable
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;
    @Nullable
    @JsonDeserialize(using = LongDeserializer.class)
    private Long supplierId;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 50, message = "maximum length 50 characters")
    private String contactPerson;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 15, message = "maximum length 15 characters")
    private String phone;
}
