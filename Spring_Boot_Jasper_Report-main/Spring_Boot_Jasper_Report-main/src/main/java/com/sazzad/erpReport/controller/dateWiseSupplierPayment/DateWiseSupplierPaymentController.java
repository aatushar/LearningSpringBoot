package com.sazzad.erpReport.controller.dateWiseSupplierPayment;


import com.sazzad.erpReport.service.dateWiseSupplierPayment.DateWiseSupplierPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class DateWiseSupplierPaymentController {
    @Autowired
    private DateWiseSupplierPaymentService dateWiseSupplierPaymentService;

    @GetMapping("/date-wise-supplier-payment-report-pdf")
    public ResponseEntity<byte[]> getDateWiseSupplierPaymentPdf() {
        return dateWiseSupplierPaymentService.generateDateWiseSupplierPaymentReport();
    }
}
