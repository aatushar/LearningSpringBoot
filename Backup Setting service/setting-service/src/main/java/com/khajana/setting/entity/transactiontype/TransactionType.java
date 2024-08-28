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
@Table(name = "2C2_sv_tran_type")
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "tran_source_type_id", nullable = false, insertable = true)
    private Long sourceTypeId;

    // @ManyToOne(optional=false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tran_source_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TransactionSourceType transactionSourceType;

    @Column(name = "tran_type_name", nullable = false, updatable = true, insertable = true)
    private String trnsTypeName;

    @Column(name = "tran_type_name_bn", nullable = false, updatable = true, insertable = true)
    private String trnsTypeNameBn;

    @Column(name = "seq_number", nullable = false, updatable = true, insertable = true)
    private Double seqNumber;

    @Column(name = "is_active", nullable = true, updatable = true, insertable = true)
    private Boolean isActive;

    @Column(name = "created_at", nullable = true, updatable = false, insertable = true)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true, insertable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false, insertable = true)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true, insertable = true)
    private Long updatedBy;

}
