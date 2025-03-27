package com.sazzad.erpReport.service.issueReturnSummary.impl;


import com.sazzad.erpReport.dto.isssueReturnSummary.IssueReturnSummaryDto;
import com.sazzad.erpReport.service.issueReturnSummary.IssueReturnSummaryService;
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
public class IssueReturnSummaryServiceImpl implements IssueReturnSummaryService {
   public ResponseEntity<byte[]> generateIssueReturnSummaryReport() {
        try{
            List<IssueReturnSummaryDto> issueReturnSummaryData = Arrays.asList(
                    new IssueReturnSummaryDto(5,4, "18-01-2025", "Orchid", "Chiklee", 50000, 100,"")
            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/IssueReturnSummaryReport.jrxml");

            if (reportStream == null){
                throw new JRException("Report not found");

            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("chikleeLogo","asset/chiklee_logo.png");
            parameters.put("date", "Date:");
            parameters.put("chikleeNumber", "Report # C_B_05C");
            parameters.put("printDate", "Print: " + currentDateTime);
            parameters.put("submitedBy", "");
            parameters.put("recommededBy","");
            parameters.put("approvedBy","");


            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(issueReturnSummaryData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=IssueReturnSummaryReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}