package com.khajana.setting.entity.item;

import com.khajana.setting.entity.companybranch.CompanyBranch;
import com.khajana.setting.entity.companystore.CompanyStore;
import com.khajana.setting.entity.product.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3N09_item_store_mapping")
public class ItemStoreMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "item_info_id", insertable = true, updatable = true, nullable = false)
    private Long itemInfoId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id", referencedColumnName = "item_info_id", insertable = false, updatable = false)
    private Item item;

    @Column(name = "prod_type_id", insertable = true, updatable = true, nullable = false)
    private Long prodTypeId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id", referencedColumnName = "prod_type_id", insertable = false, updatable = false)
    private ProductType productType;

    @Column(name = "itm_sub_grp_id", insertable = true, updatable = true, nullable = false)
    private Long itmSubGrpId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id", referencedColumnName = "itm_sub_grp_id", insertable = false, updatable = false)
    private ItemSubGroup itemSubGroup;

    @Column(name = "branch_id", insertable = true, updatable = true, nullable = false)
    private Long branchId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id", referencedColumnName = "branch_id", insertable = false, updatable = false)
    private CompanyBranch companyBranch;

    @Column(name = "store_id", insertable = true, updatable = true, nullable = false)
    private Long storeId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id", referencedColumnName = "store_id", insertable = false, updatable = false)
    private CompanyStore companyStore;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", insertable = true, updatable = true, nullable = true)
    private Long updatedBy;

}
