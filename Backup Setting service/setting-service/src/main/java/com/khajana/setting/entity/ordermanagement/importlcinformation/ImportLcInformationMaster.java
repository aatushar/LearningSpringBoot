package com.khajana.setting.entity.ordermanagement.importlcinformation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Entity
@Data
@Table(name = "4J1_import_lc_master")
@AllArgsConstructor
@NoArgsConstructor
public class ImportLcInformationMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false, insertable = true, updatable = true)
    private Long companyId;

    @Column(name = "supplier_id", nullable = false, insertable = true, updatable = true)
    private Long supplierId;

    @Column(name = "is_bblc", nullable = false, insertable = true, updatable = true)
    private Boolean isBblc;

    @Column(name = "export_lc_id", nullable = false, insertable = true, updatable = true)
    private Long exportLcId;

    @Column(name = "import_lc_no", nullable = false, insertable = true, updatable = true)
    private String importLcNo;
    @Column(name = "remarks", nullable = false, insertable = true, updatable = true)
    private String remarks;

    @Column(name = "import_lc_date", nullable = false, insertable = true, updatable = true)
    private Date importLcDate;

    @Column(name = "opening_bank_id", nullable = false, insertable = true, updatable = true)
    private Long openingBankId;

    // @Column(name = "import_lc_type_id", nullable = false, insertable = true, updatable = true)
    // private Long importLcTypeId;

    @Column(name = "currency_id", nullable = false, insertable = true, updatable = true)
    private Long currencyId;

    @Column(name = "import_lc_amt", nullable = false, insertable = true, updatable = true)
    private Double importLcAmt;

    @Column(name = "expiry_date", nullable = false, insertable = true, updatable = true)
    private Date expiryDate;

    @Column(name = "tolarance", nullable = false, insertable = true, updatable = true)
    private Double tolarance;

    @Column(name = "sourcing_type_id", nullable = false, insertable = true, updatable = true)
    private Long sourcingTypeId;

    @Column(name = "purpose_id", nullable = false, insertable = true, updatable = true)
    private Long purposeId;

    @Column(name = "advising_bank", nullable = false, insertable = true, updatable = true)
    private Long advisingBank;

    @Column(name = "application_date", nullable = false, insertable = true, updatable = true)
    private Date applicationDate;

    @Column(name = "is_applied", nullable = false, insertable = true, updatable = true)
    private Boolean isApplied;

    @Column(name = "ud_register_id", nullable = false, insertable = true, updatable = true)
    private Long udRegisterId;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", insertable = true, updatable = true, nullable = true)
    private Long updatedBy;
    @OneToMany(targetEntity = ImportLcInformationChild.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "import_lc_master_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<ImportLcInformationChild> items;
}
