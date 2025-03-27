package com.sazzad.erpReport.service.indentSummary.Impl;


import com.sazzad.erpReport.dto.indentSummary.IndentSummaryDto;
import com.sazzad.erpReport.service.indentSummary.IndentSummaryService;
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
public class IndentSummaryImpl implements IndentSummaryService {

    public ResponseEntity<byte[]> generateIndentSummaryReport() {
        try {
            List<IndentSummaryDto> indentData = Arrays.asList(
                    new IndentSummaryDto(1,"000073","29/03/2023","Rokibul Islam","Rokibul Islam","Rokibul Islam","Rokibul Islam","Rokibul Islam", "yes")
            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/indentSummaryReport.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Period", "Period: 01 Mar 23 to 31 Mar 23");
            parameters.put("ChikleeNumber", " C_B_01B");
            parameters.put("PrintDate", currentDateTime);


            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(indentData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=indentSummaryReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
