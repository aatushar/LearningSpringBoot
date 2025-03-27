package com.sazzad.erpReport.service.misMatchSalesReport.impl;


import com.sazzad.erpReport.dto.misMatchSalesReport.DailyMisMatchSalesDto;
import com.sazzad.erpReport.service.misMatchSalesReport.DailyMismatchSalesService;
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
public class DailyMismatchSalesServiceImpl implements DailyMismatchSalesService {

    public ResponseEntity<byte[]> generateDailyMismatchSalesReport() {
        try {
            List<DailyMisMatchSalesDto> dailyMisMatchSalesData = Arrays.asList(
                    new DailyMisMatchSalesDto(1L, " Go Kart", "31/07/2023", 0.00, 0.00, 600.00)


            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/DailyMisMatchSalesReport.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("chikleelogo", "asseet/chiklee_logo.png");
            parameters.put("date", "11/01/1999");
            parameters.put("submitedBy", "");
            parameters.put("recommededBy", "");
            parameters.put("approvedBy", "");
            parameters.put("chikleeNumber", "Report Chiklee#CZ_05");
            parameters.put("printDate", currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dailyMisMatchSalesData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=DailyMisMatchSalesReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (
                Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}