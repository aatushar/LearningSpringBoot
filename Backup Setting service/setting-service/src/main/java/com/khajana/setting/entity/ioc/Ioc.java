package com.khajana.setting.entity.ioc;

import com.khajana.setting.entity.companybranch.CompanyBranch;
import com.khajana.setting.entity.item.Item;
import com.khajana.setting.entity.uom.Uom;
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
@Table(name = "3U1_ioc_price_declaration")
public class Ioc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "branch_id", insertable = true, updatable = true, nullable = true)
    private Long branchId;

    @OneToOne(targetEntity = CompanyBranch.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanyBranch companyBranch;

    @Column(name = "prc_decl_name", insertable = true, updatable = true, nullable = false)
    private String prcDeclName;

    @Column(name = "prc_decl_number", insertable = true, updatable = true, nullable = true)
    private String prcDeclNumber;

    @Column(name = "effective_from", insertable = true, updatable = true, nullable = true)
    private Date effectiveFrom;

    @Column(name = "item_info_id", insertable = true, updatable = true, nullable = false)
    private Long itemInfoId;

    @OneToOne(targetEntity = Item.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_info_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Item item;

    @Column(name = "ioc_uom_id", insertable = true, updatable = true, nullable = false)
    private Long iocUomId;

    @OneToOne(targetEntity = Uom.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "ioc_uom_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Uom uom2;

    @OneToOne(targetEntity = Uom.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "ioc_uom_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Uom uom;

    @Column(name = "calculation_qty", insertable = true, updatable = true, nullable = true)
    private Double calculationQty;

    @Column(name = "ioc_qty", insertable = true, updatable = true, nullable = true)
    private Double iocQty;

    @Column(name = "total_rm_cost", insertable = true, updatable = true, nullable = true)
    private Double totalRmCost;

    @Column(name = "total_input_svc_cost", insertable = true, updatable = true, nullable = true)
    private Double totalInputSvcCost;

    @Column(name = "total_value_addition_cost", insertable = true, updatable = true, nullable = true)
    private Double totalValueAdditionCost;

    @Column(name = "grand_total_cost", insertable = true, updatable = true, nullable = true)
    private Double grandTotalCost;

    @Column(name = "remarks", insertable = true, updatable = true, nullable = true)
    private String remarks;

    @Column(name = "date_of_submission", insertable = true, updatable = true, nullable = true)
    private Date dateOfSubmission;

    @Column(name = "approved_by_nbr", insertable = true, updatable = true, nullable = true)
    private Date approvedByNbr;

    @Column(name = "created_at", insertable = true, updatable = false, nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true, nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", insertable = true, updatable = false, nullable = true)
    private Long createdBy;

    @Column(name = "updated_by", insertable = true, updatable = true, nullable = true)
    private Long updatedBy;

    @OneToMany(targetEntity = IocDetails.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "ioc_price_declaration_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<IocDetails> iocDetails;

}
