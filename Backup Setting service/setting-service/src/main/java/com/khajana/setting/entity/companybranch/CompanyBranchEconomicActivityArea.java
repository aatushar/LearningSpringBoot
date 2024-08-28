package com.khajana.setting.entity.companybranch;

import com.khajana.setting.entity.economyactivity.EconomicActivityAreaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3B4_economic_activity_area")
public class CompanyBranchEconomicActivityArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_id")
    private Long branchId;

    @OneToOne(targetEntity = CompanyBranch.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanyBranch companyBranch;

    @Column(name = "economic_activity_area_id", insertable = true)
    private Long economicActivityAreaId;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "economic_activity_area_id", referencedColumnName = "id", insertable = false, updatable = false)
    private EconomicActivityAreaEntity economicActivityAreaEntity;

    @Column(name = "other_details")
    private String otherDetail;

    @Column(name = "seq_number")
    private Double seqNo;

    @Column(name = "is_active")
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
