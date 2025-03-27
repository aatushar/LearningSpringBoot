package com.sazzad.erpReport.service.listOfFgHavingIoc;

import org.springframework.http.ResponseEntity;

public interface ListOfFgHavingIocService {
    ResponseEntity<byte[]> generateListOfFgHavingIocReport();
}
