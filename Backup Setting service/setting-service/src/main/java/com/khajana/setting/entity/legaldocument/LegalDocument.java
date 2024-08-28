package com.khajana.setting.entity.legaldocument;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "3R1_Legal_documents")
@AllArgsConstructor
@NoArgsConstructor
public class LegalDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", insertable = true, updatable = true, nullable = true)
    private Long companyId;

    @Column(name = "branch_id", insertable = true, updatable = true, nullable = true)
    private Long branchId;

    @Column(name = "doc_type_id", insertable = true, updatable = true, nullable = true)
    private Long docTypeId;

    @Column(name = "documents_no")
    private String documentNo;

    @Column(name = "document_name", insertable = true, updatable = true, nullable = false)
    private String documentName;

    @Column(name = "valid_till", insertable = true, updatable = true, nullable = false)
    private Date validTill;

    @Column(name = "upload_date", insertable = true, updatable = true, nullable = false)
    private Date uploadDate;

    @Column(name = "attachements", insertable = true, updatable = true, nullable = true)
    private String attachments;

    @Column(name = "remarks", insertable = true, updatable = true, nullable = true)
    private String remarks;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", insertable = true, updatable = true, nullable = true)
    private Long updatedBy;
}
