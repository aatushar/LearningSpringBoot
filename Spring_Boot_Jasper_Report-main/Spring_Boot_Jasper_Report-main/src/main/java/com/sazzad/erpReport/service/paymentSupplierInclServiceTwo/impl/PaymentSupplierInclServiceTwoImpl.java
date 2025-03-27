package com.sazzad.erpReport.service.paymentSupplierInclServiceTwo.impl;


import com.sazzad.erpReport.dto.paymentoSupplierInclServiceTwo.PaymentToSupplierInclServiceTwoDto;
import com.sazzad.erpReport.service.paymentSupplierInclServiceTwo.PaymentSupplierInclServiceTwoService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PaymentSupplierInclServiceTwoImpl implements PaymentSupplierInclServiceTwoService {
    public ResponseEntity<byte[]> generatePaymentToSupplierInclReportTwo() {
        try {
            List<PaymentToSupplierInclServiceTwoDto> paymentToSupplierInchData = Arrays.asList(
                    new PaymentToSupplierInclServiceTwoDto(1,1,"15-Mar-23",150000.00,150000.00,""),
                    new PaymentToSupplierInclServiceTwoDto(2,2,"16-Mar-23",150000.00,150000.00,"")
            );


            List<String> emptyListForSecondTable = new ArrayList<>();
            JRBeanCollectionDataSource emptyDataSource = new JRBeanCollectionDataSource(emptyListForSecondTable);


            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/PaymentToSupplierReportTwo.jrxml");
            if (reportStream == null) {
                throw new JRException("Report not found");
            }

            // Compile the Jasper report
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            String currentDateTime = LocalDateTime.now().format(formatter);

            // Pass parameters and data to the report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("testdataset", paymentToSupplierInchData);
            parameters.put("supplierWiseGrn", new JRBeanCollectionDataSource(paymentToSupplierInchData, false));
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("chikleeLogo", "asset/chiklee_logo.png");
            parameters.put("date", "22-08-2023");
            parameters.put("chikleeNumber", "Report #C_D_02A");
            parameters.put("printDate", currentDateTime);
            parameters.put("SupplierName", "S1");
            parameters.put("SupplierNameTwo", "S2");

            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(paymentToSupplierInchData));

            // Export the report to a PDF file
            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            // Set the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=PaymentToSupplierReportTwo.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(pdfData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
