package com.sazzad.erpReport.service.listOfFoodItem.Impl;



import com.sazzad.erpReport.dto.listOfFoodItem.ListOfFoodItemDto;
import com.sazzad.erpReport.service.listOfFoodItem.ListOfFoodItemService;
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
public class ListOfFoodItemServiceImpl implements ListOfFoodItemService {
    public ResponseEntity<byte[]> generateListOfFoodItem() {
        try {
            List<ListOfFoodItemDto> listOfFoodData = Arrays.asList(
                    new ListOfFoodItemDto(1, 1, "#f345", "Chicken Fry", "")
            );
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/ListOfFoodItem.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("chikleeLogo", "asset/chiklee_logo.png");
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("chikleeNumber", "Report # C_C_01D");
            parameters.put("printDate", "Print: " + currentDateTime);


            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listOfFoodData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ListOfFoodItem.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
