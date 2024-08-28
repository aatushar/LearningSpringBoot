package com.khajana.setting.entity.vat;

import com.khajana.setting.entity.transactiontype.TransactionSubType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2C4_sv_vat_payment_method")
public class VatPaymentMethodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "tran_sub_type_id", nullable = false)
    private Long tranSubTypeId;

    // @ManyToOne(optional=false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tran_sub_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TransactionSubType transactionSubType;

    @Column(name = "vat_payment_method_name", nullable = false, updatable = true)
    private String vatPaymentMethodName;

    @Column(name = "vat_payment_method_name_bn", nullable = false, updatable = true)
    private String vatPaymentMethodNameBn;

    @Column(name = "seq_number", nullable = false, updatable = true)
    private Double seqNo;

    @Column(name = "is_active", nullable = false, updatable = true)
    private boolean isActive;

    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;
}
