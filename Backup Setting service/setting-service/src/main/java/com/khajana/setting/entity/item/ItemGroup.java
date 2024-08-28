package com.khajana.setting.entity.item;

import com.khajana.setting.entity.uom.UomSet;
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
@Table(name = "3N02_item_group")
public class ItemGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "itm_mstr_grp_id", insertable = true, updatable = true, nullable = false)
    private Long itmMstrGrpId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "itm_mstr_grp_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ItemMasterGroup itemMasterGroup;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "itm_grp_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<ItemSubGroup> itemSubGroup;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "uom_set_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UomSet uomSet;

    @Column(name = "itm_grp_prefix", insertable = true, updatable = true, nullable = true)
    private String itmGrpPrefix;

    @Column(name = "uom_set_id", insertable = true, updatable = true, nullable = false)
    private Long uomSetId;

    @Column(name = "itm_grp_name", insertable = true, updatable = true, nullable = false)
    private String itmGrpName;

    @Column(name = "itm_grp_name_bn", insertable = true, updatable = true, nullable = true)
    private String itmGrpNameBn;

    @Column(name = "item_grp_des", insertable = true, updatable = true, nullable = true)
    private String itemGrpDes;

    @Column(name = "item_grp_des_bn", insertable = true, updatable = true, nullable = true)
    private String itemGrpDesBn;

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
