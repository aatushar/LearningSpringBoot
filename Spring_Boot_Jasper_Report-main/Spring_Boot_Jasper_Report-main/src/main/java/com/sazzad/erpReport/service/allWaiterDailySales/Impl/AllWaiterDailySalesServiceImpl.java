package com.sazzad.erpReport.service.allWaiterDailySales.Impl;


import com.sazzad.erpReport.dto.allWaiterDailySales.AllWaiterDailySalesDto;
import com.sazzad.erpReport.service.allWaiterDailySales.AllWaiterDailySalesService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class AllWaiterDailySalesServiceImpl implements AllWaiterDailySalesService {

    @Override
    public ResponseEntity<byte[]> generateAllWaiterDailySalesReport() {
        try {
            List<AllWaiterDailySalesDto> salesList = Arrays.asList(
                    new AllWaiterDailySalesDto(1, "29/03/2023", 3000, 3000, 4060, 6755, 464, 17279),
                    new AllWaiterDailySalesDto(2, "30/03/2023", 4654, 370, 4553, 1509, 1100, 12186)
            );

            // Load JasperReport template
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/AllWaiterDailySalesReport.jrxml");
            if (reportStream == null) {
                throw new JRException("Report template not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            // Report parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fromDate", "28-03-2023");
            parameters.put("toDate", "30-03-2023");
            parameters.put("restaurant", "Orchid");
            parameters.put("date", "30-03-2023");
            parameters.put("ChikleeNumber", "Chiklee# 9C_A_04");
            parameters.put("PrintDate", "15-08-2023 05:17 PM");


            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(salesList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export Report to PDF
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            // Return as Response
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=AllWaiterDailySalesReport.pdf");

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