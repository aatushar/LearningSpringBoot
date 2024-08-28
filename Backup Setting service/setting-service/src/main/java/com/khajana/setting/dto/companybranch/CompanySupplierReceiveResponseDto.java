package com.khajana.setting.dto.companybranch;


import com.khajana.setting.dto.receive.ReceiveMasterFromSupplierResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanySupplierReceiveResponseDto {
    private Long id;
    private String supplierName;
    private String supplierNameBn;
    private String supplierBinNumber;
    private String supplierBinNumberBn;


    private List<ReceiveMasterFromSupplierResponseDto> receives;
}
