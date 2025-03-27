package com.sazzad.erpReport.service.itemOpeningStockReport.impl;



import com.sazzad.erpReport.dto.itemOpeningStockReport.ItemOpeningStockDto;
import com.sazzad.erpReport.service.itemOpeningStockReport.ItemOpeningStockService;
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
public class ItemOpeningStockServiceImpl implements ItemOpeningStockService {

    @Override
    public ResponseEntity<byte[]> generateItemOpeningStockReport() {
        try {
            List<ItemOpeningStockDto> itemOpeningData = Arrays.asList(
                    new ItemOpeningStockDto(1, 1, "15-Mar-23", "Polao Rice", "KG", 15.00, 950.00, 14250.00, "")
            );
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/ItemOpeningStockReport.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("chikleeLogo", "asset/chiklee_logo.png");
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("productCategory:", "Product Category:");
            parameters.put("storeName", "Store Name");
            parameters.put("date", "Date: ");
            parameters.put("chikleeNumber", "Report # C_C_02A");
            parameters.put("printDate", currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(itemOpeningData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ItemOpeningStockReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }
    }
}
