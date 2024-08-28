package com.khajana.setting.entity.ordermanagement.utilizationdeclaration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "4F1_ud_register")
@AllArgsConstructor
@NoArgsConstructor
public class UtilizationDeclarationMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lc_id", nullable = true, insertable = true, updatable = true)
    private Long lcId;

    @Column(name = "ud_register_no", nullable = true, insertable = true, updatable = true)
    private String udRegisterNo;

    @Column(name = "ud_register_date", nullable = true, insertable = true, updatable = true)
    private Date udRegisterDate;

    @Column(name = "buyer_code", nullable = true, insertable = true, updatable = true)
    private String buyerCode;

    @Column(name = "remarks", nullable = true, insertable = true, updatable = true)
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
