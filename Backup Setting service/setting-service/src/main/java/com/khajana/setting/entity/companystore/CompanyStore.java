package com.khajana.setting.entity.companystore;

import com.khajana.setting.entity.companybranch.CompanyBranch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3C1_company_store_location")
public class CompanyStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_id", insertable = true, updatable = true, nullable = false)
    private Long branchId;

    @OneToOne(targetEntity = CompanyBranch.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanyBranch companyBranch;

    @Column(name = "sl_code", insertable = true, updatable = true, nullable = false)
    private String slCode;

    @Column(name = "sl_name", insertable = true, updatable = true, nullable = true)
    private String slName;

    @Column(name = "sl_name_bn", insertable = true, updatable = true, nullable = true)
    private String slNameBn;

    @Column(name = "sl_ShortName", insertable = true, updatable = true, nullable = true)
    private String slShortName;

    @Column(name = "sl_ShortName_bn", insertable = true, updatable = true, nullable = true)
    private String slShortNameBn;

    @Column(name = "sl_address", insertable = true, updatable = true, nullable = true)
    private String slAddress;

    @Column(name = "sl_address_bn", insertable = true, updatable = true, nullable = true)
    private String slAddressBn;

    @Column(name = "sl_officer_id", insertable = true, updatable = true, nullable = true)
    private Long slOfficerId;

    @Column(name = "sl_type", insertable = true, updatable = true, nullable = true)
    private Long slType;

    @Column(name = "is_default_location", insertable = true, updatable = true, nullable = true)
    private Boolean isDefaultLocation;

    @Column(name = "is_sales_point", insertable = true, updatable = true, nullable = true)
    private Boolean isSalesPoint;

    @Column(name = "is_virtual_location", insertable = true, updatable = true, nullable = true)
    private Boolean isVirtualLocation;

    @Column(name = "ticket_cat_id", insertable = true, updatable = true, nullable = true)
    private Long ticketCatId;

    @Column(name = "is_active", insertable = true, updatable = true, nullable = true)
    private Boolean active;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", insertable = true, updatable = true, nullable = true)
    private Long updatedBy;
}
