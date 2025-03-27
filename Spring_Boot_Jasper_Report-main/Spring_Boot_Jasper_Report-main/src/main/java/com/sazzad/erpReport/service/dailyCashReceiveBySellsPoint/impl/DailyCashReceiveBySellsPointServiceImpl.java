package com.sazzad.erpReport.service.dailyCashReceiveBySellsPoint.impl;


import com.sazzad.erpReport.dto.dailyCashReceiveBySellsPoint.DailyCashReceiveBySellsPointDto;
import com.sazzad.erpReport.service.dailyCashReceiveBySellsPoint.DailyCashReceiveBySellsPointService;
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
public class DailyCashReceiveBySellsPointServiceImpl implements DailyCashReceiveBySellsPointService {
    @Override
    public ResponseEntity<byte[]> generateDailyCashReceiveBySellsPointReport() {
        try {
            List<DailyCashReceiveBySellsPointDto> dailyCashReceiveBySellsPointData = Arrays.asList(
                    new DailyCashReceiveBySellsPointDto(1, 1, "15-Mar-23", "Orchid", 15000.00, 14000.00, "Mr Sajib", "")

            );
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/DailyCashReceiveBySellsPointReport.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("chikleeLogo", "asset/chiklee_logo.png");
            parameters.put("date", "");
            parameters.put("chikleeNumber", "Report # C_D_01A");
            parameters.put("printDate", currentDateTime);
            parameters.put("submitedBy", "");
            parameters.put("recommededBy", "");
            parameters.put("approvedBy", "");

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dailyCashReceiveBySellsPointData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=DailyCashReceiveBySellsPointReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }
    }
}