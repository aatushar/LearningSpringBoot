package com.khajana.setting.entity.vat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2A1_sv_vat_registration_type")
public class VatRegistrationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "vat_reg_name", nullable = false, updatable = true)
    private String vatRegistrationName;

    @Column(name = "vat_reg_name_bn", nullable = false, updatable = true)
    private String vatRegistrationNameBn;
    @Column(name = "seq_number", nullable = false, updatable = true)
    private Double seqNo;

    @Column(name = "is_req_bin", nullable = true, updatable = true)
    private Boolean reqBin;

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
