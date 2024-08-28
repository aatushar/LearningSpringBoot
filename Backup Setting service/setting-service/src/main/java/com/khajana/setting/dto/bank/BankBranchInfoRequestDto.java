package com.khajana.setting.dto.bank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankBranchInfoRequestDto {
    @JsonDeserialize(using = LongDeserializer.class)
    private Long bankId;
    @NotNull(message = "bankBranchName can not be empty")
    @Size(max = 100, message = "bankBranchName should not be greater than 100 characters")
    private String bankBranchName;
    @NotNull(message = "bankBranchNameBn can not be empty")
    @Size(max = 100, message = "bankBranchNameBn should not be greater than 100 characters")
    private String bankBranchNameBn;
    @NotNull(message = "bankBranchAddress can not be empty")
    @Size(max = 100, message = "bankBranchAddress should not be greater than 100 characters")
    private String bankBranchAddress;
    @NotNull(message = "bankBranchAddressBn can not be empty")
    @Size(max = 100, message = "bankBranchAddressBn should not be greater than 100 characters")
    private String bankBranchAddressBn;
    @NotNull(message = "bankBranchRoutingNo can not be empty")
    @Size(max = 10, message = "bankBranchRoutingNo should not be greater than 10 characters")
    private String bankBranchRoutingNo;
    @NotNull(message = "bankBranchPhoneNumber can not be empty")
    private String bankBranchPhoneNumber;
    @NotNull(message = "bankBranchEmailAddress can not be empty")
    @Size(max = 100, message = "bankBranchEmailAddress should not be greater than 100 characters")
    @Email
    private String bankBranchEmailAddress;
    @NotNull(message = "Sequence Number can not be empty")
    @DecimalMin(value = "1.00", message = "Sequence Number must be at least 1.00")
    @DecimalMax(value = "9999999.99", message = "Sequence Number must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "Active can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}