package com.khajana.setting.entity.vat;

import com.khajana.setting.entity.fy.FiscalYearInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2J2_sv_vat_month_info")
public class VatMonthInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "fy_id", nullable = false)
    private Long fyId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fy_id", referencedColumnName = "id", insertable = false, updatable = false)
    private FiscalYearInfo fiscalYearInfo;

    @Column(name = "vm_info", nullable = false, updatable = true)
    private String vmInfo;

    @Column(name = "vm_info_bn", nullable = false, updatable = true)
    private String vmInfoBn;

    @Column(name = "seq_number", nullable = false, updatable = true)
    private Double sequenceNumber;

    @Column(name = "from_date", nullable = false, updatable = true)
    private Date fromDate;

    @Column(name = "to_date", nullable = false, updatable = true)
    private Date toDate;

    @Column(name = "vm_status", nullable = false, updatable = true)
    private Boolean vmStatus;

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
