package com.khajana.setting.entity.uom;

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
@Table(name = "2F1_sv_uom_set")
public class UomSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Long id;

    @OneToMany(targetEntity = Uom.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "uom_set_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<Uom> uoms;

    @Column(name = "uom_set", nullable = false)
    private String uomSet;

    @Column(name = "uom_set_desc", nullable = false)
    private String uomSetDesc;

    @Column(name = "local_uom_set_desc", nullable = false)
    private String localUomSetDesc;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true)
    private Long updatedBy;

}
