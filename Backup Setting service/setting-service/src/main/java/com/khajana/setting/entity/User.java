package com.khajana.setting.entity;

import com.khajana.setting.entity.companystore.UserCompanyStoreMapping;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "1a3_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String created_at;

    /*
     * Mapping One user Has many stores
     */

    @OneToMany(targetEntity = UserCompanyStoreMapping.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<UserCompanyStoreMapping> userCompanyStoreMapping;
}
