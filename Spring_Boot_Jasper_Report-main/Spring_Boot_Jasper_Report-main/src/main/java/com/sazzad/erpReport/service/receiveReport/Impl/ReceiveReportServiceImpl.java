package com.sazzad.erpReport.service.receiveReport.Impl;


import com.sazzad.erpReport.dto.receiveReport.ReceiveReportDto;
import com.sazzad.erpReport.service.receiveReport.ReceiveReportService;
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
public class ReceiveReportServiceImpl implements ReceiveReportService {
    public ResponseEntity<byte[]> generateReceiveReport() {
        try {
            List<ReceiveReportDto> purchaseOrderData = Arrays.asList(
                    new ReceiveReportDto(1,"Rice",25.0,"KG",100.00,2500.00,""),
                    new ReceiveReportDto(2,"12 Waiter",12.0,"Person",100.00,2.0,"For the Program of Mr Jabed's Son's \n" +
                            "Meeriage Ceremony on 14 Apr 23")
            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/ReceiveReport.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("chikleeLogo", "asset/chiklee_logo.png");
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("submitedBy", "");
            parameters.put("RecommandedBy", "");
            parameters.put("approvedBy", "");
//            parameters.put("deliveryPoint", " Delivery Point:");
//            parameters.put("deliveryDate", " Delivery Date:");
//            parameters.put("payTerm", "Pay Tarm:");
            parameters.put("chikleeNumber", "Report # C_03A");
            parameters.put("printDate", "Print: " + currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(purchaseOrderData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ReceiveReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}