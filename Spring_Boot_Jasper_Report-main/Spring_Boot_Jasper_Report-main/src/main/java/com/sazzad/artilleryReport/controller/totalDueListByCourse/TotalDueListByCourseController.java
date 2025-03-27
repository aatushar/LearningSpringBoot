package com.sazzad.artilleryReport.controller.totalDueListByCourse;


import com.sazzad.artilleryReport.service.totalDueListByCourse.TotalDueListByCourseService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/settingdev/api/v1/")
public class TotalDueListByCourseController {

    @Autowired
    private TotalDueListByCourseService totalDueListByCourseService;


    @GetMapping("total-due-list-by-course-report-pdf")
    public void makePdfReport(
            HttpServletResponse response,
            @RequestParam(name = "StoreID") Long StoreID,
            @RequestParam(name = "Month") String Month,
            @RequestParam(name = "Year") String Year
    ) throws IOException {
        totalDueListByCourseService.makePdfReport(response, StoreID,Month,Year);
    }
}

