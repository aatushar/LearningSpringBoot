package com.khajana.setting.entity.language;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2S1_languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, updatable = true)
    private String name;

    @Column(name = "code", nullable = false, updatable = true)
    private String code;
    @Column(name = "app_lang_code", nullable = true, updatable = true)
    private String appLangCode;
    @Column(name = "rtl", nullable = false, updatable = true)
    private Integer rtl;
    @Column(name = "is_active", nullable = false, updatable = true)
    private Boolean active;
    @Column(name = "seq_number", nullable = false, updatable = true)
    private Double seqNo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    @Column(name = "updatedAt", nullable = true, updatable = true)
    private Date updatedAt;
    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;
    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

}
