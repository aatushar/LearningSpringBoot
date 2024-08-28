package com.khajana.setting.entity.ioc;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3U2_ioc_item_details")
public class IocDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ioc_price_declaration_id", insertable = true, updatable = true, nullable = false)
    private Long iocPriceDeclarationId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ioc_price_declaration_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Ioc ioc;

    @Column(name = "prod_type_id", insertable = true, updatable = true, nullable = true)
    private Long prodTypeId;

    @Column(name = "item_info_id", insertable = true, updatable = true, nullable = false)
    private Long itemInfoId;

    @Column(name = "consumption_uom_id", insertable = true, updatable = true, nullable = false)
    private Long consumptionUomId;

    @Column(name = "consumption_calculation_qty", insertable = true, updatable = true, nullable = false)
    private Double consumptionCalculationQty;

    @Column(name = "consumption_ioc_qty", insertable = true, updatable = true, nullable = false)
    private Double consumptionIocQty;

    @Column(name = "purchase_rate", insertable = true, updatable = true, nullable = false)
    private Double purchaseRate;

    @Column(name = "wastage_of_calculation_qty", insertable = true, updatable = true, nullable = false)
    private Double wastageOfCalculationQty;

    @Column(name = "wastage_of_ioc_qty", insertable = true, updatable = true, nullable = true)
    private Double wastageOfIocQty;

    @Column(name = "ioc_qty", insertable = true, updatable = true, nullable = true)
    private Double iocQty;

    @Column(name = "wastage_percent", insertable = true, updatable = true, nullable = false)
    private Double wastagePercent;

    @Column(name = "calculation_amt", insertable = true, updatable = true, nullable = false)
    private Double calculationAmt;

    @Column(name = "ioc_amt", insertable = true, updatable = true, nullable = false)
    private Double iocAmt;

    @Column(name = "is_rebatable", insertable = true, updatable = true, nullable = true)
    private Boolean isRebatable;

    @Column(name = "sequence", insertable = true, updatable = true, nullable = true)
    private Double seqNo;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", insertable = true, updatable = true, nullable = true)
    private Long updatedBy;

}
