package com.khajana.setting.dto.companybranch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDepartmentResponseDto {
    private Long id;
    private Long companyId;
    private String companyName;
    private String departmentName;
    private String departmentNameBn;
    private String departmentPrefix;
    private String departmentPrefixBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
