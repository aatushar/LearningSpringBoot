package com.khajana.setting.entity.issuemaster;

import com.khajana.setting.entity.customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "5K1_issue_master")
public class IssueMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "indent_master_id", nullable = true, updatable = true)
    private Long indentMasterId;

    @Column(name = "receive_master_id", nullable = true, updatable = true)
    private Long receiveMasterId;
    @Column(name = "export_lc_id", nullable = true, updatable = true)
    private Long exportLcId;
    @Column(name = "tran_source_type_id", nullable = true, updatable = true)
    private Long tranSourceTypeId;
    @Column(name = "tran_type_id", nullable = true, updatable = true)
    private Long tranTypeId;
    @Column(name = "tran_sub_type_id", nullable = true, updatable = true)
    private Long tranSubId;
    @Column(name = "prod_type_id", nullable = true, updatable = true)
    private Long prodTypeId;
    @Column(name = "company_id", nullable = false, insertable = false, updatable = false)
    private Long companyId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    /*
     * @JoinTable( name="2J1_sv_fiscal_year_info", joinColumns = {@JoinColumn(name =
     * "id")}, inverseJoinColumns = {@JoinColumn(name = "fy_id")})
     */
    @JoinColumn(name = "company_id")
    // @JoinColumn(name="fy_id", nullable=true)
    private Customer customerDetailsEntity;

    @Column(name = "branch_id", nullable = false, updatable = true)
    private int branchId;
    @Column(name = "store_id", nullable = false, updatable = true)
    private int storeId;
    @Column(name = "currency_id", nullable = false, updatable = true)
    private int currencyId;
    @Column(name = "excg_rate", nullable = false, updatable = true)
    private Double excgRate;
    @Column(name = "customer_id", nullable = true, updatable = true)
    private Long customerId;
    @Column(name = "emi_master_id", nullable = true, updatable = true)
    private Long emiMasterId;
    @Column(name = "reg_status", nullable = false, updatable = true)
    private Long regStatus;
    @Column(name = "customer_bin_number", nullable = true, updatable = true)
    private String customerBinNo;
    @Column(name = "customer_bin_number_bn", nullable = true, updatable = true)
    private String customerBinNoBn;
    @Column(name = "pay_mode_id", nullable = true, updatable = true)
    private Long payModeId;
    @Column(name = "pay_instrument_no", nullable = true, updatable = true)
    private String payInstrumentNo;
    @Column(name = "pay_instrument_date", nullable = true, updatable = true)
    private String payInstrumentDate;
    @Column(name = "bank_branch_id", nullable = true, updatable = true)
    private int bankBranchId;

    @Column(name = "bank_account_type_id", nullable = true, updatable = true)
    private Long bankAccountTypeId;

    @Column(name = "customer_account_number", nullable = true, updatable = true)
    private String customerAcctNumber;
    @Column(name = "is_reg_bank_trans", nullable = true, updatable = true)
    private Long isRegBankTrans;
    @Column(name = "delivery_to", nullable = true, updatable = true)
    private String deliveryTo;
    @Column(name = "delivery_to_bn", nullable = true, updatable = true)
    private String deliveryToBn;
    @Column(name = "delivery_purpose", nullable = true, updatable = true)
    private String deliveryPurpose;
    @Column(name = "delivery_date", nullable = false, updatable = true)
    private Date deliveryDate;
    @Column(name = "fiscal_year_id", nullable = true, updatable = true)
    private int fiscalYearId;
    @Column(name = "vat_month_id", nullable = true, updatable = true)
    private int vatMontId;
    @Column(name = "custom_office_id", nullable = true, updatable = true)
    private Long customerOfficeId;

    @Column(name = "issue_number", nullable = false, updatable = true)
    private String issueDeliveryNo;

    @Column(name = "issue_number_bn", nullable = false, updatable = true)
    private String issueDeliveryNoBn;
    @Column(name = "issue_date", nullable = false, updatable = true)
    private Date issueDate;
    @Column(name = "employee_id", nullable = true, updatable = true)
    private Long employeeId;
    @Column(name = "department_id", nullable = false, updatable = true)
    private Long departmentId;
    @Column(name = "requisition_num", nullable = false, updatable = true)
    private String requisitionNum;
    @Column(name = "requisition_num_bn", nullable = false, updatable = true)
    private String requisitionNumBn;
    @Column(name = "sales_invoice_date", nullable = false, updatable = true)
    private Date salesInvoiceDate;
    @Column(name = "is_vds_applicable", nullable = true, updatable = true)
    private int isVdsApplicable;
    @Column(name = "is_vds_done", nullable = true, updatable = true)
    private Long isVdsDone;
    @Column(name = "port_of_discharge_id", nullable = true, updatable = true)
    private int portOfDischargeId;
    @Column(name = "challan_type_id", nullable = true, updatable = true)
    private int challanTypeId;
    @Column(name = "challan_number", nullable = true, updatable = true)
    private String challanNumber;
    @Column(name = "challan_number_bn", nullable = true, updatable = true)
    private String challanNumberBn;
    @Column(name = "challan_date", nullable = false, updatable = true)
    private Date cahallanDate;
    @Column(name = "vehicle_num", nullable = false, updatable = true)
    private String vehicleNum;
    @Column(name = "vehicle_num_bn", nullable = false, updatable = true)
    private String vehicleNumBn;
    @Column(name = "vehicle_type_id", nullable = true, updatable = true)
    private int vehicleTypeId;
    @Column(name = "total_issue_amount_before_discount", nullable = false, updatable = true)
    private Double totalIsAmBeDiscount;
    @Column(name = "total_issue_amt_local_curr_before_discount", nullable = false, updatable = true)
    private Double totalIsAmLocalCurrBeDiscount;
    @Column(name = "total_discount", nullable = false, updatable = true)
    private Double totalDiscount;
    @Column(name = "total_cd_amount", nullable = true, updatable = true)
    private Double totalCdAmount;
    @Column(name = "total_rd_amount", nullable = true, updatable = true)
    private Double totalRdAmount;
    @Column(name = "total_sd_amnt", nullable = true, updatable = true)
    private Double totalSdAmount;
    @Column(name = "total_vat_amnt", nullable = true, updatable = true)
    private Double totalVatAmount;
    @Column(name = "total_issue_amt_trans_curr", nullable = false, updatable = true)
    private Double totalIssueAmtTransCurr;
    @Column(name = "total_issue_amt_local_curr", nullable = false, updatable = true)
    private Double totalIssueAmtLocalCurr;
    @Column(name = "remarks", nullable = true, updatable = true)
    private String remarks;
    @Column(name = "remarks_bn", nullable = true, updatable = true)
    private String remarksBn;
    @Column(name = "monthly_proc_status", nullable = true, updatable = true)
    private int monthlyProcStatus;
    @Column(name = "yearly_proc_status", nullable = true, updatable = true)
    private int yearlyProcStatus;

    @Column(name = "print_status", nullable = false, updatable = true)
    private int printStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;
}
