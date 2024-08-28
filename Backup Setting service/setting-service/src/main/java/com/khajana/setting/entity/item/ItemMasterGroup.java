package com.khajana.setting.entity.item;

import com.khajana.setting.entity.product.ProductType;
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
@Table(name = "3N01_item_master_group")
public class ItemMasterGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "prod_type_id", insertable = true, updatable = true, nullable = true)
    private Long prodTypeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "prod_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductType productType;

    @OneToMany(targetEntity = ItemGroup.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "itm_mstr_grp_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<ItemGroup> itemGroups;

    @Column(name = "itm_mstr_grp_name", insertable = true, updatable = true, nullable = false)
    private String itmMstrGrpName;

    @Column(name = "itm_mstr_grp_name_bn", insertable = true, updatable = true, nullable = true)
    private String itmMstrGrpNameBn;

    @Column(name = "itm_mstr_grp_prefix", insertable = true, updatable = true, nullable = true)
    private String itmMstrGrpPrefix;

    @Column(name = "item_mstr_grp_des", insertable = true, updatable = true, nullable = true)
    private String itemMstrGrpDes;

    @Column(name = "item_mstr_grp_des_bn", insertable = true, updatable = true, nullable = true)
    private String itemMstrGrpDesBn;

    @Column(name = "seq_number", insertable = true, updatable = true, nullable = true)
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
