package com.sazzad.erpReport.service.orderWiseDailySales.impl;


import com.sazzad.erpReport.dto.orderWiseDailySales.OrderWiseDailySalesDto;
import com.sazzad.erpReport.service.orderWiseDailySales.OrderWiseDailySalesService;
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
public class OrderWiseDailySalesServiceImpl implements OrderWiseDailySalesService {

    @Override
    public ResponseEntity<byte[]> generateOrderWiseDailySalesReport() {
        try {
            List<OrderWiseDailySalesDto> salesList = Arrays.asList(
                    new OrderWiseDailySalesDto(1, "MD Irfan", "1755638944", "10001", "Dine IN",
                            "Anis", "3/29/2023, 8:27 PM", 9000.00),
                    new OrderWiseDailySalesDto(2, "Asad Munem", "1755638944", "10002", "Take Away",
                            "Iqbal", "3/29/2023, 8:27 PM", 8000.00)
            );

            // Load JRXML File
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/OrderWiseDailySalesReport.jrxml");
            if (reportStream == null) {
                throw new JRException("Report template not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            // Parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("selectDate", "30-03-2023");
            parameters.put("restaurant", "Restaurant : Orchid");
            parameters.put("date", "Date: 30-03-2023");
            parameters.put("ChikleeNumber", "Chiklee# 9C_A_02");
            parameters.put("PrintDate", currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(salesList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=OrderWiseDailySalesReport.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}