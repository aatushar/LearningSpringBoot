package com.khajana.setting.entity.address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2R7_city")
public class City {

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

    @Column(name = "name_bangla", nullable = false, updatable = true)
    private String nameBn;

    @Column(name = "seq_number", nullable = false, updatable = true)
    private Double seqNo;

    @Column(name = "is_active", nullable = false, updatable = true)
    private Boolean active;

    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    // @Column(name = "deleted_at", nullable = true, updatable = true)
    // private Date deletedAt;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

    @Column(name = "created_by", nullable = true, updatable = true)
    private Long createdBy;

}
