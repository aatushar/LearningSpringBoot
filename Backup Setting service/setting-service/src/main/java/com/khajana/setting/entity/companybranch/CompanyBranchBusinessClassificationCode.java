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
@Table(name = "3B5_business_classification_code")
public class CompanyBranchBusinessClassificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_id")
    private Long branchId;

    @OneToOne(targetEntity = CompanyBranch.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanyBranch companyBranch;

    @Column(name = "hs_code_id")
    private Long hsCodeId;

    // @OneToOne(targetEntity = HsCode.class, cascade = CascadeType.ALL)
    // @JoinColumn(name = "hs_code_id", referencedColumnName = "id", insertable = false, updatable = false)
    // private HsCode hsCode;

    @Column(name = "commercial_description_of_supply")
    private Long commercialDescriptionOfSupply;

    @Column(name = "description_of_hscode")
    private String descriptionOfHsCode;

    @Column(name = "seq_number")
    private Double seqNo;

    @Column(name = "is_active")
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
