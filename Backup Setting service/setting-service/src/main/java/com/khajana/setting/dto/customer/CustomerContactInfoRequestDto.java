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
public class CustomerContactInfoRequestDto {
    @Nullable
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 50, message = "maximum length 50 characters")
    private String contactPerson;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 50, message = "maximum length 50 characters")
    private String contactPersonPhone;

}
