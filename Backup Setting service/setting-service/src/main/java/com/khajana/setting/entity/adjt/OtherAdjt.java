package com.khajana.setting.entity.adjt;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2Q1_sv_others_adjt")
public class OtherAdjt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;
    @Column(name = "others_adjt_name", nullable = false, updatable = true)
    private String name;

    @Column(name = "others_adjt_name_bn", nullable = false, updatable = true)
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
