package com.khajana.setting.dto.companybranch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
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
public class CompanyTypeRequestDto {
    @NotNull(message = "companyTypeCode can not be empty")
    @Size(max = 100, message = "companyTypeCode should not be greater than 100 characters")
    private String companyTypeCode;

    @NotNull(message = "companyTypeCodeBn can not be empty")
    @Size(max = 100, message = "companyTypeCodeBn  should not be greater than 100 characters")
    private String companyTypeCodeBn;

    @NotNull(message = "companyType can not be empty")
    @Size(max = 100, message = "companyType should not be greater than 100 characters")
    private String companyType;

    @NotNull(message = "companyTypeBn can not be empty")
    @Size(max = 100, message = "companyTypeBn  should not be greater than 100 characters")
    private String companyTypeBn;

    @NotNull(message = "Sequence Number can not be empty")
    @DecimalMin(value = "1.00", message = "Sequence Number must be at least 1.00")
    @DecimalMax(value = "9999999.99", message = "Sequence Number must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "active can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}
