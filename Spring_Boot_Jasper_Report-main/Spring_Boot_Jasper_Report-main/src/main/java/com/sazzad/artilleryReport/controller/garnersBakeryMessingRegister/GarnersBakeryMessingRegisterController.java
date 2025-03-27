package com.sazzad.artilleryReport.controller.garnersBakeryMessingRegister;


import com.sazzad.artilleryReport.service.garnersBekeryMesingRegister.GarnersBakeryMassingRegisterService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/settingdev/api/v1/")
public class GarnersBakeryMessingRegisterController {
    @Autowired
    private GarnersBakeryMassingRegisterService garnersBakeryMassingRegisterService;

    @GetMapping("/garners-bakery-messing-register-report-pdf")
    public void getPdfData(
            HttpServletResponse response,
            @RequestParam(name = "StoreID") Long StoreID
    ) throws IOException {
        garnersBakeryMassingRegisterService.makePdfReport(response, StoreID);
    }
}
