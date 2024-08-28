package com.khajana.setting.entity.ordermanagement.exportlcinformation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "4E3_export_lc_Item_child")
@AllArgsConstructor
@NoArgsConstructor
public class ExportLcInformationChild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lc_id", nullable = false, insertable = true, updatable = true)
    private Long lcId;

    @Column(name = "item_info_id", nullable = false, insertable = true, updatable = true)
    private Long itemInfoId;

    @Column(name = "qty", nullable = false, insertable = true, updatable = true)
    private Double qty;

    @Column(name = "rate", nullable = false, insertable = true, updatable = true)
    private Double rate;

    @Column(name = "amount", nullable = false, insertable = true, updatable = true)
    private Double amount;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", insertable = true, updatable = true, nullable = true)
    private Long updatedBy;
}
