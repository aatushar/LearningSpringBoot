package com.khajana.setting.entity.issue;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "5Z1_itemstock_master")
public class StockMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "receive_Issue_master_id", insertable = true, updatable = true, nullable = true)
    private Long receiveMasterId;
    @Column(name = "tran_source_type_id", insertable = true, updatable = true, nullable = true)
    private Long tranSourceTypeId;
    @Column(name = "tran_type_id", insertable = true, updatable = true, nullable = true)
    private Long tranTypeId;
    @Column(name = "tran_sub_type_id", insertable = true, updatable = true, nullable = true)
    private Long tranSubTypeId;
    @Column(name = "prod_type_id", insertable = true, updatable = true, nullable = true)
    private Long prodTypeId;
    @Column(name = "vat_rebate_id", insertable = true, updatable = true, nullable = true)
    private Long vatRebateId;
    @Column(name = "company_id", insertable = true, updatable = true, nullable = true)
    private Long companyId;
    @Column(name = "branch_id", insertable = true, updatable = true, nullable = true)
    private Long branchId;
    @Column(name = "currency_id", insertable = true, updatable = true, nullable = true)
    private Long currencyId;
    @Column(name = "fiscal_year_id", insertable = true, updatable = true, nullable = true)
    private Long fiscalYearId;
    @Column(name = "vat_month_id", insertable = true, updatable = true, nullable = true)
    private Long vatMonthId;
    @Column(name = "item_info_id", insertable = true, updatable = true, nullable = true)
    private Long itemInfoId;
    @Column(name = "uom_id", insertable = true, updatable = true, nullable = true)
    private Long uomId;
    @Column(name = "vat_payment_method_id", insertable = true, updatable = true, nullable = true)
    private Long vatPaymentMethodId;
    @Column(name = "item_cat_for_retail_id", insertable = true, updatable = true, nullable = true)
    private Long itemCatForRetail;
    @Column(name = "vat_rate_type_id", insertable = true, updatable = true, nullable = true)
    private Long vatRateTypeId;
    @Column(name = "challan_number", insertable = true, updatable = true, nullable = true)
    private String challanNumber;
    @Column(name = "challan_number_bn", insertable = true, updatable = true, nullable = true)
    private String challanNumberBn;
    @Column(name = "remarks", insertable = true, updatable = true, nullable = true)
    private String remarks;
    @Column(name = "remarks_bn", insertable = true, updatable = true, nullable = true)
    private String remarksBn;

    @Column(name = "receive_issue_date", insertable = true, updatable = true, nullable = true)
    private Date receiveIssueDate;
    @Column(name = "opening_bal_date", insertable = true, updatable = true, nullable = true)
    private Date openingBalDate;
    @Column(name = "challan_date", insertable = true, updatable = true, nullable = true)
    private Date challanDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

}
