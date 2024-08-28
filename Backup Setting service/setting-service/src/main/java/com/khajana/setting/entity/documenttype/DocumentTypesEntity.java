package com.khajana.setting.entity.documenttype;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2R9_document_type")
public class DocumentTypesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doc_type", nullable = false, updatable = true)
    private String docType;

    @Column(name = "doc_type_bn", nullable = false, updatable = true)
    private String docTypeBn;

    @Column(name = "seq_number", nullable = false, updatable = true)
    private Double seqNo;
    @Column(name = "is_active", nullable = false, updatable = true)
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
