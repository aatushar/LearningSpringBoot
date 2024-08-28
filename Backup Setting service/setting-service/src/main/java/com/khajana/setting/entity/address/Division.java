package com.khajana.setting.entity.address;

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
@Table(name = "2R3_division")
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "country_id", nullable = false, updatable = true)
    private Long countryId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Country country;

    @Column(name = "name", nullable = false, updatable = true)
    private String name;

    @Column(name = "bn_name", nullable = false, updatable = true)
    private String nameBn;

    @Column(name = "lat", nullable = true, updatable = true)
    private String lat;

    @Column(name = "long", nullable = true, updatable = true)
    private String longitude;

    @Column(name = "seq_number", nullable = true, updatable = true)
    private Double seqNo;

    @Column(name = "is_active", nullable = true, updatable = true)
    private Boolean active;

    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

    @OneToMany(targetEntity = District.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "division_id", referencedColumnName = "id")
    private List<District> districts;

}
