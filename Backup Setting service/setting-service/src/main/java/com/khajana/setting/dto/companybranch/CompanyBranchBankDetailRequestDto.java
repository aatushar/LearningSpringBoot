package com.khajana.setting.dto.companybranch;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBranchBankDetailRequestDto {
    @NotNull(message = "required field can not be empty")
    private Long branchId;
    @NotNull(message = "required field can not be empty")
    private Long bankBranchId;
    @NotNull(message = "required field can not be empty")
    private Long bankAccountTypeId;
    @NotBlank(message = "required field can not be empty")
    @NotNull(message = "required field can not be empty")
    @Size(max = 20, message = "required field can be maximum of 20 characters")
    private String companyAccountNumber;
    @NotBlank(message = "required field can not be empty")
    @NotNull(message = "required field can not be empty")
    @Size(max = 20, message = "required field can be maximum of 20 characters")
    private String companyAccountNumberBn;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;


}
