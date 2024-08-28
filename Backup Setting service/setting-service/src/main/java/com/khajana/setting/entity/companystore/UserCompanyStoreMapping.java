package com.khajana.setting.entity.companystore;

import com.khajana.setting.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3C6_users_stores")
public class UserCompanyStoreMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", insertable = true, updatable = true)
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name = "store_id", insertable = true, updatable = true)
    private Long storeId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanyStore companyStore;

    // @Column(name = "store_name", insertable = true, updatable = true)
    // private String storeName;

    // @Column(name = "store_name_bn", insertable = true, updatable = true)
    // private String storeNameBn;

    @Column(name = "is_default", insertable = true, updatable = true)
    private Boolean isDefault;
    // @Column(name = "is_active", insertable = true, updatable = true)
    // private Boolean active;

    @Column(name = "created_at", nullable = true, insertable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, insertable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, insertable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, insertable = true, updatable = true)
    private Long updatedBy;
}
