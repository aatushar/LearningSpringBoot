package com.khajana.setting.entity.item;

import com.khajana.setting.entity.hscode.HsCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3N04_item_sub_grp_hs_code_mapping")
public class ItemSubGroupHsCodeMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "itm_sub_grp_id", nullable = false) // Adjust nullability based on image
    private Long itmSubGrpId;

    @Column(name = "hs_code_id", nullable = false) // Adjust nullability based on image
    private Long hsCodeId;

    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

    @OneToOne(targetEntity = HsCode.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "hs_code_id", referencedColumnName = "id", insertable = false, updatable = false)
    private HsCode hsCode;

}
