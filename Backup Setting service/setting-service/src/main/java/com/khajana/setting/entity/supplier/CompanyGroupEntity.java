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
@Table(name = "3A_company_group")
public class CompanyGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;
    @Column(name = "country_id", nullable = false, insertable = false, updatable = false)
    private Long countryId;

    @Column(name = "bank_id", nullable = false, insertable = false, updatable = false)
    private int bankId;

    @Column(name = "comp_code", nullable = false, updatable = true)
    private String companyCode;

    @Column(name = "comp_type_code", nullable = false, updatable = true)
    private String companyCodeType;

    @Column(name = "comp_type", nullable = false, updatable = true)
    private String companyType;

    @Column(name = "comp_name", nullable = false, updatable = true)
    private String companyName;

    @Column(name = "comp_name_bn", nullable = false, updatable = true)
    private String companyNameBn;

    @Column(name = "reg_person_name", nullable = false, updatable = true)
    private String regPersonName;
    @Column(name = "reg_person_name_bn", nullable = false, updatable = true)
    private String regPersonNameBn;

    @Column(name = "reg_person_nid", nullable = false, updatable = true)
    private String regPersonNid;
    @Column(name = "reg_person_nid_bn", nullable = false, updatable = true)
    private String regPersonNidBn;
    @Column(name = "comp_logo", nullable = false, updatable = true)
    private String companyLogo;
    @Column(name = "comp_icon", nullable = false, updatable = true)
    private String companyIcon;
    @Column(name = "nature_of_biz", nullable = false, updatable = true)
    private String natureOfBiz;

    @Column(name = "biz_activity_code", nullable = false, updatable = true)
    private String bizActCode;
    @Column(name = "biz_activity_code_bn", nullable = false, updatable = true)
    private String bizActCodeBn;

    @Column(name = "biz_activity_name", nullable = false, updatable = true)
    private String bizActivityName;
    @Column(name = "biz_activity_name_bn", nullable = false, updatable = true)
    private String bizActivityNameBn;
    @Column(name = "vat_reg_type", nullable = false, updatable = true)
    private String vatRegType;

    @Column(name = "comp_short_name", nullable = false, updatable = true)
    private String companyShortName;
    @Column(name = "comp_short_name_bn", nullable = false, updatable = true)
    private String companyShortNameBn;
    @Column(name = "comp_address", nullable = false, updatable = true)
    private String companyAdd;
    @Column(name = "comp_address_bn", nullable = false, updatable = true)
    private String companyAddBn;
    @Column(name = "area_code", nullable = false, updatable = true)
    private String areaCode;
    @Column(name = "area_code_bn", nullable = false, updatable = true)
    private String areaCodeBn;
    @Column(name = "custom_office_area", nullable = false, updatable = true)
    private String customerOfficeArea;
    @Column(name = "custom_office_area_bn", nullable = false, updatable = true)
    private String customerOfficeAreaBn;
    @Column(name = "phone_number", nullable = false, updatable = true)
    private String phoneNumber;
    @Column(name = "email_address", nullable = false, updatable = true)
    private String emailAdd;
    @Column(name = "tin_number", nullable = false, updatable = true)
    private String tinNumber;

    @Column(name = "vat_reg_certificate", nullable = false, updatable = true)
    private String vatRegCertificate;
    @Column(name = "vat_reg_certificate_date", nullable = false, updatable = true)
    private Date vatRegCertificateDate;

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
