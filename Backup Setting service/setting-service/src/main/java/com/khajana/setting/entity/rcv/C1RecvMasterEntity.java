package com.khajana.setting.entity.rcv;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "5C1_recv_master")
public class C1RecvMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "issue_master_id", nullable = true, updatable = true)
    private Long issueMasterId;

    @Column(name = "import_lc_id", nullable = true, updatable = true)
    private Long importLcId;

    @Column(name = "tran_source_type_id", nullable = true, updatable = true)
    private Long tranSourceTypeId;

    @Column(name = "tran_type_id", nullable = true, updatable = true)
    private Long tranTypeId;
    @Column(name = "tran_sub_type_id", nullable = true, updatable = true)
    private Long tranSubTypeId;
    @Column(name = "prod_type_id", nullable = false, updatable = true)
    private Long prodTypeId;
    @Column(name = "vat_rebate_id", nullable = true, updatable = true)
    private Long vatRebateId;
    @Column(name = "company_id", nullable = false, updatable = true)
    private Long companyId;

    @Column(name = "branch_id", nullable = false, updatable = true)
    private Long branchId;

    @Column(name = "store_id", nullable = false, updatable = true)
    private Long storeId;
    @Column(name = "currency_id", nullable = false, updatable = true)
    private Long currencyId;
    @Column(name = "excg_rate", nullable = false, updatable = true)
    private Double excgRate;

    @Column(name = "supplier_id", nullable = true, updatable = true)
    private int supplierId;

    @Column(name = "is_reg", nullable = true, updatable = true)
    private boolean isReg;
    @Column(name = "supplier_bin_number", nullable = true, updatable = true)
    private String supplierBinNo;

    @Column(name = "supplier_bin_number_bn", nullable = true, updatable = true)
    private String supplierBinNoBn;

    @Column(name = "pay_mode_id", nullable = true, updatable = true)
    private Long payModeId;
    @Column(name = "pay_instrument_no", nullable = true, updatable = true)
    private String payInstrumentNo;
    @Column(name = "pay_instrument_date", nullable = true, updatable = true)
    private String payInstrumentDate;
    @Column(name = "payment_institution_id", nullable = true, updatable = true)
    private Long paymentInstitutionId;
    @Column(name = "bank_branch_id", nullable = true, updatable = true)
    private int bankBranchId;
    @Column(name = "bank_account_type_id", nullable = true, updatable = true)
    private Long bankAccTypeId;
    @Column(name = "is_reg_bank_trans", nullable = true, updatable = true)
    private boolean isRegBankTrans;
    @Column(name = "supplier_account_number", nullable = true, updatable = true)
    private String supplierAcctNumber;

    @Column(name = "receive_date", nullable = true, updatable = true)
    private Date receiveDate;

    @Column(name = "fiscal_year_id", nullable = true, updatable = true)
    private int fiscalYearId;

    @Column(name = "vat_month_id", nullable = true, updatable = true)
    private int vatMonthId;

    @Column(name = "grn_number", nullable = false, updatable = true)
    private String grnNumber;

    @Column(name = "grn_number_bn", nullable = false, updatable = true)
    private String grnNumberBn;

    @Column(name = "grn_date", nullable = false, updatable = true)
    private Date grnDate;
    @Column(name = "port_of_discharge_id", nullable = true, updatable = true)
    private Long portOfDischargeId;

    @Column(name = "challan_type_id", nullable = true, updatable = true)
    private int challanTypeId;

    @Column(name = "challan_number", nullable = false, updatable = true)
    private String challanNumber;
    @Column(name = "challan_number_bn", nullable = true, updatable = true)
    private String challanNumberBn;

    @Column(name = "challan_date", nullable = false, updatable = true)
    private Date challanDate;
    @Column(name = "total_assessable_amt_trans_curr", nullable = true, updatable = true)
    private Double totalAsseAmtTransCurr;
    @Column(name = "total_assessable_amt_local_curr", nullable = true, updatable = true)
    private Double totalAsseAmtLocalCurr;

    @Column(name = "recv_amt_wotax_trans_curr", nullable = false, updatable = true)
    private Double recvAmtWotaxTransCurr;
    @Column(name = "recv_amt_wotax_local_curr", nullable = false, updatable = true)
    private Double recvAmtWotaxLocalCurr;

    @Column(name = "total_cd_amount", nullable = true, updatable = true)
    private Double totalCdAmount;

    @Column(name = "total_rd_amount", nullable = true, updatable = true)
    private Double totalRdAmount;
    @Column(name = "total_sd_amount", nullable = true, updatable = true)
    private Double totalSdAmount;
    @Column(name = "total_vat_amount", nullable = true, updatable = true)
    private Double totalVatAmount;
    @Column(name = "total_paid_vat_amount", nullable = true, updatable = true)
    private Double totalPaidVatAmount;
    @Column(name = "total_at_amount", nullable = true, updatable = true)
    private Double totalAtAmount;
    @Column(name = "total_ait_amount", nullable = true, updatable = true)
    private Double totalAitAmount;

    @Column(name = "recv_amt_withtax_trans_curr", nullable = true, updatable = true)
    private Double recvAmtWithtaxTransCurr;
    @Column(name = "recv_amt_withtax_local_curr", nullable = false, updatable = true)
    private Double recvAmtWithtaxLocalCurr;
    @Column(name = "monthly_proc_status", nullable = true, updatable = true)
    private boolean monthlyProcStatus;
    @Column(name = "yearly_proc_status", nullable = true, updatable = true)
    private boolean yearlyProcStatus;

    @Column(name = "is_rebateable", nullable = true, updatable = true)
    private boolean isRebateAble;
    @Column(name = "is_vds_applicable", nullable = true, updatable = true)
    private boolean isVdsApplicable;
    @Column(name = "is_vds_done", nullable = true, updatable = true)
    private boolean isVdsDone;
    @Column(name = "is_paid", nullable = true, updatable = true)
    private boolean isPaid;
    @Column(name = "remarks", nullable = true, updatable = true)
    private String remark;
    @Column(name = "remarks_bn", nullable = true, updatable = true)
    private String remarkBn;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;
}
