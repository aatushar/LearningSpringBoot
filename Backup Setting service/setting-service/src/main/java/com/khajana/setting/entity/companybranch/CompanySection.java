package com.khajana.setting.entity.companybranch;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "3F1_section_master")
public class CompanySection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", insertable = true, updatable = true, nullable = false)
    private Long companyId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanyDepartment companyDepartment;

    @Column(name = "department_id", nullable = false, insertable = true, updatable = true)
    private Long departmentId;

    @Column(name = "sec_name", nullable = false, insertable = true, updatable = true)
    private String secName;

    @Column(name = "sec_name_bn", insertable = true, updatable = true, nullable = true)
    private String secNameBn;

    @Column(name = "seq_number", insertable = true, updatable = true, nullable = false)
    private Double seqNo;

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
}
