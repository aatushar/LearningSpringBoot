package com.khajana.setting.entity.issuemaster;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "GetAllSellIndexData")
public class SpEntity {

    ////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    private Long id;
//    @Column(name ="issue_number",nullable = false, updatable = true)
//    private String issueDeliveryNo;
//    @Column(name ="issue_number_bn",nullable = false, updatable = true)
//    private String issueDeliveryNoBn;
//    @Column(name ="issue_date",nullable = false, updatable = true)
//    private Date issueDate;
//    @Column(name ="challan_number",nullable = true, updatable = true)
//    private String challanNumber;
//    @Column(name ="challan_number_bn",nullable = true, updatable = true)
//    private String challanNumberBn;
//    @Column(name ="challan_date",nullable = false, updatable = true)
//    private Date cahallanDate;
//
//    private int customerCatId;
//    @Column(name ="customer_name",nullable = false, updatable = true)
//    private String customerName;
//    @Column(name ="customer_name_bn",nullable = false, updatable = true)
//    private String customerNameBn;
//    @Column(name ="customer_bin_number",nullable = true, updatable = true)
//    private String customerBinNumber;
//    @Column(name ="customer_bin_number_bn",nullable = true, updatable = true)
//    private String customerBinNumberBn;
//    @Column(name ="remarks",nullable = true, updatable = true)
//    private String remarks;
//    @Column(name ="remarks_bn",nullable = true, updatable = true)
//    private String remarksBn;
    @Id
    private Long id;
    private String issueDeliveryNo;
    private String issueDeliveryNoBn;
    private Date issueDate;
    private String challanNumber;
    private String challanNumberBn;
    private Date cahallanDate;

    private int customerCatId;
    private String customerName;
    private String customerNameBn;
    private String customerBinNumber;
    private String customerBinNumberBn;
    private String remarks;
    private String remarksBn;
}
