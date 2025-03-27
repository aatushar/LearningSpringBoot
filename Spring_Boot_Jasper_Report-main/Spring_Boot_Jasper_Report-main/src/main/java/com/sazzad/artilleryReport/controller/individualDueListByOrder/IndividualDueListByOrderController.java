package com.sazzad.artilleryReport.controller.individualDueListByOrder;


import com.sazzad.artilleryReport.service.individualDueListByOrder.IndividualDueListByOrderService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settingdev/api/v1/")
public class IndividualDueListByOrderController {

   @Autowired
    private IndividualDueListByOrderService individualDueListByOrderService;

   @GetMapping("/individual-due-list-by-order-report-pdf")
   public void getPdfData(HttpServletResponse response,
                          @RequestParam(name = "transDate") String transDate,
                          @RequestParam(name = "storeId")Long storeId
   ) throws Exception {
       individualDueListByOrderService.makePdfIndividualDueListByOrder(response, transDate, storeId);
   }

}
