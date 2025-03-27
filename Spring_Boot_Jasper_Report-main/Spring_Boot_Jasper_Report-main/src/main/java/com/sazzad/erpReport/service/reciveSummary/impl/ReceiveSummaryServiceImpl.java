package com.sazzad.erpReport.service.reciveSummary.impl;


import com.sazzad.erpReport.dto.reciveSummary.ReceiveSummaryDto;
import com.sazzad.erpReport.service.reciveSummary.ReceiveSummaryService;
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
public class ReceiveSummaryServiceImpl implements ReceiveSummaryService {
    public ResponseEntity<byte[]> generateReceiveSummary() {
        try {
            List<ReceiveSummaryDto> receiveSummaryData = Arrays.asList(
                    new ReceiveSummaryDto(1, 1, "15-01-2025", "Runner", "A55", 2500.00, 500.0, "")

            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/ReceiveSummaryReport.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("chikleelogo", "asseet/chiklee_logo.png");
            parameters.put("date", "Date:");
            parameters.put("submitedBy", "");
            parameters.put("recommededBy", "");
            parameters.put("approvedBy", "");
            parameters.put("chikleeNumber", "Report # C_B_04A");
            parameters.put("printDate", "Print: " + currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(receiveSummaryData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ReceiveSummaryReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (
                Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}