package com.khajana.setting.dto.companystore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.IntegerDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyStoreRequestDto {
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long branchId;
    @NotNull(message = "required field can not be null or empty")
    @Size(max = 10, message = "length should not be greater than 10 characters")
    private String slCode;
    @NotNull(message = "required field can not be null or empty")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String slName;
    @NotNull(message = "required field can not be null or empty")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String slNameBn;
    @NotNull(message = "required field can not be null or empty")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String slShortName;
    @NotNull(message = "required field can not be null or empty")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String slShortNameBn;
    @NotNull(message = "required field can not be null or empty")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String slAddress;
    @Nullable
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String slAddressBn;
    @Nullable
    @JsonDeserialize(using = IntegerDeserializer.class)
    private Long slOfficerId;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long slType;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean isDefaultLocation;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean isSalesPoint;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean isVirtualLocation;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}
