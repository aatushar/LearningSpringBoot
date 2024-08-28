package com.khajana.setting.entity.vat;

import com.khajana.setting.entity.product.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2C8_sv_vat_rate_stage")
public class VatRateStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prod_cat_id", nullable = false, updatable = true)
    private Long productCategoryId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "prod_cat_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductCategory productCategory;

    @Column(name = "vat_rate_stage_name", nullable = false, updatable = true)
    private String name;

    @Column(name = "vat_rate_stage_name_bn", nullable = false, updatable = true)
    private String nameBn;

    @Column(name = "seq_number", nullable = false, updatable = true)
    private Double seqNo;

    @Column(name = "is_active", nullable = false, updatable = true)
    private Boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;
}
