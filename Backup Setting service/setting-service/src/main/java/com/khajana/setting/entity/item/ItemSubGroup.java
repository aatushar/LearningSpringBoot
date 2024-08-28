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
@Table(name = "3N03_item_sub_group")
public class ItemSubGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "itm_grp_id", insertable = true, updatable = true, nullable = false)
    private Long itmGrpId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id", referencedColumnName = "itm_grp_id", insertable = false, updatable = false)
    private ItemGroup itemGroup;

    @Column(name = "itm_sub_grp_name", insertable = true, updatable = true, nullable = false)
    private String itmSubGrpName;

    @Column(name = "itm_sub_grp_name_bn", insertable = true, updatable = true, nullable = true)
    private String itmSubGrpNameBn;

    @Column(name = "itm_sub_grp_prefix", insertable = true, updatable = true, nullable = true)
    private String itmSubGrpPrefix;

    @Column(name = "inv_method_id", insertable = true, updatable = true, nullable = true)
    private Long invMethodId;

    @Column(name = "seq_number", nullable = true, updatable = true)
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id", referencedColumnName = "itm_sub_grp_id", insertable = false, updatable = false)
    private ItemSubGroupHsCodeMapping itemSubGroupHsCodeMappings;

}
