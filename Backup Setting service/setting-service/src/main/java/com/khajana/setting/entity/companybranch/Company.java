package com.khajana.setting.entity.companybranch;

import com.khajana.setting.entity.address.Country;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "3A_company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_id", insertable = true, updatable = true, nullable = false)
    private Long countryId;

    @Column(name = "currency_id", length = 50, insertable = true, updatable = true, nullable = true)
    private Long currencyId;

    @Column(name = "comp_code", length = 50, insertable = true, updatable = true, nullable = false)
    private String compCode;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comp_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanyTypeEntity companyTypeEntity;

    @Column(name = "comp_type_id", insertable = true, updatable = true, nullable = false)
    private Long compTypeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Country country;

    @Column(name = "comp_business_id", length = 50, insertable = true, updatable = true, nullable = true)
    private String compBusinessId;


    @Column(name = "comp_business_others", length = 50, insertable = true, updatable = true, nullable = true)
    private String compBusinessOthers;

    @Column(name = "comp_name", length = 100, insertable = true, updatable = true, nullable = false)
    private String compName;

    @Column(name = "comp_name_bn", length = 100, insertable = true, updatable = true, nullable = false)
    private String compNameBn;

    @Column(name = "comp_type", length = 100, insertable = true, updatable = true, nullable = true)
    private String compType;

    @Column(name = "reg_person_name", length = 100, insertable = true, updatable = true, nullable = true)
    private String regPersonName;

    @Column(name = "reg_person_name_bn", length = 100, insertable = true, updatable = true, nullable = true)
    private String regPersonNameBn;

    @Column(name = "reg_person_nid", length = 19, insertable = true, updatable = true, nullable = true)
    private String regPersonNid;

    @Column(name = "reg_person_nid_bn", length = 19, insertable = true, updatable = true, nullable = true)
    private String regPersonNidBn;

    @Column(name = "comp_logo", length = 200, insertable = true, updatable = true, nullable = true)
    private String compLogo;

    @Column(name = "comp_icon", length = 200, insertable = true, updatable = true, nullable = true)
    private String compIcon;

    @Column(name = "nature_of_biz", length = 100, insertable = true, updatable = true, nullable = true)
    private String natureOfBiz;

    @Column(name = "comp_short_name", length = 100, insertable = true, updatable = true, nullable = true)
    private String compShortName;

    @Column(name = "comp_short_name_bn", length = 100, insertable = true, updatable = true, nullable = true)
    private String compShortNameBn;

    @Column(name = "comp_address", length = 100, insertable = true, updatable = true, nullable = false)
    private String compAddress;

    @Column(name = "comp_address_bn", length = 100, insertable = true, updatable = true, nullable = true)
    private String compAddressBn;

    @Column(name = "area_code", length = 10, insertable = true, updatable = true, nullable = true)
    private String areaCode;

    @Column(name = "area_code_bn", length = 10, insertable = true, updatable = true, nullable = true)
    private String areaCodeBn;

    @Column(name = "phone_number", length = 15, insertable = true, updatable = true, nullable = true)
    private String phoneNumber;

    @Column(name = "email_address", length = 100, insertable = true, updatable = true, nullable = true)
    private String emailAddress;

    @Column(name = "is_active", insertable = true, updatable = true, nullable = true)
    private Boolean active;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;
}
