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
@Table(name = "3H1_employee_master")
public class CompanyEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_id", insertable = true, updatable = true, nullable = false)
    private Long departmentId;

    @Column(name = "section_id", insertable = true, updatable = true, nullable = false)
    private Long sectionId;

    @Column(name = "designation_id", insertable = true, updatable = true, nullable = false)
    private Long designationId;

    @Column(name = "code", insertable = true, updatable = true, nullable = false)
    private String code;

    @Column(name = "code_bn", insertable = true, updatable = true, nullable = false)
    private String codeBn;

    @Column(name = "name", insertable = true, updatable = true, nullable = false)
    private String name;

    @Column(name = "name_bn", insertable = true, updatable = true, nullable = false)
    private String nameBn;

    @Column(name = "type", insertable = true, updatable = true, nullable = false)
    private String type;

    @Column(name = "nid", insertable = true, updatable = true, nullable = false)
    private String nid;

    @Column(name = "nid_doc", insertable = true, updatable = true, nullable = false)
    private String nidDoc;

    @Column(name = "address", insertable = true, updatable = true, nullable = false)
    private String address;

    @Column(name = "address_bn", insertable = true, updatable = true, nullable = false)
    private String addressBn;

    @Column(name = "email", insertable = true, updatable = true, nullable = false)
    private String email;

    @Column(name = "photo", insertable = true, updatable = true, nullable = false)
    private String photo;

    @Column(name = "phone", insertable = true, updatable = true, nullable = false)
    private String phone;

    @Column(name = "is_active", insertable = true, updatable = true, nullable = false)
    private Boolean active;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", insertable = false, updatable = true, nullable = false)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = false)
    private Long createdBy;

    @Column(name = "updated_by", insertable = false, updatable = true, nullable = false)
    private Long updatedBy;
}
