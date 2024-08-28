package com.khajana.setting.entity.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3J4_customer_contact_info")
public class CustomerContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "customer_code", nullable = true)
    private String customerCode;

    @Column(name = "display_code", nullable = true)
    private String contactPersonDisplayCode;

    @Column(name = "short_name", nullable = true)
    private String contactPersonShortName;

    @Column(name = "contact_person", length = 50)
    private String contactPerson;

    @Column(name = "phone", nullable = true)
    private String contactPersonPhone;

    @Column(name = "tin_num", length = 50, nullable = true)
    private String contactPersonTinNum;

    @Column(name = "is_active", nullable = false)
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
