package com.khajana.setting.dto.companybranch;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEmployeeRequestDto {
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long departmentId;

    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long sectionId;

    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long designationId;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length will be maximum of 50 characters")
    private String code;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length will be maximum of 50 characters")
    private String codeBn;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length will be maximum of 50 characters")
    private String name;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length will be maximum of 50 characters")
    private String nameBn;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length will be maximum of 50 characters")
    private String type;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length will be maximum of 50 characters")
    private String nid;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length will be maximum of 50 characters")
    private String nidDoc;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length will be maximum of 50 characters")
    private String address;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length will be maximum of 50 characters")
    private String addressBn;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length will be maximum of 50 characters")
    @Email(message = "need a valid email address")
    private String email;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length will be maximum of 50 characters")
    private MultipartFile photo;

    @NotNull(message = "required field can not be null")
    @Size(max = 20, message = "length will be maximum of 20 characters")
    private String phone;

    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}
