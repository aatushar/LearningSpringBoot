package com.khajana.setting.entity.itemcatforretail;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2C5_sv_item_cat_for_retail")
public class ItemCatForRetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_cat_for_retail_name", nullable = false, updatable = true)
    private String itemRetailNameE;

    @Column(name = "item_cat_for_retail_name_bn", nullable = false, updatable = true)
    private String itemRetailNameBnE;

    @Column(name = "seq_number", nullable = false, updatable = true)
    private Double seqNoE;

    @Column(name = "is_active", nullable = false, updatable = true)
    private boolean isActive;

    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;
}
