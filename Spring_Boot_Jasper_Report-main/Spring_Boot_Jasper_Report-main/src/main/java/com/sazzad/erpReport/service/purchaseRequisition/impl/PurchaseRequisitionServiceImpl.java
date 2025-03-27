package com.sazzad.erpReport.service.purchaseRequisition.impl;


import com.sazzad.erpReport.dto.purchaseRequsition.PurchaseRequisitionDto;
import com.sazzad.erpReport.service.purchaseRequisition.PurchaseRequisitionService;
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
public class PurchaseRequisitionServiceImpl implements PurchaseRequisitionService {

    @Override
    public ResponseEntity<byte[]> generatePurchaseRequisition() {
        try {
            List<PurchaseRequisitionDto> purchaseRequisitonList = Arrays.asList(
                    new PurchaseRequisitionDto(1, "000071", "2023-08-15", "Big Onion", "Kg", 0.00, 2.00, 0.00, "good"),
                    new PurchaseRequisitionDto(2, "000071", "2023-08-15", "Bitter", "Kg", 0.00, 2.00, 0.00, ""),
                    new PurchaseRequisitionDto(3, "000071", "2023-08-15", "Bombe Chili", "Pcs", 0.00, 1.00, 0.00, ""),
                    new PurchaseRequisitionDto(4, "000071", "2023-08-15", "Coriander Leaf", "Kg", 150.00, 1.00, 150.00, ""),
                    new PurchaseRequisitionDto(5, "000071", "2023-08-15", "Cucumber", "Kg", 60.00, 1.00, 60.00, ""),
                    new PurchaseRequisitionDto(6, "000071", "2023-08-15", "Lemon", "Pcs", 10.00, 11.00, 110.00, ""),
                    new PurchaseRequisitionDto(7, "000071", "2023-08-15", "Mint Leaf", "Kg", 50.00, 1.00, 50.00, ""),
                    new PurchaseRequisitionDto(8, "000071", "2023-08-15", "Potato", "Kg", 30.00, 1.00, 30.00, "")
        );


            // Load JRXML Template
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/PurchaseRequisition.jrxml");
            if (reportStream == null) {
                throw new JRException("Report template not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            // Parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("purchaseRequisitionNo", "000071");
            parameters.put("to", "Main Store");
            parameters.put("RequisitonDate", "2023-08-15");
            parameters.put("Product Type", "Raw Materials");
            parameters.put("ChikleeNumber", " # C_B_03A");
            parameters.put("PrintDate", currentDateTime);

            // Data Source
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(purchaseRequisitonList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export Report to PDF
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            // Return as Response
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=PurchaseRequisitionReport.pdf");

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