package com.sazzad.artilleryReport.service.dailySalesSummaryByItem;

import jakarta.servlet.http.HttpServletResponse;


public interface DailySalesSummaryByItemService {
     void makePdfReport(HttpServletResponse httpServletResponse, String transDate, Long storeId );


}
