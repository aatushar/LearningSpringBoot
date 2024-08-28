package com.khajana.setting.entity.hscode;

import com.khajana.setting.entity.product.ProductCategory;
import com.khajana.setting.entity.vat.VatStructure;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2E1_sv_hs_code")
public class HsCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hs_code_chapter", nullable = true, updatable = true, insertable = true)
    private String hsCodeChapter;

    @Column(name = "hs_code_heading", nullable = true, updatable = true, insertable = true)
    private String hsCodeHeading;

    @Column(name = "hs_code_section", nullable = true, updatable = true, insertable = true)
    private String hsCodeSection;

    @Column(name = "prod_cat_id", nullable = true, updatable = true, insertable = true)
    private Long productCategoryId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "prod_cat_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductCategory productCategory;

    @OneToMany(targetEntity = VatStructure.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "hs_code_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<VatStructure> vatStructures;

    @Column(name = "hs_code", nullable = true, updatable = true, insertable = true)
    private String hsCode;

    @Column(name = "description", nullable = true, updatable = true, insertable = true)
    private String description;

    @Column(name = "description_bn", nullable = true, updatable = true, insertable = true)
    private String descriptionBn;

    @Column(name = "seq_number", nullable = true, updatable = true, insertable = true)
    private Double seqNo;

    @Column(name = "is_active", nullable = true, updatable = true, insertable = true)
    private Boolean active;

    @Column(name = "created_at", nullable = true, updatable = false, insertable = true)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true, insertable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false, insertable = true)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true, insertable = true)
    private Long updatedBy;

}
