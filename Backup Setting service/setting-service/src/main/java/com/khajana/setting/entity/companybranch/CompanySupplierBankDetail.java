package com.khajana.setting.entity.companybranch;

import com.khajana.setting.entity.bank.BankAccountType;
import com.khajana.setting.entity.bank.BankBranchInfo;
import com.khajana.setting.entity.bank.BankInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3K5_supplier_bank_details")
public class CompanySupplierBankDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_id")
    private Long supplierId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanySupplier companySupplier;

    @Column(name = "bank_id")
    private Long bankId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bank_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BankInfo bankInfo;

    @Column(name = "bank_branch_id")
    private Long bankBranchId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bank_branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BankBranchInfo bankBranchInfo;

    @Column(name = "bank_account_type_id")
    private Long bankAccountTypeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bank_account_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BankAccountType bankAccountType;

    @Column(name = "supplier_account_number", length = 100)
    private String supplierAccountNumber;

    @Column(name = "supplier_account_number_bn", length = 100)
    private String supplierAccountNumberBn;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", insertable = true, updatable = true, nullable = true)
    private Long updatedBy;

}
