package com.sazzad.erpReport.service.indentItem.impl;


import com.sazzad.erpReport.dto.indentItem.IndentItemDto;
import com.sazzad.erpReport.service.indentItem.IndentItemReportService;
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
public class IndentItemReportServiceImpl implements IndentItemReportService {
    @Override
    public ResponseEntity<byte[]> downloadReport() {
        try {
            List<IndentItemDto> items = Arrays.asList(
                    new IndentItemDto(1, "Basmati Rice",  "Kg", 50, 290, 14500, "Good"),
                    new IndentItemDto(2, "Buter Daal",  "Kg", 20, 120, 2400, "Good")

            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/IndentReport.jrxml");
            if (reportStream == null) {
                throw new JRException("Report template not found");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IndentNo", "000050");
            parameters.put("IndentTo", "Sub Store-Kitchen");
            parameters.put("IndentDate","15-08-2023");
            parameters.put("ChikleeNumber"," Report # C_B_01A");
            parameters.put("PrintDate", currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=IndentReport.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

