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
@Table(name = "5K2_issue_child")
public class IssueChild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "issue_master_id", insertable = true, updatable = true, nullable = false)
    private Long issueMasterId;

    @Column(name = "card_id", insertable = true, updatable = true, nullable = true)
    private Long cardId;

    @Column(name = "item_info_id", insertable = true, updatable = true, nullable = false)
    private Long itemInfoId;

    @Column(name = "uom_id", insertable = true, updatable = true, nullable = false)
    private Long uomId;

    @Column(name = "uom_short_code", insertable = true, updatable = true, nullable = false)
    private String uomShortCode;

    @Column(name = "relative_factor", insertable = true, updatable = true, nullable = false)
    private Double relativeFactor;

    @Column(name = "vat_payment_method_id", insertable = true, updatable = true, nullable = true)
    private Long vatPaymentMethodId;

    @Column(name = "item_cat_for_retail_id", insertable = true, updatable = true, nullable = true)
    private Long itemCatForRetailId;

    @Column(name = "issue_qty", insertable = true, updatable = true, nullable = false)
    private Double issueQty;

    @Column(name = "discount_percent", insertable = true, updatable = true, nullable = true)
    private Double discountPercent;

    @Column(name = "item_rate", insertable = true, updatable = true, nullable = true)
    private Double itemRate;

    @Column(name = "issue_rate", insertable = true, updatable = true, nullable = false)
    private Double issueRate;

    @Column(name = "mrp_value", insertable = true, updatable = true, nullable = true)
    private Double mrpValue;

    @Column(name = "discount_amount", insertable = true, updatable = true, nullable = true)
    private Double discountAmount;

    @Column(name = "item_value_tran_curr", insertable = true, updatable = true, nullable = true)
    private Double itemValueTranCurr;

    @Column(name = "item_value_local_curr", insertable = true, updatable = true, nullable = true)
    private Double itemValueLocalCurr;

    @Column(name = "vat_rate_type_id", insertable = true, updatable = true, nullable = false)
    private Long vatRateTypeId;

    @Column(name = "is_fixed_rate", insertable = true, updatable = true, nullable = true)
    private Boolean isFixedRate;

    @Column(name = "cd_percent", insertable = true, updatable = true, nullable = true)
    private Double cdPercent;

    @Column(name = "cd_amount", insertable = true, updatable = true, nullable = true)
    private Double cdAmount;

    @Column(name = "rd_percent", insertable = true, updatable = true, nullable = true)
    private Double rdPercent;

    @Column(name = "rd_amount", insertable = true, updatable = true, nullable = true)
    private Double rdAmount;

    @Column(name = "indent_child_id", insertable = true, updatable = true, nullable = true)
    private Long indentChildId;

    @Column(name = "sd_percent", insertable = true, updatable = true, nullable = true)
    private Double sdPercent;

    @Column(name = "sd_amount", insertable = true, updatable = true, nullable = true)
    private Double sdAmount;

    @Column(name = "vat_percent", insertable = true, updatable = true, nullable = true)
    private Double vatPercent;

    @Column(name = "fixed_rate_uom_id", insertable = true, updatable = true, nullable = true)
    private Long fixedRateUomId;

    @Column(name = "fixed_rate", insertable = true, updatable = true, nullable = true)
    private Double fixedRate;

    @Column(name = "vat_amount", insertable = true, updatable = true, nullable = true)
    private Double vatAmount;

    @Column(name = "ait_percent", insertable = true, updatable = true, nullable = true)
    private Double aitPercent;

    @Column(name = "ait_amount", insertable = true, updatable = true, nullable = true)
    private Double aitAmount;

    @Column(name = "at_percent", insertable = true, updatable = true, nullable = true)
    private Double atPercent;

    @Column(name = "at_amount", insertable = true, updatable = true, nullable = true)
    private Double atAmount;

    @Column(name = "total_amount_tran_curr", insertable = true, updatable = true, nullable = true)
    private Double totalAmountTranCurr;

    @Column(name = "total_amount_local_curr", insertable = true, updatable = true, nullable = true)
    private Double totalAmountLocalCurr;

    @Column(name = "trn_unit_id", insertable = true, updatable = true, nullable = true)
    private Long trnUnitId;

    @Column(name = "inventory_method_id", insertable = true, updatable = true, nullable = true)
    private Long inventoryMethodId;

    @Column(name = "itm_trade_rate", insertable = true, updatable = true, nullable = true)
    private Double itmTradeRate;

    @Column(name = "itm_wholesle_rate", insertable = true, updatable = true, nullable = true)
    private Double itmWholesaleRate;

    @Column(name = "itm_export_rate", insertable = true, updatable = true, nullable = true)
    private Double itmExportRate;

    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;
}
