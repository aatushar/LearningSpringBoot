package com.sazzad.erpReport.service.paymentToSupplierInclService;

import org.springframework.http.ResponseEntity;

public interface PaymentToSupplierInclService {
    ResponseEntity<byte[]> generatePaymentToSupplierInclReport();
}
