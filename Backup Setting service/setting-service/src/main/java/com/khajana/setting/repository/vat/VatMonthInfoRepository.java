package com.khajana.setting.repository.vat;

import com.khajana.setting.dto.vat.VatMonthResponseDto;
import com.khajana.setting.entity.vat.VatMonthInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VatMonthInfoRepository extends JpaRepository<VatMonthInfo, Long> {

    /*
     * @Query("SELECT  new com.khajana.setting.dto.vat.VatMonthResponseDto(" +
     * "vm.id,vm.fyId,fy.fiscalYear ,fy.fiscalYearBn,vm.vmInfo,vm.vmInfoBn,vm.sequenceNumber, "
     * +
     * "vm.fromDate,vm.toDate, vm.vmStatus, vm.isActive,vm.createdAt,vm.updatedAt,vm.createdBy,vm.updatedBy) "
     * + "FROM VatMonthInfo vm JOIN vm.fiscalYearInfo fy") public
     * Page<VatMonthResponseDto> getJoinInformation(Pageable pageable);
     */

    /*
     * @Query("SELECT  new com.khajana.setting.dto.vat.VatMonthResponseDto(" +
     * "vm.id,vm.fyId,fy.fiscalYear,fy.fiscalYearBn,vm.vmInfo,vm.vmInfoBn,vm.sequenceNumber, "
     * +
     * "vm.fromDate,vm.toDate, vm.vmStatus, vm.isActive,vm.createdAt,vm.updatedAt,vm.createdBy,vm.updatedBy) "
     * + "FROM VatMonthInfo vm JOIN vm.fiscalYearInfo fy WHERE vm.id = ?1") public
     * VatMonthResponseDto findVatMonthById(Long id);
     */

    @Query("SELECT  new com.khajana.setting.dto.vat.VatMonthResponseDto("
            + "vm.id,vm.fyId,fy.fiscalYear,fy.fiscalYearBn,vm.vmInfo,vm.vmInfoBn,vm.sequenceNumber, "
            + "vm.fromDate,vm.toDate, vm.vmStatus, vm.isActive,vm.createdAt,vm.updatedAt,vm.createdBy,vm.updatedBy) "
            + "FROM VatMonthInfo vm JOIN vm.fiscalYearInfo fy WHERE vm.fyId = ?1")
    public VatMonthResponseDto findVatMonthByFYId(Long fyId);

    Optional<VatMonthInfo> findVatMonthById(Long id);

}
