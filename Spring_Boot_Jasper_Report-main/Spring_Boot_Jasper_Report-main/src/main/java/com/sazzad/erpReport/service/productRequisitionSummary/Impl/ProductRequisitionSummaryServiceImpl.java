package com.sazzad.erpReport.service.productRequisitionSummary.Impl;


import com.sazzad.erpReport.dto.productRequisitionSummary.ProductRequisitionSummaryDto;
import com.sazzad.erpReport.service.productRequisitionSummary.ProductRequisitionSummaryService;
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
public class ProductRequisitionSummaryServiceImpl implements ProductRequisitionSummaryService {

    @Override
    public ResponseEntity<byte[]> generateRequisitionSummaryReport() {
        try {
            // Sample Data
            List<ProductRequisitionSummaryDto> requisitionList = Arrays.asList(
                    new ProductRequisitionSummaryDto(1, "000073", "2023-08-16", "Rokibul Islam", "Dry Items", "NO", "NO", "Rokibul Islam", ""),
                    new ProductRequisitionSummaryDto(2, "000075", "2023-08-16", "Rokibul Islam", "Beverage, Dessert-RM", "NO", "NO", "Rokibul Islam", ""),
                    new ProductRequisitionSummaryDto(3, "000076", "2023-08-16", "Rokibul Islam", "Dry Items", "NO", "NO", "Rokibul Islam", "")
            );

            // Load JRXML Template
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/ProductRequisitionSummary.jrxml");
            if (reportStream == null) {
                throw new JRException("Report template not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            // Parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Product Requisition Summary");
            parameters.put("Period", "2023-08-16 - 2023-08-17");
            parameters.put("ChikleeNumber", "C_B_02B");
            parameters.put("PrintDate", currentDateTime);

            // Data Source
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(requisitionList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export Report to PDF
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            // Return as Response
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ProductRequisitionSummary.pdf");

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