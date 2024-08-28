package com.khajana.setting.entity.companybranch;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2R10_Company_Type")
public class CompanyTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_type_code", nullable = false, updatable = true)
    private String companyTypeCode;

    @Column(name = "company_type_code_bn", nullable = false, updatable = true)
    private String companyTypeCodeBn;

    @Column(name = "company_type", nullable = false, updatable = true)
    private String companyType;
    @Column(name = "company_type_bn", nullable = false, updatable = true)
    private String companyTypeBn;

    @Column(name = "seq_number", updatable = true, nullable = false)
    private Double seqNo;

    @Column(name = "is_active", nullable = false, updatable = true)
    private Boolean active;

    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;
}
