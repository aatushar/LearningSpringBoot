package com.sazzad.erpReport.service.paymentToSupplierInclService.impl;


import com.sazzad.erpReport.dto.paymentToSupplierIncl.PaymentToSupplierInclDto;
import com.sazzad.erpReport.service.paymentToSupplierInclService.PaymentToSupplierInclService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentToSupplierInclServiceImpl implements PaymentToSupplierInclService {
    public ResponseEntity<byte[]> generatePaymentToSupplierInclReport() {
        try {
            List<PaymentToSupplierInclDto> paymentToSupplierInchData = Arrays.asList(
                    new PaymentToSupplierInclDto(1,1,"15-Mar-23",10001,"1-Mar-23", 1000,10000,"")
            );

            // Load the JRXML file from resources
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/PaymentToSupplierReport.jrxml");
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
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("chikleeLogo", "asset/chiklee_logo.png");
            parameters.put("date", "22-08-2023");
            parameters.put("chikleeNumber", "Report #C_D_02A");
            parameters.put("printDate", currentDateTime);
            parameters.put("SupplierName", "S1");
            parameters.put("SupplierNameTwo", "S2");



            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(paymentToSupplierInchData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export the report to a PDF file
            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            // Set the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=PaymentToSupplierReport.pdf");

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