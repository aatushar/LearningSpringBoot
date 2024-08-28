package com.khajana.setting.entity.ordermanagement.importlcinformation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "4J2_import_lc_child")
@AllArgsConstructor
@NoArgsConstructor
public class ImportLcInformationChild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "import_lc_master_id", nullable = false, insertable = true, updatable = true)
    private Long importLcMasterId;

    @Column(name = "item_info_id", nullable = false, insertable = true, updatable = true)
    private Long itemInfoId;

    @Column(name = "rate", nullable = false, insertable = true, updatable = true)
    private Double rate;

    @Column(name = "import_lc_amt", nullable = false, insertable = true, updatable = true)
    private Double importLcAmt;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", insertable = true, updatable = true, nullable = true)
    private Long updatedBy;
}
