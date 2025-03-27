package com.sazzad.erpReport.service.waiterWiseDailySales.Impl;

import com.sazzad.erpReport.dto.waiterWiseDailySales.WaiterWiseDailySalesDto;
import com.sazzad.erpReport.service.waiterWiseDailySales.WaiterWiseDailySalesService;
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
public class WaiterWiseDailySalesServiceImpl implements WaiterWiseDailySalesService {
    @Override
    public ResponseEntity<byte[]> generateWaiterWiseDailySalesReport() {
        try {
            List<WaiterWiseDailySalesDto> salesList = Arrays.asList(
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00),
                    new WaiterWiseDailySalesDto(1, "MD Irfan", "3/29/2023, 8:27 PM", 9000.00),
                    new WaiterWiseDailySalesDto(2, "Asad Munem", "3/29/2023, 8:27 PM", 8000.00)

            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/WaiterWiseDailySalesReport.jrxml");
            if (reportStream == null) {
                throw new JRException("Report template not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("selectDate", "30-03-2023");
            parameters.put("restaurant", "Restaurant: Orchid");
            parameters.put("date", "Date: 30-03-2023");
            parameters.put("ChikleeNumber", "Chiklee# 9C_A_03");
            parameters.put("printDate", currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(salesList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=WaiterWiseDailySalesReport.pdf");

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