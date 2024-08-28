package com.khajana.setting.entity.customer;

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
@Table(name = "3J5_customer_bank_details")
public class CustomerBankDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Customer customer;

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

    @Column(name = "customer_account_number", length = 100)
    private String customerAccountNumber;

    @Column(name = "customer_account_number_bn", length = 100)
    private String customerAccountNumberBn;

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
