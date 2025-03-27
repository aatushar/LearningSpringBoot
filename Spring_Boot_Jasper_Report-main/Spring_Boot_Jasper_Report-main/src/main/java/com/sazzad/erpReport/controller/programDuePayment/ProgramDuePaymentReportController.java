package com.sazzad.erpReport.controller.programDuePayment;


import com.sazzad.erpReport.service.duePayment.ProgramDuePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settingdev/api/v1/")
public class ProgramDuePaymentReportController {
    @Autowired
    private ProgramDuePaymentService programDuePaymentService;

    @GetMapping("/program-due-payment-report-pdf")
    public ResponseEntity<byte[]> downloadReport() {
        try {
            // Call service method to generate report
            byte[] pdf = programDuePaymentService.generateReport();

            // Set response headers and return the PDF file
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ProgramDuePaymentReport.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}