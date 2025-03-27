package com.sazzad.erpReport.controller.paymentSupplierInclServiceTwo;


import com.sazzad.erpReport.service.paymentSupplierInclServiceTwo.PaymentSupplierInclServiceTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class PaymentSupplierInclServiceTwoController {

    @Autowired
    private PaymentSupplierInclServiceTwoService paymentSupplierInclServiceTwoService;

    @GetMapping("payment-supplier-incl-service-two-service-report-pdf")
    public ResponseEntity<byte[]> getPaymentSupplierInclServiceTwo() {
        return paymentSupplierInclServiceTwoService.generatePaymentToSupplierInclReportTwo();
    }
}
