package com.sazzad.erpReport.service.issueReturn.impl;


import com.sazzad.erpReport.dto.issueReturn.IssueReturnDto;
import com.sazzad.erpReport.service.issueReturn.IssueReturnService;
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
public class IssueReturnServiceImpl implements IssueReturnService {

    public ResponseEntity<byte[]> generateIssueReturnReport() {
        try{
            List<IssueReturnDto> issueReturnData = Arrays.asList(
                    new IssueReturnDto(1, "Rice", "KG", 100.00, 25, 25, 2500.00,500.0,""),
                    new IssueReturnDto(2, "12 water", "Person", 600.00, 12, 12, 7200.00,00.0,"")

            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/IssueReturnReport.jrxml");

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
            parameters.put("recommededBy","");
            parameters.put("approvedBy","");
            parameters.put("chikleeNumber", "Report # C_B_05A");
            parameters.put("printDate", "Print: " + currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(issueReturnData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=IssueReturnReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch(
                Exception e)

        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}