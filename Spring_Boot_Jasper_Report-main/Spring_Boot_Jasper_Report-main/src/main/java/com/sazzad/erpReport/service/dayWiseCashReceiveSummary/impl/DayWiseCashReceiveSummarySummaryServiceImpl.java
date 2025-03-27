package com.sazzad.erpReport.service.dayWiseCashReceiveSummary.impl;



import com.sazzad.erpReport.dto.dayWiseCashReceiveSummary.DayWiseCashReceiveSummaryDto;
import com.sazzad.erpReport.service.dayWiseCashReceiveSummary.DayWiseCashReceiveSummaryService;
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
public class DayWiseCashReceiveSummarySummaryServiceImpl implements DayWiseCashReceiveSummaryService {
    @Override
    public ResponseEntity<byte[]> generateDailySellsCashReceiveReport() {
        try {
            List<DayWiseCashReceiveSummaryDto> dailySellsCashReceiveData = Arrays.asList(
                    new DayWiseCashReceiveSummaryDto(1, 22, "04/04/2023", 1200.00,14000.00 , ""),
                    new DayWiseCashReceiveSummaryDto(2, 22, "04/04/2023", 400.00,14000.00 , ""),
                    new DayWiseCashReceiveSummaryDto(3, 22, "04/04/2023", 1500.00,6000.00 , "")
            );
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/DayWiseCashReceivedSummary.jrxml");

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
            parameters.put("chikleeNumber", "Report # C_D_01B");
            parameters.put("printDate", "Print: " + currentDateTime);
            parameters.put("submitedBy", "");
            parameters.put("recommededBy","");
            parameters.put("approvedBy","");

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dailySellsCashReceiveData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=DayWiseCashReceivedSummary.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }
    }
}
