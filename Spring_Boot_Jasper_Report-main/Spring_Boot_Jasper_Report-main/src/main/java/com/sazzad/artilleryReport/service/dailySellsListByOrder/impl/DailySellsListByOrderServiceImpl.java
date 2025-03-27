package com.sazzad.artilleryReport.service.dailySellsListByOrder.impl;



import com.sazzad.artilleryReport.dto.dailySellsListByOrder.DailySellsListByOrderDTO;
import com.sazzad.artilleryReport.service.dailySellsListByOrder.DailySellsListByOrderService;
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
public class DailySellsListByOrderServiceImpl implements DailySellsListByOrderService {
    private final JdbcTemplate jdbcTemplate;

    public DailySellsListByOrderServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void makePdfReport(HttpServletResponse response, String transDate, Long storeId) {
        try {
            // Properly formatted SQL procedure call
            String sql = "EXEC GetDailySellsListByOrder ?,?";
            List<DailySellsListByOrderDTO> dataList = jdbcTemplate.query(
                    sql, new BeanPropertyRowMapper<>(DailySellsListByOrderDTO.class), transDate, storeId
            );

            if (dataList.isEmpty()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                response.getWriter().write("No data available for the given parameters.");
                return;
            }

            // Parameters for the report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("reportName", "Daily Sales List By Order");
            parameters.put("companyName", dataList.get(0).getCompanyName());
            parameters.put("storeName", dataList.get(0).getStoreName());
            parameters.put("artilleryLogo", "asset/artillery_logo.png");
            parameters.put("date", transDate);

            // Load the JasperReports template file
            InputStream inputStream = getClass().getResourceAsStream("/reports/artilleryReport/DailySellsListByOrder.jrxml");
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
            response.setHeader("Content-Disposition", "inline; filename=DailySellsListByOrder.pdf");

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