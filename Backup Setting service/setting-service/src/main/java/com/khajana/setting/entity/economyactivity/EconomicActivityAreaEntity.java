package com.khajana.setting.entity.economyactivity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2A4_area_of_economic_acitivity")
public class EconomicActivityAreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "economic_activity_id", nullable = false, updatable = true)
    private Long economicActivityId;

    @Column(name = "economic_activity_area_name", nullable = false, updatable = true)
    private String economicActivityName;
    @Column(name = "economic_activity_area_name_bn", nullable = false, updatable = true)
    private String economicActivityNameBn;

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
