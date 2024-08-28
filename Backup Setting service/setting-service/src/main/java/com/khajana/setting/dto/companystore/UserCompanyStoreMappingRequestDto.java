package com.khajana.setting.dto.companystore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCompanyStoreMappingRequestDto {
    @NotNull(message = "required field can not be null")
    private Long userId;

    @NotNull(message = "Stores list cannot be null")
    @Valid
    private List<@Valid StoreMapping> stores;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StoreMapping {
        @Nullable
        @JsonDeserialize(using = LongDeserializer.class)
        private Long id;

        @NotNull(message = "Store ID cannot be null")
        @JsonDeserialize(using = LongDeserializer.class)
        private Long storeId;

        @NotNull(message = "Default status cannot be null")
        @JsonDeserialize(using = BooleanDeserializer.class)
        private Boolean isDefault;
    }
}
