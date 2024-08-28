package com.khajana.setting.entity.receive;

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
public class ReceiveMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "issue_master_id")
    private Long issueMasterId;

    @Column(name = "credit_note_master_id")
    private Long creditNoteMasterId;

    @Column(name = "txn_code")
    private String txnCode;

    @Column(name = "import_lc_id")
    private Long importLcId;

    @Column(name = "ud_id")
    private Long udId;

    @Column(name = "tran_source_type_id")
    private Long tranSourceTypeId;

    @Column(name = "tran_type_id")
    private Long tranTypeId;

    @Column(name = "tran_sub_type_id")
    private Long tranSubTypeId;

    @Column(name = "prod_type_id")
    private Long prodTypeId;

    @Column(name = "vat_rebate_id")
    private Long vatRebateId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "excg_rate")
    private Double excgRate;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "is_reg")
    private Boolean isReg;

    @Column(name = "supplier_bin_number")
    private String supplierBinNumber;

    @Column(name = "supplier_bin_number_bn")
    private String supplierBinNumberBn;

    @Column(name = "pay_mode_id")
    private Long payModeId;

    @Column(name = "pay_instrument_no")
    private String payInstrumentNo;

    @Column(name = "pay_instrument_date")
    private String payInstrumentDate;

    @Column(name = "payment_institution_id")
    private Long paymentInstitutionId;

    @Column(name = "bank_branch_id")
    private Long bankBranchId;

    @Column(name = "bank_account_type_id")
    private Long bankAccountTypeId;

    @Column(name = "is_reg_bank_trans")
    private Boolean isRegBankTrans;

    @Column(name = "supplier_account_number")
    private String supplierAccountNumber;

    @Column(name = "receive_date")
    private Date receiveDate;

    @Column(name = "fiscal_year_id")
    private Long fiscalYearId;

    @Column(name = "vat_month_id")
    private Long vatMonthId;

    @Column(name = "grn_number")
    private String grnNumber;

    @Column(name = "grn_number_bn")
    private String grnNumberBn;

    @Column(name = "grn_date")
    private Date grnDate;

    @Column(name = "port_of_discharge_id")
    private Long portOfDischargeId;

    @Column(name = "challan_type_id")
    private Short challanTypeId;

    @Column(name = "challan_number")
    private String challanNumber;
    @Column(name = "challan_number_bn")
    private String challanNumberBn;

    @Column(name = "challan_date")
    private Date challanDate;

    @Column(name = "total_assessable_amt_trans_curr")
    private Double totalAssessableAmtTransCurr;

    @Column(name = "total_assessable_amt_local_curr")
    private Double totalAssessableAmtLocalCurr;

    @Column(name = "recv_amt_wotax_trans_curr")
    private Double recvAmtWotaxTransCurr;

    @Column(name = "recv_amt_wotax_local_curr")
    private Double recvAmtWotaxLocalCurr;

    @Column(name = "total_cd_amount")
    private Double totalCdAmount;

    @Column(name = "total_rd_amount")
    private Double totalRdAmount;

    @Column(name = "total_sd_amount")
    private Double totalSdAmount;

    @Column(name = "total_vat_amount")
    private Double totalVatAmount;

    @Column(name = "total_paid_vat_amount")
    private Double totalPaidVatAmount;

    @Column(name = "total_at_amount")
    private Double totalAtAmount;

    @Column(name = "total_ait_amount")
    private Double totalAitAmount;

    @Column(name = "recv_amt_withtax_trans_curr")
    private Double recvAmtWithtaxTransCurr;

    @Column(name = "recv_amt_withtax_local_curr")
    private Double recvAmtWithtaxLocalCurr;

    @Column(name = "monthly_proc_status")
    private Boolean monthlyProcStatus;

    @Column(name = "yearly_proc_status")
    private Boolean yearlyProcStatus;

    @Column(name = "is_rebateable")
    private Boolean isRebateable;

    @Column(name = "is_vds_applicable")
    private Boolean isVdsApplicable;

    @Column(name = "is_vds_done")
    private Boolean isVdsDone;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "is_bond")
    private Boolean isBond;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "remarks_bn")
    private String remarksBn;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;
}
