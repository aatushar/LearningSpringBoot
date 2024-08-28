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
@Table(name = "5Z2_itemstock_child")
public class StockChild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "itemstock_master_id", insertable = true, updatable = true, nullable = true)
    private Long itemstockMasterId;

    @Column(name = "receive_issue_child_id", insertable = true, updatable = true, nullable = true)
    private Long receiveIssueChildId;

    @Column(name = "store_id", insertable = true, updatable = true, nullable = true)
    private Long storeId;

    @Column(name = "opening_bal_qty", insertable = true, updatable = true, nullable = true)
    private Double openingBalQty;

    @Column(name = "opening_bal_rate", insertable = true, updatable = true, nullable = true)
    private Double openingBalRate;

    @Column(name = "opening_bal_amount", insertable = true, updatable = true, nullable = true)
    private Double openingBalAmount;

    @Column(name = "opening_bal_amount_with_tax", insertable = true, updatable = true, nullable = true)
    private Double openingBalAmountWithTax;

    @Column(name = "receive_qty", insertable = true, updatable = true, nullable = true)
    private Double receiveQty;

    @Column(name = "receive_rate", insertable = true, updatable = true, nullable = true)
    private Double receiveRate;

    @Column(name = "receive_amount", insertable = true, updatable = true, nullable = true)
    private Double receiveAmount;

    @Column(name = "receive_amount_with_tax", insertable = true, updatable = true, nullable = true)
    private Double receiveAmountWithTax;

    @Column(name = "receive_vat_percent", insertable = true, updatable = true, nullable = true)
    private Double receiveVatPercent;

    @Column(name = "receive_vat_amnt", insertable = true, updatable = true, nullable = true)
    private Double receiveVatAmount;

    @Column(name = "receive_sd_percent", insertable = true, updatable = true, nullable = true)
    private Double receiveSdPercent;

    @Column(name = "receive_sd_amnt", insertable = true, updatable = true, nullable = true)
    private Double receiveSdAmount;

    @Column(name = "issue_qty", insertable = true, updatable = true, nullable = true)
    private Double issueQty;

    @Column(name = "issue_rate", insertable = true, updatable = true, nullable = true)
    private Double issueRate;

    @Column(name = "issue_amount", insertable = true, updatable = true, nullable = true)
    private Double issueAmount;

    @Column(name = "stock_issue_rate", insertable = true, updatable = true, nullable = true)
    private Double stockIssueRate;

    @Column(name = "stock_issue_amount", insertable = true, updatable = true, nullable = true)
    private Double stockIssueAmount;

    @Column(name = "issue_vat_percent", insertable = true, updatable = true, nullable = true)
    private Double issueVatPercent;

    @Column(name = "issue_vat_amnt", insertable = true, updatable = true, nullable = true)
    private Double issueVatAmount;

    @Column(name = "issue_sd_percent", insertable = true, updatable = true, nullable = true)
    private Double issueSdPercent;

    @Column(name = "issue_sd_amnt", insertable = true, updatable = true, nullable = true)
    private Double issueSdAmount;

    @Column(name = "issue_amount_with_tax", insertable = true, updatable = true, nullable = true)
    private Double issueAmountWithTax;

    @Column(name = "closing_bal_qty", insertable = true, updatable = true, nullable = true)
    private Double closingBalQty;

    @Column(name = "closing_bal_rate", insertable = true, updatable = true, nullable = true)
    private Double closingBalRate;

    @Column(name = "closing_bal_amount", insertable = true, updatable = true, nullable = true)
    private Double closingBalAmount;

    @Column(name = "closing_bal_amount_with_tax", insertable = true, updatable = true, nullable = true)
    private Double closingBalAmountWithTax;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;
}
