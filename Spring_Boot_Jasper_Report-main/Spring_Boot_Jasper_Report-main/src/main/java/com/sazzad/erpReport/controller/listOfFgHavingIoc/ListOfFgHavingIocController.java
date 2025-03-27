package com.sazzad.erpReport.controller.listOfFgHavingIoc;



import com.sazzad.erpReport.service.listOfFgHavingIoc.ListOfFgHavingIocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class ListOfFgHavingIocController {
    @Autowired
    public ListOfFgHavingIocService listOfFgHavingIocService;

    @GetMapping("/list-of-having-ioc-report-pdf")
    public ResponseEntity<byte[]> getListOfFgHavingIoc() {
        return listOfFgHavingIocService.generateListOfFgHavingIocReport();
    }
}
