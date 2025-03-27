package com.sazzad.erpReport.service.paymentSupplierInclServiceTwo;

import org.springframework.http.ResponseEntity;

public interface PaymentSupplierInclServiceTwoService {
    ResponseEntity<byte[]> generatePaymentToSupplierInclReportTwo();
}
