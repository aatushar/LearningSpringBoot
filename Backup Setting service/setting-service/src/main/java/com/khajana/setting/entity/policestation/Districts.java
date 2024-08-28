package com.khajana.setting.entity.policestation;

import com.khajana.setting.entity.address.Division;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2R4_district")
public class Districts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "division_id", nullable = false, updatable = true)
    private Long divisionId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "division_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Division division;

    @Column(name = "name", nullable = true, updatable = true)
    private String name;

    @Column(name = "name_bangla", nullable = true, updatable = true)
    private String nameBn;

    @Column(name = "lat", nullable = true, updatable = true)
    private Double lat;

    @Column(name = "long", nullable = true, updatable = true)
    private Double longitude;

    @Column(name = "seq_number", nullable = false, updatable = true)
    private Double seqNo;

    @Column(name = "is_active", nullable = false, updatable = true)
    private int active;

    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = true)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

}
