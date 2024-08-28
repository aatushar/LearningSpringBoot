package com.khajana.setting.entity.ordermanagement.exportlcinformation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Entity
@Data
@Table(name = "4E2_export_lc")
@AllArgsConstructor
@NoArgsConstructor
public class ExportLcInformationMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lc_no", nullable = false, insertable = true, updatable = true)
    private String lcNo;

    @Column(name = "lc_date", nullable = false, insertable = true, updatable = true)
    private Date lcDate;

    @Column(name = "group_code", nullable = true, insertable = true, updatable = true)
    private String groupCode;

    @Column(name = "customer_id", nullable = false, insertable = true, updatable = true)
    private Long customerId;

    @Column(name = "company_id", nullable = true, insertable = true, updatable = true)
    private Long companyId;

    @Column(name = "open_bank_id", nullable = false, insertable = true, updatable = true)
    private Long openBankId;

    @Column(name = "lien_bank_id", nullable = false, insertable = true, updatable = true)
    private Long lienBankId;

    @Column(name = "lien_date", nullable = false, insertable = true, updatable = true)
    private Date lienDate;

    @Column(name = "currency_id", nullable = false, insertable = true, updatable = true)
    private Long currencyId;

    @Column(name = "lc_amt", nullable = false, insertable = true, updatable = true)
    private Double lcAmt;

    @Column(name = "ship_date", nullable = false, insertable = true, updatable = true)
    private Date shipDate;

    @Column(name = "exp_date", nullable = false, insertable = true, updatable = true)
    private Date expDate;

    @Column(name = "Remarks", nullable = false, insertable = true, updatable = true)
    private String remarks;

    @Column(name = "lc_for_id", nullable = false, insertable = true, updatable = true)
    private Long lcForId;

    @Column(name = "maximport_limit", nullable = false, insertable = true, updatable = true)
    private Double maximportLimit;

    @Column(name = "is_closed", nullable = false, insertable = true, updatable = true)
    private Boolean isClosed;


    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", insertable = true, updatable = true, nullable = true)
    private Long updatedBy;


    @OneToMany(targetEntity = ExportLcInformationChild.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "lc_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<ExportLcInformationChild> items;
}
