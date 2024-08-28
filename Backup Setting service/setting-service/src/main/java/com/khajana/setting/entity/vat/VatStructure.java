package com.khajana.setting.entity.vat;

import com.khajana.setting.entity.hscode.HsCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2E2_sv_vat_structure")
public class VatStructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hs_code_id")
    private Long hsCodeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hs_code_id", referencedColumnName = "id", insertable = false, updatable = false)
    private HsCode hsCode;

    @Column(name = "vat_rate_ref_id")
    private Long vatRateRefId;

    @Column(name = "tran_sub_type_id")
    private Long tranSubTypeId;

    @Column(name = "vat_rate_type_id")
    private Long vatRateTypeId;

    @Column(name = "prod_type_id")
    private Long prodTypeId;

    @Column(name = "fiscal_year_id")
    private Long fiscalYearId;

    @Column(name = "effective_date")
    private Date effectiveDate;

    @Column(name = "uom_id")
    private Long uomId;

    @Column(name = "cd")
    private Double cd;

    @Column(name = "sd")
    private Double sd;

    @Column(name = "vat")
    private Double vat;

    @Column(name = "at")
    private Double at;

    @Column(name = "ait")
    private Double ait;

    @Column(name = "rd")
    private Double rd;

    @Column(name = "atv")
    private Double atv;

    @Column(name = "exd")
    private Double exd;

    @Column(name = "tti")
    private Double tti;

    @Column(name = "is_fixed_rate")
    private Boolean isFixedRate;

    @Column(name = "fixed_rate_uom_id")
    private Long fixedRateUomId;

    @Column(name = "fixed_rate")
    private Double fixedRate;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;
}
