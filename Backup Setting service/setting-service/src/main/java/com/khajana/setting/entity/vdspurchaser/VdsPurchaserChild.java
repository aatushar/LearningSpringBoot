package com.khajana.setting.entity.vdspurchaser;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "6D2_vds_purchaser_child")
public class VdsPurchaserChild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "vds_purchaser_master_id")
    private Long vdsPurchaserMasterId;

    @Column(name = "receive_master_id")
    private Long receiveMasterId;

    @Column(name = "recv_amt_wotax_local_curr")
    private Double recvAmtWotaxLocalCurr;

    @Column(name = "vat_amount")
    private Double vatAmount;

    @Column(name = "deducted_vat_amount")
    private Double deductedVatAmount;

    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

}
