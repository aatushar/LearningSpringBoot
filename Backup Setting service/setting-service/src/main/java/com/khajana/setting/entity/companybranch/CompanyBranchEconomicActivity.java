package com.khajana.setting.entity.companybranch;


import com.khajana.setting.entity.economyactivity.EconomyActivity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "3B3_economic_activity")
public class CompanyBranchEconomicActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_id", insertable = true, updatable = true)
    private Long branchId;

    @OneToOne(targetEntity = CompanyBranch.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanyBranch companyBranch;

    @Column(name = "economic_activity_id", insertable = true, updatable = true)
    private Long economicActivityId;

    @OneToOne(targetEntity = EconomyActivity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "economic_activity_id", referencedColumnName = "id", insertable = false, updatable = false)
    private EconomyActivity economyActivity;

    @Column(name = "supporting_doc_no", insertable = true, updatable = true)
    private String supportingDocNo;

    @Column(name = "supporting_doc_issue_date", insertable = true, updatable = true)
    private Date supportingDocIssueDate;

    @Column(name = "seq_number", insertable = true, updatable = true)
    private Double seqNo;

    @Column(name = "is_active", insertable = true, updatable = true)
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
