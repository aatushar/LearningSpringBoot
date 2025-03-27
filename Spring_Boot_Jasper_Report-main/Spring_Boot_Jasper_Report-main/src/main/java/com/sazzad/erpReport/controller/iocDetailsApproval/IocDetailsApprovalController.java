package com.sazzad.erpReport.controller.iocDetailsApproval;



import com.sazzad.erpReport.service.iocDetaisApproval.IocDetailsApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class IocDetailsApprovalController {

    @Autowired
    private IocDetailsApprovalService iocDetailsApprovalService;

    @GetMapping("/ioc-details-approval-report-pdf")
    public ResponseEntity<byte[]> getIocDetailsApproval() {
        return iocDetailsApprovalService.generateIocDetailsApprovalReport();
    }
}
