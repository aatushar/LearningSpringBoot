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
@Table(name = "5C2_recv_child")
public class ReceiveChild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "receive_master_id")
    private Long receiveMasterId;

    @Column(name = "item_info_id")
    private Long itemInfoId;

    @Column(name = "uom_id")
    private Long uomId;

    @Column(name = "uom_short_code")
    private String uomShortCode;

    @Column(name = "relative_factor")
    private Double relativeFactor;

    @Column(name = "vat_payment_method_id")
    private Long vatPaymentMethodId;

    @Column(name = "item_cat_for_retail_id")
    private Long itemCatForRetailId;

    @Column(name = "gate_recv_qty")
    private Double gateRecvQty;

    @Column(name = "recv_quantity")
    private Double recvQuantity;

    @Column(name = "itm_receive_rate")
    private Double itmReceiveRate;

    @Column(name = "item_assessable_value_trans_curr")
    private Double itemAssessableValueTransCurr;

    @Column(name = "item_assessable_value_local_curr")
    private Double itemAssessableValueLocalCurr;

    @Column(name = "item_value_wotax_trans_curr")
    private Double itemValueWotaxTransCurr;

    @Column(name = "item_value_wotax_local_curr")
    private Double itemValueWotaxLocalCurr;

    @Column(name = "vat_rate_type_id")
    private Long vatRateTypeId;

    @Column(name = "is_fixed_rate")
    private Boolean isFixedRate;

    @Column(name = "cd_percent")
    private Double cdPercent;

    @Column(name = "cd_amount")
    private Double cdAmount;

    @Column(name = "rd_percent")
    private Double rdPercent;

    @Column(name = "rd_amount")
    private Double rdAmount;

    @Column(name = "sd_percent")
    private Double sdPercent;

    @Column(name = "sd_amount")
    private Double sdAmount;

    @Column(name = "vat_percent")
    private Double vatPercent;

    @Column(name = "fixed_rate_uom_id")
    private Long fixedRateUomId;

    @Column(name = "fixed_rate")
    private Double fixedRate;

    @Column(name = "vat_amount")
    private Double vatAmount;

    @Column(name = "at_percent")
    private Double atPercent;

    @Column(name = "at_amount")
    private Double atAmount;

    @Column(name = "ait_percent")
    private Double aitPercent;

    @Column(name = "ait_amount")
    private Double aitAmount;

    @Column(name = "total_amt_trans_curr")
    private Double totalAmtTransCurr;

    @Column(name = "total_amt_local_curr")
    private Double totalAmtLocalCurr;

    @Column(name = "gate_entry_at")
    private Date gateEntryAt;

    @Column(name = "gate_entry_by")
    private Long gateEntryBy;

    @Column(name = "opening_stock_remarks")
    private String openingStockRemarks;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;
}
