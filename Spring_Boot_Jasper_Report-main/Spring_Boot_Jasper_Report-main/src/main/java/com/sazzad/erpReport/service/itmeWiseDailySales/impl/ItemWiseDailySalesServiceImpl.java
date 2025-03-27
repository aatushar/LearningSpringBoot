package com.sazzad.erpReport.service.itmeWiseDailySales.impl;

import com.sazzad.erpReport.dto.itemWiseDailySales.ItemWiseDailySalesDto;
import com.sazzad.erpReport.service.itmeWiseDailySales.ItemWiseDailySalesService;
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
public class ItemWiseDailySalesServiceImpl implements ItemWiseDailySalesService {

    @Override
    public ResponseEntity<byte[]> generateDailySalesReport() {
        try {
            List<ItemWiseDailySalesDto> salesList = Arrays.asList(
                    new ItemWiseDailySalesDto(1, "Chicken Roast", "Pcs", 100, 90.00, 9000.00),
                    new ItemWiseDailySalesDto(2, "Chicken Momo", "6 Pcs", 80, 100.00, 8000.00)
            );


            // Load JRXML File
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/ItemWiseDailySalesReport.jrxml");
            if (reportStream == null) {
                throw new JRException("Report template not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("selectDate", "30-03-2023");
            parameters.put("date", "Date:30-03-2023");
            parameters.put("ChikleeNumber", "Chiklee# 9C_A_01");
            parameters.put("printDate", "Print: " + currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(salesList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=IndentReport.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}