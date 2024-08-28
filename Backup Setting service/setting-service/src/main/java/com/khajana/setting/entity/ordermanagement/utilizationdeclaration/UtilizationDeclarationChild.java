package com.khajana.setting.entity.ordermanagement.utilizationdeclaration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "4F2_ud_register_child_bblc")
@AllArgsConstructor
@NoArgsConstructor
public class UtilizationDeclarationChild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ud_register_id", nullable = true, insertable = true, updatable = true)
    private Long udRegisterId;

    @Column(name = "bblc_id", nullable = true, insertable = true, updatable = true)
    private Long bblcId;

    @Column(name = "remarks", nullable = true, insertable = true, updatable = true)
    private String remarks;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", insertable = true, updatable = true, nullable = true)
    private Long updatedBy;
}
