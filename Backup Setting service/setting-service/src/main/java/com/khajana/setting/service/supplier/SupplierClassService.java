package com.khajana.setting.service.supplier;

import com.khajana.setting.dto.supplier.SupplierDto;
import com.khajana.setting.repository.supplier.SupplierRepo;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SupplierClassService {
    @Autowired
    SupplierRepo supplierRepo;

    public SimplePage<SupplierDto> findAllSupplier(Pageable pageable) {
        Page<SupplierDto> page = supplierRepo.getVatMonthInfoWithThreeTableJoin(pageable);
        return new SimplePage<>(page.getContent(), page.getTotalElements(), pageable);
    }

}
