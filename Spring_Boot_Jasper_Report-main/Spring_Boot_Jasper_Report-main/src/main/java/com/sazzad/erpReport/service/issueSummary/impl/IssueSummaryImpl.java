package com.sazzad.erpReport.service.issueSummary.impl;


import com.sazzad.erpReport.dto.issueSummary.IssueSummaryDto;
import com.sazzad.erpReport.service.issueSummary.IssueSummaryService;
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
public class IssueSummaryImpl implements IssueSummaryService {
public ResponseEntity<byte[]> generateIssueSummaryReport() {
    try{
        List<IssueSummaryDto> issueSummaryData = Arrays.asList(
                new IssueSummaryDto(1,1, "18-01-2025", "Orchid", "Chiklee", 50000.00, "")
        );

        InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/IssueSummaryReport.jrxml");

        if (reportStream == null){
            throw new JRException("Report not found");

        }
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String currentDateTime = LocalDateTime.now().format(formatter);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("companyName", "Chiklee Water Park");
        parameters.put("chikleelogo", "asseet/chiklee_logo.png");
        parameters.put("date", "Date:");
        parameters.put("chikleeNumber", "Report # C_B_04B");
        parameters.put("printDate", "Print: " + currentDateTime);
        parameters.put("submitedBy", "");
        parameters.put("recommededBy","");
        parameters.put("approvedBy","");


        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(issueSummaryData);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=IssueSummaryReport.pdf");

        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e)
    {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
}
