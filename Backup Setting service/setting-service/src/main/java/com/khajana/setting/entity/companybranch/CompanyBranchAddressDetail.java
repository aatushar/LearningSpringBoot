package com.khajana.setting.entity.companybranch;

import com.khajana.setting.entity.address.AddressType;
import com.khajana.setting.entity.address.District;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3B6_branch_address_details")
public class CompanyBranchAddressDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_id")
    private Long branchId;

    @OneToOne(targetEntity = CompanyBranch.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanyBranch companyBranch;

    @Column(name = "type_of_address_id")
    private Long addressTypeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "type_of_address_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AddressType addressType;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "postal_code", length = 50)
    private Long postalCode;

    @Column(name = "upozilla_id")
    private Long upazillaId;

    @Column(name = "division_id")
    private Long divisionId;

    @Column(name = "district_id")
    private Long districtId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", referencedColumnName = "id", insertable = false, updatable = false)
    private District district;

    @Column(name = "is_active", nullable = true, updatable = false)
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
