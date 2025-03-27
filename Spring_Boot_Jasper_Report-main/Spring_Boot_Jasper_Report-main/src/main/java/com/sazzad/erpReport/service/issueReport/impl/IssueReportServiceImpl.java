package com.sazzad.erpReport.service.issueReport.impl;


import com.sazzad.erpReport.dto.issueReportDto.IssueReportDto;
import com.sazzad.erpReport.service.issueReport.IssueReportService;
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
public class IssueReportServiceImpl implements IssueReportService {
    public ResponseEntity<byte[]> generateIssueReport() {
        try {
            List<IssueReportDto> issueData = Arrays.asList(
                    new IssueReportDto(1,"Rice",25.0,"KG",100.00,2500.00,""),
                    new IssueReportDto(2,"12 Waiter",12.0,"Person",100.00,2.0,"For the Program of Mr Jabed's Son's Meeriage Ceremony on 14 Apr 23")
            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/IssueReport.jrxml");

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
            parameters.put("chikleeNumber", "Report Chiklee #9C_04A");
            parameters.put("printDate", currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(issueData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=IssueReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}