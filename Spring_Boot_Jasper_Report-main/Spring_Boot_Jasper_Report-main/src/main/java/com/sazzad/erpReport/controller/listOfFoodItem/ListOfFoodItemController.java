package com.sazzad.erpReport.controller.listOfFoodItem;


import com.sazzad.erpReport.service.listOfFoodItem.ListOfFoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class ListOfFoodItemController {
    @Autowired
    private ListOfFoodItemService listOfFoodItemService;

    @GetMapping("/list-of-food-item-report-pdf")
    public ResponseEntity<byte[]> getListOfFoodItem() {
        return listOfFoodItemService.generateListOfFoodItem();
    }


}
