package com.sazzad.erpReport.service.mothWiseDailySelllsDetails.impl;


import com.sazzad.erpReport.dto.monthWiseProgramSellsDetais.MonthWiseDailySellsDetailsDto;
import com.sazzad.erpReport.service.mothWiseDailySelllsDetails.MonthWiseDailySellsDetailsService;
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
public class MonthWiseDailySellsDetailsServiceImpl implements MonthWiseDailySellsDetailsService {

    @Override
    public ResponseEntity<byte[]> generateMonthWiseDailySellsReport() {
        try {
            List<MonthWiseDailySellsDetailsDto> monthWiseDailySellsDetailsServiceData = Arrays.asList(
                    new MonthWiseDailySellsDetailsDto(1,"IBN Sina-Seminar", "IBN Sina-Rangpur","01713-041428","1-Aug-2023","Evening(7PM-12PM)",400, 700000.00,400000.00,300000.00,"Due"),
                    new MonthWiseDailySellsDetailsDto(2,"Popular Meeting", "IBN Sina-Rangpur","01713-041428","7-Aug-2023","Evening(7PM-12PM)",350, 350000.00,225000.00,125000.00,"Due"),
                    new MonthWiseDailySellsDetailsDto(3,"Marriage- Dr. Javed", "Dr. Javed","01713-041428","13-Aug-2023","Full Day( 10AM- 12 AM)",450, 800000.00,800000.00,00.00,"Paid"),
                    new MonthWiseDailySellsDetailsDto(4,"Birthday- Z IT MD", "Syed Mostafa Jamal","01713-041428","20-Aug-2023","Full Day( 10AM- 12 AM)",150, 200000.00,76300.00,123700.00,"Due")
            );
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/MonthWiseDailySellsDetailsServiceReport.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("chikleeLogo", "asset/chiklee_logo.png");
            parameters.put("date", "22-08-2023");
            parameters.put("chikleeNumber", "Report #D_01C");
            parameters.put("printDate", currentDateTime);
            parameters.put("entryTicketDay", "440");
            parameters.put("entryTicketEvening", "0");
            parameters.put("approvedBy", "");

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(monthWiseDailySellsDetailsServiceData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=MonthWiseDailySellsDetailsServiceReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }
    }
}