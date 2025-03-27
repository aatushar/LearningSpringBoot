package com.sazzad.erpReport.controller.paymentToSupplierInclServiceController;


import com.sazzad.erpReport.service.paymentToSupplierInclService.PaymentToSupplierInclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class PaymentTOSupplierInclServiceController {

    @Autowired
    private PaymentToSupplierInclService paymentToSupplierInclService;

    @GetMapping("/payment-to-supplier-incl-service")
    public ResponseEntity<byte[]> getPaymentTOSupplierInclService() {
        return paymentToSupplierInclService.generatePaymentToSupplierInclReport();
    }
}
