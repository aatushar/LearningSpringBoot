package com.khajana.setting.repository.issuemaster;

import com.khajana.setting.dto.issuemaster.IssueMasterDto;
import com.khajana.setting.entity.issuemaster.IssueMasterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IssueMasterRepo extends JpaRepository<IssueMasterEntity, Long> {
    @Query("SELECT  new com.khajana.setting.dto.issuemaster.IssueMasterDto("
            + "vm.id,vm.issueDeliveryNo,vm.deliveryDate,fy.customerName,vm.challanNumber,vm.cahallanDate, "
            + "vm.createdAt,vm.updatedAt, vm.createdBy, vm.updatedBy) "
            + "FROM IssueMasterEntity vm JOIN vm.customerDetailsEntity fy")
    public Page<IssueMasterDto> getJoinInformationIssue(Pageable pageable);
//    @Query("SELECT  new com.khajana.setting.dto.vat.VatMonthDto("
//            + "vm.id,vm.fyId,fy.fiscalYear ,fy.fiscalYearBn,vm.vmInfo,vm.vmInfoBn,vm.sequenceNumber, "
//            + "vm.fromDate,vm.toDate, vm.vmStatus, vm.isActive,vm.createdAt) "
//            + "FROM VatMonthInfo vm JOIN vm.fiscalYearInfo fy")
}
