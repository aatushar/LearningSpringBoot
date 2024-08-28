package com.khajana.setting.dto.companybranch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequestDto {
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long countryId;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long currencyId;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String compCode;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long compTypeId;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String compName;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String compNameBn;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String compType;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String regPersonName;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String regPersonNameBn;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String regPersonNid;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String regPersonNidBn;
    @NotNull(message = "required field can not be null")
    private MultipartFile compLogo;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String compShortName;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String compShortNameBn;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String compAddress;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String compAddressBn;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String areaCode;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String areaCodeBn;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    private String phoneNumber;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length should not be greater than 50 characters")
    @Email(message = "input type must be an email")
    private String emailAddress;
}
