package com.khajana.setting.entity.transactiontype;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2C1_sv_tran_source_type")
public class TransactionSourceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "tran_source_type_name", nullable = false, updatable = true)
    private String tranSourceTypeName;

    @Column(name = "tran_source_type_name_bn", nullable = false, updatable = true)
    private String tranSourceTypeNameBN;

    @Column(name = "seq_number", nullable = false, updatable = true)
    private Double seqNumber;

    @Column(name = "is_active", nullable = false, updatable = true)
    private boolean isActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

}
