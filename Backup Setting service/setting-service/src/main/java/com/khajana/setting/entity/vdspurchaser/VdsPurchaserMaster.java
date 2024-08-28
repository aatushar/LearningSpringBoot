package com.khajana.setting.entity.vdspurchaser;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "6D1_vds_purchaser_master")
public class VdsPurchaserMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "vm_id")
    private Long vmId;

    @Column(name = "certificate_no")
    private String certificateNo;

    @Column(name = "published_date")
    private Date publishedDate;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "tc_master_id")
    private Long tcMasterId;

    @Column(name = "total_recv_amt_wotax_local_curr")
    private Double totalRecvAmtWotaxLocalCurr;

    @Column(name = "total_vat_amount")
    private Double totalVatAmount;

    @Column(name = "total_deducted_vat_amount")
    private Double totalDeductedVatAmount;

    @Column(name = "total_vds_paid_amount")
    private Double totalVdsPaidAmount;

    @Column(name = "is_paid", nullable = false, updatable = true)
    private Boolean isPaid;


    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

}
