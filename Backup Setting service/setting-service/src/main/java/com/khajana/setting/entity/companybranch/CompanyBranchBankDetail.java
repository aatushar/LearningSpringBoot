package com.khajana.setting.entity.companybranch;

import com.khajana.setting.entity.bank.BankAccountType;
import com.khajana.setting.entity.bank.BankBranchInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3B2_branch_bank_details")
public class CompanyBranchBankDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_id", insertable = true, updatable = true)
    private Long branchId;

    @OneToOne(targetEntity = CompanyBranch.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanyBranch companyBranch;

    @Column(name = "bank_branch_id", insertable = true, updatable = true)
    private Long bankBranchId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bank_branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BankBranchInfo bankBranchInfo;

    @Column(name = "bank_account_type_id", insertable = true, updatable = true)
    private Long bankAccountTypeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bank_account_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BankAccountType bankAccountType;

    @Column(name = "company_account_number", insertable = true, updatable = true)
    private String companyAccountNumber;

    @Column(name = "company_account_number_bn", insertable = true, updatable = true)
    private String companyAccountNumberBn;

    @Column(name = "is_active", insertable = true, updatable = true)
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
