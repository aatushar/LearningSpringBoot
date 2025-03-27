package com.sazzad.erpReport.service.kitchenWiseSales.Impl;


import com.sazzad.erpReport.dto.kitchenWiseSales.KitchenWiseSalesDto;
import com.sazzad.erpReport.service.kitchenWiseSales.KitchenWiseSalesService;
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
public class KitchenWiseSalesServiceImpl implements KitchenWiseSalesService {

    @Override
    public ResponseEntity<byte[]> generateKitchenWiseSalesReport() {
        try {
            List<KitchenWiseSalesDto> salesData = Arrays.asList(
                    new KitchenWiseSalesDto(1, "29/03/2023", 3000.00, 3000.00, 4060.00, 6755.00, 464.00, 17279.00),
                    new KitchenWiseSalesDto(2, "30/03/2023", 4654.00, 370.00, 4553.00, 1509.00, 1100.00, 12186.00)
            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/KitchenWiseSalesReport.jrxml");
            if (reportStream == null) {
                throw new JRException("Report template not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fromDate", "28-03-2023");
            parameters.put("toDate", "30-03-2023");
            parameters.put("restaurantName", "Restaurant: Orchid");
            parameters.put("date", "Date: 30-03-2023");
            parameters.put("chikleeNumber", "Chiklee# 9C_A_05");
            parameters.put("printDate", currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(salesData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=KitchenWiseSalesReport.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(pdfData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}