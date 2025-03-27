package com.sazzad.erpReport.service.storeWiseItemList;

import org.springframework.http.ResponseEntity;

public interface StoreWiseItemListService {
    ResponseEntity<byte[]> generateStoreWiseItemListReport ();
}
