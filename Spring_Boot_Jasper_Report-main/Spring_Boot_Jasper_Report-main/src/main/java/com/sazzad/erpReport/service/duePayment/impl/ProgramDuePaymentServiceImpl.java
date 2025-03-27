package com.sazzad.erpReport.service.duePayment.impl;


import com.sazzad.erpReport.dto.duePayment.ProgramDuePaymentDto;
import com.sazzad.erpReport.service.duePayment.ProgramDuePaymentService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProgramDuePaymentServiceImpl implements ProgramDuePaymentService {

    @Override
    public byte[] generateReport() throws JRException {
        // Create sample data inside the service
        ProgramDuePaymentDto item1 = new ProgramDuePaymentDto(1, "IBN Sina-Seminar", "IBN Sina-Rangpur", "01713-041428",
                "1-Aug-2023", "Evening(7PM-12PM)", 400, 700000.00, 400000.00, 300000.00);
        ProgramDuePaymentDto item2 = new ProgramDuePaymentDto(2, "Popular Pharma", "Popular Meeting", "01713-041428",
                "7-Aug-2023", "Evening(7PM-12PM)", 350, 550000.00, 225000.00, 125000.00);
        ProgramDuePaymentDto item3 = new ProgramDuePaymentDto(3, "Marriage- Dr. Javed", "Dr. Javed", "01713-041428",
                "13-Aug-2023", "Full Day (10AM-12AM)", 450, 880000.00, 479000.00, 321000.00);
        ProgramDuePaymentDto item4 = new ProgramDuePaymentDto(4, "Birthday- ZIT MD", "Syed Mostafa Jamal", "01713-041428",
                "20-Aug-2023", "Full Day (10AM-12AM)", 150, 200000.00, 76300.00, 123700.00);

        List<ProgramDuePaymentDto> paymentList = Arrays.asList(item1, item2, item3, item4);

        // Load the JasperReport from the file
        InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/ProgramDuePaymentReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
        String currentDateTime = LocalDateTime.now().format(formatter);

        // Parameters for the report
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Program Due Payment List-All Time");
        parameters.put("WaterParkName", "Chiklee Water Park");
        parameters.put("Location", "Honumantola, Rangpur");
        parameters.put("ReportNumber", "C_D_02D");
        parameters.put("PrintDate", currentDateTime);


        // Creating the data source for the report
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(paymentList);

        // Filling the report with data
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Exporting the report to a byte array (PDF)
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
