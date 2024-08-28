package com.khajana.setting.entity.uom;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2F2_sv_uom")
public class Uom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Long id;

    @Column(name = "uom_set_id")
    private Long uomSetId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "uom_set_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UomSet uomSet;

    @Column(name = "uom_short_code", length = 20)
    private String uomShortCode;

    @Column(name = "uom_desc", length = 50)
    private String uomDesc;

    @Column(name = "local_desc", length = 50)
    private String localDesc;

    @Column(name = "relative_factor")
    private Double relativeFactor;

    @Column(name = "fraction_allow", length = 50)
    private Boolean fractionAllow;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;
}
