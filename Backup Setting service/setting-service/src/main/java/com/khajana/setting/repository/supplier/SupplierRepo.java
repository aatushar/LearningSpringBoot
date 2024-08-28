package com.khajana.setting.repository.supplier;

import com.khajana.setting.dto.supplier.SupplierDto;
import com.khajana.setting.entity.supplier.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupplierRepo extends JpaRepository<SupplierEntity, Long> {
    @Query("SELECT new com.khajana.setting.dto.supplier.SupplierDto("
            + "vm.id,fy.vatRegType,vm.supplierName,vm.supplierNameBn,vm.supplierBinNumber,vm.supplierBinNumberBn) "
            + "FROM SupplierEntity vm JOIN vm.companyGroupEntity fy")
    Page<SupplierDto> getVatMonthInfoWithThreeTableJoin(Pageable pageable);
//    public Page<VatMonthDto> getJoinInformation(Pageable pageable);
    // @Query("SELECT new com.khajana.setting.dto.supplier.SupplierDto("
//            + "vm.id, fy.vatRegType, vm.supplierName, vm.supplierNameBn, vm.supplierBinNumber, vm.supplierBinNumberBn, "
//            + "vm.isActive, vm.createdAt) "
//            + "FROM SupplierEntity vm "
//            + "JOIN vm.companyGroupEntity fy "
//            + "JOIN vm.vatRegistrationType at")

    // SELECT
//            vm.fieldFromVatMonthInfo,
//            fy.fieldFromFiscalYearInfo,
//            at.fieldFromAnotherTableInfo
//            FROM
//            VatMonthInfo vm
//            JOIN
//            FiscalYearInfo fy ON vm.fiscalYearId = fy.id
//            JOIN
//            AnotherTableInfo at ON fy.anotherTableId = at.id;
//    @Query("SELECT  new com.khajana.setting.dto.vat.VatMonthDto("
//            + "vm.id,vm.fyId,fy.fiscalYear,fy.fiscalYearBn,vm.vmInfo,vm.vmInfoBn,vm.sequenceNumber, "
//            + "vm.fromDate,vm.toDate, vm.vmStatus, vm.isActive,vm.createdAt) "
//            + "FROM VatMonthInfo vm JOIN vm.fiscalYearInfo fy")

}
