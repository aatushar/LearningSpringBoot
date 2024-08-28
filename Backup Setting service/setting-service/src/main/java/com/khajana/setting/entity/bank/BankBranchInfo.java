package com.khajana.setting.entity.bank;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2H2_sv_bank_branch_info")
public class BankBranchInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "bank_id", nullable = false, updatable = true)
    private Long bankId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BankInfo bankInfo;

    @Column(name = "bank_branch_name", nullable = false, updatable = true)
    private String bankBranchName;

    @Column(name = "bank_branch_name_bn", nullable = false, updatable = true)
    private String bankBranchNameBn;

    @Column(name = "bank_branch_address", nullable = false, updatable = true)
    private String bankBranchAddress;

    @Column(name = "bank_branch_address_bn", nullable = false, updatable = true)
    private String bankBranchAddressBn;

    @Column(name = "routing_no", nullable = false, updatable = true)
    private String bankBranchRoutingNo;

    @Column(name = "phone_number", nullable = false, updatable = true)
    private String bankBranchPhoneNumber;

    @Column(name = "email_address", nullable = false, updatable = true)
    private String bankBranchEmailAddress;

    @Column(name = "seq_number", nullable = false, updatable = true)
    private Double seqNo;

    @Column(name = "is_active", nullable = false, updatable = true)
    private Boolean isActive;

    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

}
