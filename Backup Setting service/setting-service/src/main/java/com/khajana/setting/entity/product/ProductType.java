package com.khajana.setting.entity.product;

import com.khajana.setting.entity.item.ItemMasterGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2D2_sv_product_type")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "prod_cat_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductCategory productCategory;

    @OneToMany(targetEntity = ItemMasterGroup.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "prod_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<ItemMasterGroup> itemMasterGroups;

    @Column(name = "prod_cat_id", nullable = false, updatable = true)
    private Long productCategoryId;

    @Column(name = "prod_type_name", nullable = false, updatable = true)
    private String name;

    @Column(name = "prod_type_name_bn", nullable = false, updatable = true)
    private String nameBn;

    @Column(name = "seq_number", nullable = false, updatable = true)
    private Double seqNo;

    @Column(name = "is_active", nullable = false, updatable = true)
    private Boolean active;

    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

}
