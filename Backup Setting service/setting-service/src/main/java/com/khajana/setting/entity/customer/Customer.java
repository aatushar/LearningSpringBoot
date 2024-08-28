package com.khajana.setting.entity.customer;

import com.khajana.setting.entity.issue.IssueMaster;
import com.khajana.setting.entity.vat.VatRegistrationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3J2_customer_details")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false, updatable = true)
    private Long companyId;
    @Column(name = "vat_reg_id", nullable = true, updatable = true)
    private Long vatRegId;

    @OneToMany(targetEntity = CustomerAddressDetail.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<CustomerAddressDetail> addresses;

    @OneToMany(targetEntity = CustomerBankDetail.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<CustomerBankDetail> bankDetails;

    @OneToMany(targetEntity = IssueMaster.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<IssueMaster> issueMasters;

    // @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable =
    // false, updatable = false)
    // private CustomerContactInfo contactInfos;

    @OneToOne(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CustomerContactInfo contactInfo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vat_reg_id", referencedColumnName = "id", insertable = false, updatable = false)
    private VatRegistrationType vatReg;

    @Column(name = "customer_type_id", nullable = true, updatable = true)
    private Long customerTypeId;
    @Column(name = "customer_name", nullable = false, updatable = true)
    private String customerName;
    @Column(name = "customer_name_bn", nullable = false, updatable = true)
    private String customerNameBn;
    @Column(name = "customer_bin_number", nullable = true, updatable = true)
    private String customerBinNumber;
    @Column(name = "customer_bin_number_bn", nullable = true, updatable = true)
    private String customerBinNumberBn;
    @Column(name = "registration_status", nullable = false, updatable = true)
    private Boolean registrationStatus;
    @Column(name = "is_active", nullable = false, updatable = true)
    private Boolean active;
    @Column(name = "email_address", nullable = false, updatable = true)
    private String emailAddress;
    @Column(name = "email_verified_at", nullable = true, updatable = true)
    private Date emailVerifiedAt;
    @Column(name = "country_id", nullable = true, updatable = true)
    private Long countryId;

    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;
}
