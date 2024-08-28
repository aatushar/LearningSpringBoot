package com.khajana.setting.entity.item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3N05_item_info")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company_id", insertable = true, updatable = true, nullable = false)
    private Long companyId;

    @Column(name = "vat_payment_method_id", insertable = true, updatable = true, nullable = true)
    private Long vatPaymentMethodId;

    @Column(name = "item_cat_for_retail_id", insertable = true, updatable = true, nullable = true)
    private Long itemCatForRetailId;

    @Column(name = "itm_code", insertable = true, updatable = true)
    private String itmCode;

    @Column(name = "display_itm_code", insertable = true, updatable = true)
    private String displayItmCode;

    @Column(name = "display_itm_code_bn", insertable = true, updatable = true, nullable = true)
    private String displayItmCodeBn;

    @Column(name = "display_itm_name", insertable = true, updatable = true, nullable = true)
    private String displayItmName;

    @Column(name = "display_itm_name_bn", insertable = true, updatable = true, nullable = true)
    private String displayItmNameBn;

    @Column(name = "mushak_itm_name", insertable = true, updatable = true, nullable = true)
    private String mushakItmName;

    @Column(name = "mushak_itm_name_bn", insertable = true, updatable = true, nullable = true)
    private String mushakItmNameBn;

    @Column(name = "country_origin", insertable = true, updatable = true, nullable = true)
    private Long countryOrigin;

    @Column(name = "hs_code_id", insertable = true, updatable = true, nullable = true)
    private Long hsCodeId;

    @Column(name = "currency_info_id", insertable = true, updatable = true)
    private Long currencyInfoId;

    @Column(name = "default_vat_rate_id", insertable = true, updatable = true, nullable = true)
    private Long defaultVatRateId;

    @Column(name = "estimate_time", insertable = true, updatable = true, nullable = true)
    private String estimateTime;

    @Column(name = "uom_id", insertable = true, updatable = true)
    private Long uomId;

    @Column(name = "trns_unit_id", insertable = true, updatable = true, nullable = true)
    private Long trnsUnitId;

    @Column(name = "stock_unit_id", insertable = true, updatable = true)
    private Long stockUnitId;

    @Column(name = "sales_unit_id", insertable = true, updatable = true, nullable = true)
    private Long salesUnitId;

    @Column(name = "current_rate", insertable = true, updatable = true, nullable = true)
    private Double currentRate;

    @Column(name = "ioc_rate", insertable = true, updatable = true, nullable = true)
    private Double iocRate;

    @Column(name = "ioc_min_rate", insertable = true, updatable = true, nullable = true)
    private Double iocMinRate;

    @Column(name = "ioc_max_rate", insertable = true, updatable = true, nullable = true)
    private Double iocMaxRate;

    @Column(name = "ioc_ref_id", insertable = true, updatable = true, nullable = true)
    private Long iocRefId;

    @Column(name = "ioc_purpose_id", insertable = true, updatable = true, nullable = true)
    private Long iocPurposeId;

    @Column(name = "sold_status", insertable = true, updatable = true, nullable = true)
    private Long soldStatus;

    @Column(name = "is_trade_item", insertable = true, updatable = true, nullable = true)
    private Boolean isTradeItem;

    @Column(name = "is_rebateable", insertable = true, updatable = true, nullable = true)
    private Boolean isRebateable;

    @Column(name = "is_active", insertable = true, updatable = true, nullable = false)
    private Boolean active;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", insertable = true, updatable = true, nullable = true)
    private Long updatedBy;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id", referencedColumnName = "item_info_id", insertable = false, updatable = false)
    private ItemStoreMapping itemStoreMapping;

}
