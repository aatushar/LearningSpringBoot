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
@Table(name = "2C3_sv_tran_sub_type")
public class TransactionSubType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "tran_type_id", nullable = false)
    private Long trnsTypeId;

    // @ManyToOne(optional=true)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tran_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TransactionType transactionType;

    @Column(name = "tran_sub_type_name", nullable = false, updatable = true)
    private String trnsSubTypeName;

    @Column(name = "tran_sub_type_name_bn", nullable = false, updatable = true)
    private String trnsSubTypeNameBn;

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
