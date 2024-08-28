package com.khajana.setting.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchWiseStore {
    private int id;

    @Column(name = "company_id")
    private int companyId;
    private int branchId;
    @Column(name = "b_u_name")
    @JsonSerialize()
    private String branchUniteNameEN;
    private String branchUniteNameBN;
    private String storeLocationCode;
    private String storeLocationNameEN;
    private String storeLocationNameBN;
    private String storeLocationAddress;
    private String storeLocationType;
}
