package com.khajana.setting.entity.companybranch;

import com.khajana.setting.entity.address.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3K6_supplier_address_details")
public class CompanySupplierAddressDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_id")
    private Long supplierId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanySupplier companySupplier;

    @Column(name = "address_type_id")
    private Long addressTypeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AddressType addressType;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "postal_code", length = 50)
    private String postalCode;

    @Column(name = "upazilla_id")
    private Long upazilaId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "upazilla_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Upazila upazila;

    @Column(name = "district_id")
    private Long districtId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", referencedColumnName = "id", insertable = false, updatable = false)
    private District district;

    @Column(name = "division_id")
    private Long divisionId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "division_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Division division;

    @Column(name = "country_id")
    private Long countryId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Country country;

    @Column(name = "is_active")
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
