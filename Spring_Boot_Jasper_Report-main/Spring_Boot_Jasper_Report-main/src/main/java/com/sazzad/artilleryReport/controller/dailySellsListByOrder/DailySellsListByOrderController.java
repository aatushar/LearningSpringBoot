package com.sazzad.artilleryReport.controller.dailySellsListByOrder;


import com.sazzad.artilleryReport.service.dailySellsListByOrder.DailySellsListByOrderService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("settingdev/api/v1")
public class DailySellsListByOrderController {

    @Autowired
    private DailySellsListByOrderService dailySellsListByOrderService;

    @GetMapping("/daily-sells-list-by-order")
    public void makePdfReport(
            HttpServletResponse response,
            @RequestParam(name = "transDate") String transDate,
            @RequestParam(name = "storeId") Long storeId
    ) throws IOException {
        dailySellsListByOrderService.makePdfReport(response, transDate, storeId);
    }
}
