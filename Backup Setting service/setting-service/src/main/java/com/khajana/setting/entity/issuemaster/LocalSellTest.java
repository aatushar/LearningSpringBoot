package com.khajana.setting.entity.issuemaster;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LocalSellTest {
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