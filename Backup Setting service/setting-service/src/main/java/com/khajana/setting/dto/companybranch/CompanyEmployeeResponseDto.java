package com.khajana.setting.dto.companybranch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEmployeeResponseDto {
    private Long id;
    private Long departmentId;
    private String departmentName;
    private Long sectionId;
    private String sectionName;
    private Long designationId;
    private String designationName;
    private String code;
    private String codeBn;
    private String name;
    private String nameBn;
    private String type;
    private String nid;
    private String nidDoc;
    private String address;
    private String addressBn;
    private String email;
    private String photo;
    private String phone;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
