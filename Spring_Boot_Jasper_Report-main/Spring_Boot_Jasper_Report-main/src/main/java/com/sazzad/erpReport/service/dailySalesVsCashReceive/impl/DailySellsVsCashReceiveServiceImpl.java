package com.sazzad.erpReport.service.dailySalesVsCashReceive.impl;


import com.sazzad.erpReport.dto.dailySalesVsCashReceive.DailySalesVsCashReceiveDto;
import com.sazzad.erpReport.service.dailySalesVsCashReceive.DailySellsVsCashReceiveService;
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
public class DailySellsVsCashReceiveServiceImpl implements DailySellsVsCashReceiveService {
    @Override
    public ResponseEntity<byte[]> generateDailySellsVsCashReceiveReport() {
        try {
            List<DailySalesVsCashReceiveDto> dailySalesVsCashReceiveData = Arrays.asList(
                    new DailySalesVsCashReceiveDto(1, "Happy Zone", 3165.00, 0.00,  0.00, 3165.00, 3165.00, 0.00, 0.00, 0.00, 0.00,3165.00, 0.00, 3165.00,""),
                    new DailySalesVsCashReceiveDto(2, "Tulip-Fuchka Zone", 3165.00, 0.00,  0.00, 3165.00, 3165.00, 0.00, 0.00, 0.00, 0.00,3165.00, 0.00, 3165.00,""),
                    new DailySalesVsCashReceiveDto(3, "Mini Kitchen", 3165.00, 0.00,  0.00, 3165.00, 3165.00, 0.00, 0.00, 0.00, 0.00,3165.00, 0.00, 3165.00,""),
                    new DailySalesVsCashReceiveDto(4, "Bakery Zone", 3165.00, 0.00,  0.00, 3165.00, 3165.00, 0.00, 0.00, 0.00, 0.00,3165.00, 0.00, 3165.00,""),
                    new DailySalesVsCashReceiveDto(5, "Orchid-Restaurant", 3165.00, 0.00,  0.00, 3165.00, 3165.00, 0.00, 0.00, 0.00, 0.00,3165.00, 0.00, 3165.00,""),
                    new DailySalesVsCashReceiveDto(6, " Boutique Shop", 3165.00, 0.00,  0.00, 3165.00, 3165.00, 0.00, 0.00, 0.00, 0.00,3165.00, 0.00, 3165.00,""),
                    new DailySalesVsCashReceiveDto(7, " Salad Bar", 3165.00, 0.00,  0.00, 3165.00, 3165.00, 0.00, 0.00, 0.00, 0.00,3165.00, 0.00, 3165.00,""),
                    new DailySalesVsCashReceiveDto(8, " Entry Gate", 3165.00, 0.00,  0.00, 3165.00, 3165.00, 0.00, 0.00, 0.00, 0.00,3165.00, 0.00, 3165.00,""),
                    new DailySalesVsCashReceiveDto(9, "Water Ride Zone", 3165.00, 0.00,  0.00, 3165.00, 3165.00, 0.00, 0.00, 0.00, 0.00,3165.00, 0.00, 3165.00,""),
                    new DailySalesVsCashReceiveDto(10, " Go Kart", 3165.00, 0.00,  0.00, 3165.00, 3165.00, 0.00, 0.00, 0.00, 0.00,3165.00, 0.00, 3165.00,""),
                    new DailySalesVsCashReceiveDto(11, " Chiklee Express", 3165.00, 0.00,  0.00, 3165.00, 3165.00, 0.00, 0.00, 0.00, 0.00,3165.00, 0.00, 3165.00,""),
                    new DailySalesVsCashReceiveDto(12, "  Kids Zone", 3165.00, 0.00,  0.00, 3165.00, 3165.00, 0.00, 0.00, 0.00, 0.00,3165.00, 0.00, 3165.00,""),
                    new DailySalesVsCashReceiveDto(13, "   Game Zone", 3165.00, 0.00,  0.00, 3165.00, 3165.00, 0.00, 0.00, 0.00, 0.00,3165.00, 0.00, 3165.00,"")

            );
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/DailySellsVsCashReceivedReport.jrxml");

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

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource( dailySalesVsCashReceiveData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=DailySellsVsCashReceivedReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }
    }
}