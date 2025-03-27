package com.sazzad.erpReport.service.dateWiseSupplierPayment.impl;


import com.sazzad.erpReport.dto.dateWiseSupplierPayment.DateWiseSupplierPaymentDto;
import com.sazzad.erpReport.service.dateWiseSupplierPayment.DateWiseSupplierPaymentService;
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
public class DateWiseSupplierPaymentServiceImpl implements DateWiseSupplierPaymentService {

    @Override
    public ResponseEntity<byte[]> generateDateWiseSupplierPaymentReport() {
        try {
            List<DateWiseSupplierPaymentDto> dateWiseSupplierPaymentData = Arrays.asList(
                    new DateWiseSupplierPaymentDto(1L, 1L, "Amin Khan", 1200.00, 1400.00, ""),
                    new DateWiseSupplierPaymentDto(1L, 1L, "Monir Khan", 300.00, 4300.00, ""),
                    new DateWiseSupplierPaymentDto(2L, 2L, "Amin Khan", 700.00, 3000.00, "")
            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/DateWiseSupplierPayment.jrxml");
            if (reportStream == null) {
                throw new JRException("Report file not found");
            }

            // Compile JRXML File
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Format Date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = LocalDateTime.now().format(formatter);

            // Fix: Correctly map data source
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dateWiseSupplierPaymentData);

            // Parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("CollectionBeanParam", dataSource);
            parameters.put("CollectionBeanParamTwo", dataSource);
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("chikleeLogo", getClass().getResourceAsStream("/static/images/chiklee_logo.png"));
            parameters.put("chikleeNumber", "Report #C_D_02A");
            parameters.put("printDate", currentDateTime);
            parameters.put("DateOne", "22-08-2023");
            parameters.put("DateTwo", "23-08-2023");

            // Fill Report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export to PDF
            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            // Set Headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=DateWiseSupplierPayment.pdf");

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
