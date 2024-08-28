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
@Table(name = "2A2_sv_economic_acitivity")
public class EconomyActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "economic_activity_name", nullable = false, updatable = true)
    private String economicActivityName;

    @Column(name = "economic_activity_name_bn", nullable = false, updatable = true)
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
