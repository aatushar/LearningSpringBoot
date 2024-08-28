package com.khajana.setting.dto.companybranch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBranchRequestDto {
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long companyId;
    @NotNull(message = "required field can not be empty")
    @Size(max = 100, message = "required field can be maximum of 100 characters")
    private String branchUnitName;
    @NotNull(message = "required field can not be empty")
    @Size(max = 100, message = "required field can be maximum of 100 characters")
    private String branchUnitNameBn;
    @NotNull(message = "required field can not be empty")
    @Size(max = 20, message = "required field can be maximum of 20 characters")
    private String branchUnitBinNumber;
    @NotNull(message = "required field can not be empty")
    @Size(max = 20, message = "required field can be maximum of 20 characters")
    private String branchUnitBinNumberBn;
    @NotNull(message = "required field can not be empty")
    @Size(max = 100, message = "required field can be maximum of 100 characters")
    private String branchUnitShortName;
    @NotNull(message = "required field can not be empty")
    @Size(max = 100, message = "required field can be maximum of 100 characters")
    private String branchUnitShortNameBn;
    @NotNull(message = "required field can not be empty")
    @Size(max = 100, message = "required field can be maximum of 100 characters")
    private String branchUnitVatRegistrationType;
    @NotNull(message = "required field can not be empty")
    @Size(max = 100, message = "required field can be maximum of 100 characters")
    private String branchUnitCustomOfficeArea;
    @NotNull(message = "required field can not be empty")
    @Size(max = 100, message = "required field can be maximum of 100 characters")
    private String branchUnitCustomOfficeAreaBn;
    @NotNull(message = "required field can not be empty")
    @Size(max = 11, message = "required field can be maximum of 20 characters")
    private String branchUnitPhoneNumber;
    @NotNull(message = "required field can not be empty")
    @Size(max = 20, message = "required field can be maximum of 20 characters")
    @Email(message = "input type should be email")
    private String branchUnitEmailAddress;

    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;

    @Valid
    private List<@Valid CompanyBranchBankDetailRequestServiceDto> bankDetails; // nullable
    @Valid
    private List<@Valid CompanyBranchEconomicActivityRequestServiceDto> economicActivities; // nullable
    @Valid
    private List<@Valid CompanyBranchEconomicActivityAreaRequestServiceDto> economicActivityAreas; // nullable
    @Valid
    private List<@Valid CompanyBranchBusinessClassificationCodeRequestServiceDto> classificationCodes; // nullable
    @Valid
    private List<@Valid CompanyBranchAddressDetailRequestServiceDto> addressDetails; // nullable
}
