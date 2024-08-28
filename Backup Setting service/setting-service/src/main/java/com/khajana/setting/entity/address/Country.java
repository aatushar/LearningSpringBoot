package com.khajana.setting.entity.address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2R2_country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable=false, updatable=false)
    private Long id;
    @Column(name ="country_code",nullable = true, updatable = true)
    private String code;

    @Column(name ="country_code_bn", nullable = true, updatable = true)
    private String codeBn;
    
    @Column(name ="name",nullable = false, updatable = true)
    private String name;

    @Column(name ="name_bangla", nullable = false, updatable = true)
    private String nameBn;

    @Column(name ="seq_number", nullable = false, updatable = true)
    private Double seqNo;

    @Column(name ="is_active", nullable = false, updatable = true)
    private boolean active;

    @Column(name ="created_at", nullable = true, updatable = true)
    private Date createdAt;

    @Column(name ="updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name ="created_by", nullable = true, updatable = true)
    private Long createdBy;

    // @Column(name ="updated_by", nullable = true, updatable = true)
    // private Long updatedBy;

}
