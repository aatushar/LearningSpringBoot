package com.khajana.setting.dto.companybranch;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBranchEconomicActivityRequestDto {
    @NotNull(message = "required field can not be empty")
    private Long branchId;
    @NotNull(message = "required field can not be empty")
    private Long economicActivityId;
    @NotNull(message = "required field can not be empty")
    @NotNull(message = "required field can not be empty")
    @Size(max = 20, message = "required field can be maximum of 20 characters")
    private String supportingDocNo;
    @NotNull(message = "required field can not be empty")
    private Date supportingDocIssueDate;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;


}
