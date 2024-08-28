package com.khajana.setting.entity.companybranch;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3K4_supplier_contact_info")
public class CompanySupplierContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_id")
    private Long supplierId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanySupplier companySupplier;

    @Column(name = "supplier_code", nullable = true)
    private String supplierCode;

    @Column(name = "display_code", nullable = true)
    private String contactPersonDisplayCode;

    @Column(name = "short_name", nullable = true)
    private String contactPersonShortName;

    @Column(name = "contact_person", length = 50)
    private String contactPerson;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "tin_num", length = 50, nullable = true)
    private String contactPersonTinNum;

    @Column(name = "is_active", nullable = false)
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
