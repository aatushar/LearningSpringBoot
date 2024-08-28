package com.khajana.setting.controller.issuemaster;

import com.khajana.setting.service.issuemaster.SpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/setting/api/v1/sp")
public class SpController {

    //    private SpRepo spRepo;
    @Autowired
    SpService spService;

    @GetMapping("/all")
    public List<Map<String, Object>> getAllSellIndexData(@RequestParam("prodTypeId") int prodTypeId) {
        return spService.getAllSellIndexData(prodTypeId);
    }
}
