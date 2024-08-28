package com.khajana.setting.entity.companybranch;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3B1_company_branch_unit")
public class CompanyBranch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "company_id", nullable = true, updatable = true)
    private Long companyId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Company company;

    @Column(name = "b_u_name", nullable = true, updatable = true)
    private String branchUnitName;

    @Column(name = "b_u_name_bn", nullable = true, updatable = true)
    private String branchUnitNameBn;

    @Column(name = "b_u_bin_number", nullable = true, updatable = true)
    private String branchUnitBinNumber;

    @Column(name = "b_u_bin_number_bn", nullable = true, updatable = true)
    private String branchUnitBinNumberBn;

    @Column(name = "b_u_short_name", nullable = true, updatable = true)
    private String branchUnitShortName;

    @Column(name = "b_u_short_name_bn", nullable = true, updatable = true)
    private String branchUnitShortNameBn;

    @Column(name = "b_u_vat_reg_type", nullable = true, updatable = true)
    private String branchUnitVatRegistrationType;

    @Column(name = "b_u_custom_office_area", nullable = true, updatable = true)
    private String branchUnitCustomOfficeArea;

    @Column(name = "b_u_custom_office_area_bn", nullable = true, updatable = true)
    private String branchUnitCustomOfficeAreaBn;

    @Column(name = "b_u_phone_number", nullable = true, updatable = true)
    private String branchUnitPhoneNumber;

    @Column(name = "b_u_email_adress", nullable = true, updatable = true)
    private String branchUnitEmailAddress;

    @Column(name = "is_active", nullable = false, updatable = true)
    private Boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

    @OneToMany(targetEntity = CompanyBranchAddressDetail.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<CompanyBranchAddressDetail> companyBranchAddressDetails;

    @OneToMany(targetEntity = CompanyBranchBankDetail.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<CompanyBranchBankDetail> companyBranchBankDetails;

    @OneToMany(targetEntity = CompanyBranchBusinessClassificationCode.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<CompanyBranchBusinessClassificationCode> companyBranchBusinessClassificationCodes;

    @OneToMany(targetEntity = CompanyBranchEconomicActivity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<CompanyBranchEconomicActivity> companyBranchEconomicActivities;

    @OneToMany(targetEntity = CompanyBranchEconomicActivityArea.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<CompanyBranchEconomicActivityArea> companyBranchEconomicActivityAreas;

}
