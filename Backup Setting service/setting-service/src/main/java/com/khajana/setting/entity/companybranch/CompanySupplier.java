package com.khajana.setting.entity.companybranch;

import com.khajana.setting.entity.receive.ReceiveMaster;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "3K2_supplier_details")
public class CompanySupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", insertable = true, updatable = true, nullable = false)
    private Long companyId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Company company;

    @Column(name = "supplier_type_id", nullable = true, insertable = true, updatable = true)
    private Long supplierTypeId;

    @Column(name = "vat_reg_id", nullable = true, insertable = true, updatable = true)
    private Long vatRegId;

    @Column(name = "supplier_name", insertable = true, updatable = true, nullable = false)
    private String supplierName;

    @Column(name = "supplier_name_bn", insertable = true, updatable = true, nullable = true)
    private String supplierNameBn;

    @Column(name = "supplier_bin_number", insertable = true, updatable = true, nullable = true)
    private String supplierBinNumber;

    @Column(name = "supplier_bin_number_bn", insertable = true, updatable = true, nullable = false)
    private String supplierBinNumberBn;

    @Column(name = "registration_status", insertable = true, updatable = true, nullable = false)
    private Boolean registrationStatus;

    @Column(name = "email_address", insertable = true, updatable = true, nullable = true)
    private String email;

    @Column(name = "email_verified_at", insertable = true, updatable = true, nullable = true)
    private Date emailVerifiedAt;

    @Column(name = "is_active", insertable = true, updatable = true, nullable = false)
    private Boolean active;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", insertable = true, updatable = true, nullable = true)
    private Long updatedBy;

    @OneToMany(targetEntity = CompanySupplierAddressDetail.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<CompanySupplierAddressDetail> addresses;

    @OneToMany(targetEntity = CompanySupplierBankDetail.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<CompanySupplierBankDetail> bankDetails;

    @OneToMany(targetEntity = ReceiveMaster.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<ReceiveMaster> receives;

    @OneToOne(mappedBy = "companySupplier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CompanySupplierContactInfo contactInfo;
}
