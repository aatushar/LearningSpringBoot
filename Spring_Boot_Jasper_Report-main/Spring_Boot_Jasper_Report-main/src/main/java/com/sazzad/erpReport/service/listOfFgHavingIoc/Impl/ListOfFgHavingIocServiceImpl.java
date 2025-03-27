package com.sazzad.erpReport.service.listOfFgHavingIoc.Impl;


import com.sazzad.erpReport.dto.listOfFgHavingIoc.ListOfFgHavingIocDto;
import com.sazzad.erpReport.service.listOfFgHavingIoc.ListOfFgHavingIocService;
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
public class ListOfFgHavingIocServiceImpl implements ListOfFgHavingIocService {
    public ResponseEntity<byte[]> generateListOfFgHavingIocReport() {

        try {
            List<ListOfFgHavingIocDto> listOfFgHavingIocData = Arrays.asList(
                    new ListOfFgHavingIocDto(1, 1,"15 Mar-23", "Polao Rice", "1:3", 950.00, "")
            );
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/ListOfFgHavingioc.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("chikleeLogo","asset/chiklee_logo.png");
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("restaurant", "Orchid-Restaurant");
            parameters.put("chikleeNumber", "Report # C_C_01B");
            parameters.put("printDate", "Print: " + currentDateTime);


            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listOfFgHavingIocData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ListOfFgHavingioc.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}