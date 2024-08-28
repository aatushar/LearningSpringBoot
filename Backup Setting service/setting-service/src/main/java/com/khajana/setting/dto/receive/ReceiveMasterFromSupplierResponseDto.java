package com.khajana.setting.dto.receive;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveMasterFromSupplierResponseDto {
    private Long receiveMasterId;
    private Long supplierId;
    private String supplierBinNumber;
    private String supplierBinNumberBn;
    private String supplierAccountNumber;
    private String receiveDate;
    private String challanNumber;
    private String challanNumberBn;
    private String challanDate;
    private Double vatAmount;
    private Double recvAmtWithtaxLocalCurr;
    private Double recvAmtWotaxLocalCurr;
}
