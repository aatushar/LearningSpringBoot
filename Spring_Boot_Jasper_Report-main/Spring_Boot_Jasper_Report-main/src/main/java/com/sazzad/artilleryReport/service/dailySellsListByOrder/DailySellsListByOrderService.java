package com.sazzad.artilleryReport.service.dailySellsListByOrder;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface DailySellsListByOrderService {
    void makePdfReport(HttpServletResponse httpServletResponse, String transDate, Long storeId );
}
