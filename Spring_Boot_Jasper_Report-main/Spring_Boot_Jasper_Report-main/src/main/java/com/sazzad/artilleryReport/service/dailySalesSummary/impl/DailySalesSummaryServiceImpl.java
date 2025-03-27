package com.sazzad.artilleryReport.service.dailySalesSummary.impl;


import com.sazzad.artilleryReport.dto.dailySalesSummary.DailySalesSummaryDto;

import com.sazzad.artilleryReport.service.dailySalesSummary.DailySalesSummaryService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DailySalesSummaryServiceImpl implements DailySalesSummaryService {

    private final JdbcTemplate jdbcTemplate;

    public DailySalesSummaryServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void makePdfReport(HttpServletResponse response, Long StoreID) {
        try {
            // Properly formatted SQL procedure call
            String sql = "EXEC getDateWiseDailySalesSummery ?";
            List<DailySalesSummaryDto> dataList = jdbcTemplate.query(
                    sql, new BeanPropertyRowMapper<>(DailySalesSummaryDto.class), StoreID
            );

            if (dataList.isEmpty()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                response.getWriter().write("No data available for the given parameters.");
                return;
            }

            // Parameters for the report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("reportName", "Daily Sales Summary");
            parameters.put("companyName", dataList.get(0).getCompanyName());
            parameters.put("storeName", dataList.get(0).getStoreName());
            parameters.put("artilleryLogo", "asset/artillery_logo.png");


            // Load the JasperReports template file
            InputStream inputStream = getClass().getResourceAsStream("/reports/artilleryReport/DailySalesSummary.jrxml");
            if (inputStream == null) {
                throw new RuntimeException("Jasper report template not found.");
            }

            // Compile report
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

            // Fill report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Set response content type
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=DailySalesSummary.pdf");

            // Export report to response output stream
            OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            outputStream.flush();

        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void handleException(HttpServletResponse response, Exception e) {
        try {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write("Error generating report: " + e.getMessage());
        } catch (Exception ioException) {
            ioException.printStackTrace();
        }
    }
}
