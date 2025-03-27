package com.sazzad.erpReport.service.itemStockSummary.impl;



import com.sazzad.erpReport.dto.itemStockSummary.ItemStockSummaryDto;
import com.sazzad.erpReport.service.itemStockSummary.ItemStockSummaryService;
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
public class ItemStockSummaryServiceImpl implements ItemStockSummaryService {
    @Override
    public ResponseEntity<byte[]> generateItemStockSummaryReport() {
        try {
            List<ItemStockSummaryDto> itemStockSummaryData = Arrays.asList(
                    new ItemStockSummaryDto(1, 1, "Polao Rice", "KG", 15.00, 950.00, 14250.00, "")
            );
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/ItemStockSummary.jrxml");

            if (reportStream == null) {
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

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(itemStockSummaryData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ItemStockSummary.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }
    }
}