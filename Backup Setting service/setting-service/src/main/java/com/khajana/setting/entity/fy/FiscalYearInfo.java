package com.khajana.setting.entity.fy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2J1_sv_fiscal_year_info")
public class FiscalYearInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "fsc_year_info", nullable = false, updatable = true)
    private String fiscalYear;

    @Column(name = "fsc_year_info_bn", nullable = false, updatable = true)
    private String fiscalYearBn;

    @Column(name = "seq_number", nullable = false, updatable = true)
    private Double sequenceNumber;

    @Column(name = "from_date", nullable = false, updatable = true)
    private Date fromDate;

    @Column(name = "to_date", nullable = false, updatable = true)
    private Date toDate;

    // @Column(name = "fsc_status", nullable = false, updatable = true)
    // private String fiscalStatus;

    @Column(name = "is_active", nullable = false, updatable = true)
    private boolean isActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

}
