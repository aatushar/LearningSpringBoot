package com.khajana.setting.entity.supplier;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3K2_supplier_details")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;
    @Column(name = "company_id", nullable = false, insertable = false, updatable = false)
    private Long companyId;

    @Column(name = "vat_reg_id", nullable = false, insertable = false, updatable = false)
    private Long vatRegId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    /*
     * @JoinTable( name="2J1_sv_fiscal_year_info", joinColumns = {@JoinColumn(name =
     * "id")}, inverseJoinColumns = {@JoinColumn(name = "fy_id")})
     */

    // @JoinColumn(name="fy_id", nullable=true)

    @JoinColumn(name = "company_id")
    private CompanyGroupEntity companyGroupEntity;

//    @ManyToOne (fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @JoinColumn (name = "vat_reg_id")
//    private VatRegistrationType vatRegistrationType;

//    @ManyToOne (fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    /*
//     * @JoinTable( name="2J1_sv_fiscal_year_info", joinColumns = {@JoinColumn(name =
//     * "id")}, inverseJoinColumns = {@JoinColumn(name = "fy_id")})
//     */
//    @JoinColumn (name = "fy_id")
//    // @JoinColumn(name="fy_id", nullable=true)
//    private FiscalYearInfo fiscalYearInfo;

    @Column(name = "supplier_cat_id", nullable = false, updatable = true)
    private String supplierCatId;

    @Column(name = "supplier_name", nullable = false, updatable = true)
    private String supplierName;

    @Column(name = "supplier_name_bn", nullable = false, updatable = true)
    private String supplierNameBn;

    @Column(name = "supplier_bin_number", nullable = false, updatable = true)
    private String supplierBinNumber;
    @Column(name = "supplier_bin_number_bn", nullable = false, updatable = true)
    private String supplierBinNumberBn;
    @Column(name = "registration_status", nullable = false, updatable = true)
    private boolean registrationStatus;
    @Column(name = "email_address", nullable = false, updatable = true)
    private String emailAdd;
    @Column(name = "email_verified_at", nullable = false, updatable = true)
    private Date emailVerifiedAt;
    @Column(name = "country_id", nullable = false, updatable = true)
    private int countryId;

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
