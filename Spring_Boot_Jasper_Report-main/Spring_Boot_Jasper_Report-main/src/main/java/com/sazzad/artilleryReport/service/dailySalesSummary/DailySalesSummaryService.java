package com.sazzad.artilleryReport.service.dailySalesSummary;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface DailySalesSummaryService {
    void  makePdfReport(HttpServletResponse httpServletResponse, Long StoreID );
}
