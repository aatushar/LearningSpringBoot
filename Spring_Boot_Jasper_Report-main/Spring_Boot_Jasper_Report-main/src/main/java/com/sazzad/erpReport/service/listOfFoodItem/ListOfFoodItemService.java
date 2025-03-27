package com.sazzad.erpReport.service.listOfFoodItem;

import org.springframework.http.ResponseEntity;

public interface ListOfFoodItemService {
    ResponseEntity<byte[]> generateListOfFoodItem();
}
