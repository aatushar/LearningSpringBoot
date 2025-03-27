package com.sazzad.erpReport.service.issueReturnQty.impl;


import com.sazzad.erpReport.dto.IssueReturnQty.IssueReturnQtyDto;
import com.sazzad.erpReport.service.issueReturnQty.IssueReturnQtyService;
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
public class IssueReturnQtyServiceImpl implements IssueReturnQtyService {

    public ResponseEntity<byte[]> generateIssueReturnQtyReport() {
        try {
            List<IssueReturnQtyDto> issueReturnQtyData = Arrays.asList(
                    new IssueReturnQtyDto(1, "Rice", "KG", 30, 25, 25, ""),
                    new IssueReturnQtyDto(2, "12 water", "Person", null, 12, 12, "For the Program of Mr Jabed's Son's Meeriage Ceremony on 14 Apr 23")

            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/IssueReturnQtyReport.jrxml");

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
            parameters.put("chikleeNumber", "Report # C_B_05B");
            parameters.put("printDate", "Print: " + currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(issueReturnQtyData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=IssueReturnQtyReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (
                Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
