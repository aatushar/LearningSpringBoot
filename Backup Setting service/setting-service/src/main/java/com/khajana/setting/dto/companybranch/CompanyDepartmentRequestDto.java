package com.khajana.setting.dto.companybranch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDepartmentRequestDto {
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long companyId;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length will be maximum of 50 characters")
    private String departmentName;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length will be maximum of 50 characters")
    private String departmentNameBn;

    @NotNull(message = "required field can not be null")
    @Size(max = 10, message = "length will be maximum of 10 characters")
    private String departmentPrefix;

    @NotNull(message = "required field can not be null")
    @Size(max = 10, message = "length will be maximum of 10 characters")
    private String departmentPrefixBn;

    @NotNull(message = "required field can not be null")
    @DecimalMin(value = "1.00", message = "Sequence Number must be at least 1.00")
    @DecimalMax(value = "9999.99", message = "Sequence Number must be at most 9999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;

    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}
