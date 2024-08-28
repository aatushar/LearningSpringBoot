package com.khajana.setting.service.vat;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.vat.VatMonthRequestDto;
import com.khajana.setting.dto.vat.VatMonthResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.fy.FiscalYearInfo;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.entity.vat.VatMonthInfo;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.fy.FiscalYearInfoRepository;
import com.khajana.setting.repository.vat.VatMonthInfoRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VatMonthService {

    @Autowired
    VatMonthInfoRepository vatMonthInfoRepository;

    @Autowired
    FiscalYearInfoRepository fiscalYearInfoRepository;

    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
     * public Page<VatMonthDto> findAllVatMonths(Pageable pageable) {
     * Page<VatMonthDto> page = vatMonthInfoRepository.getJoinInformation(pageable);
     * return page; }
     */

    public ApiResponse findAllVatMonths(Pageable pageable) {

        Page<VatMonthInfo> page = vatMonthInfoRepository.findAll(pageable);
        return new ApiResponse(200, "Paginated Results",
                new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                        page.getTotalElements(), pageable));

    }

    public VatMonthResponseDto findVatMonthById(Long id) {

        VatMonthInfo newEntity = vatMonthInfoRepository.findVatMonthById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VatMonth", "Id", id));

        return transferToDTO(newEntity);

    }

    public VatMonthResponseDto findVatMonthByFyIdId(Long id) {
        VatMonthResponseDto dto = vatMonthInfoRepository.findVatMonthByFYId(id);
        return dto;
    }

    public ApiResponse addVatVatMonth(VatMonthRequestDto dto) {
        try {
            VatMonthInfo newEntity = vatMonthInfoRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateVatMonth(Long id, VatMonthRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            VatMonthInfo newEntity = vatMonthInfoRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private VatMonthInfo transferToEntity(Long id, VatMonthRequestDto dto) {
        VatMonthInfo entity = new VatMonthInfo();

        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            VatMonthInfo newEntity = vatMonthInfoRepository.findVatMonthById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("VatMonth", "Id", id));

            entity.setId(newEntity.getId());
            entity.setFyId(dto.getFyId());
            entity.setVmInfo(dto.getVmInfo());
            entity.setVmInfoBn(dto.getVmInfoBn());
            entity.setSequenceNumber(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            entity.setFromDate(dto.getFromDate());
            entity.setToDate(dto.getToDate());
            entity.setVmStatus(true);
            entity.setActive(dto.getActive());

            entity.setUpdatedAt(new Date());
            entity.setUpdatedBy(userData.getId());

            return entity;
        } else {
            entity.setFyId(dto.getFyId());
            entity.setVmInfo(dto.getVmInfo());
            entity.setVmInfoBn(dto.getVmInfoBn());
            entity.setSequenceNumber(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            entity.setFromDate(dto.getFromDate());
            entity.setToDate(dto.getToDate());
            entity.setVmStatus(true);
            entity.setActive(dto.getActive());
            entity.setCreatedAt(new Date());
            entity.setCreatedBy(userData.getId());

            return entity;
        }

    }

    private VatMonthResponseDto transferToDTO(VatMonthInfo entity) {
        VatMonthResponseDto dto = new VatMonthResponseDto();
        dto.setId(entity.getId());
        dto.setFyId(entity.getFyId());
        if (entity.getFyId() != null) {
            FiscalYearInfo fiscalYearInfo = fiscalYearInfoRepository.findById(entity.getFyId()).orElse(null);
            dto.setFiscalYear(fiscalYearInfo.getFiscalYear());
            dto.setFiscalYearBn(fiscalYearInfo.getFiscalYearBn());
        }

        dto.setVmInfo(entity.getVmInfo());
        dto.setVmInfoBn(entity.getVmInfoBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSequenceNumber()));
        dto.setFromDate(DateUtil.convertDateToString(entity.getFromDate(), ConstantValue.ONLY_DATE));
        dto.setToDate(DateUtil.convertDateToString(entity.getToDate(), ConstantValue.ONLY_DATE));
        dto.setVmStatus(entity.getVmStatus());
        dto.setActive(entity.isActive());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (entity.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(entity.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }
        return dto;
    }

    public List<HouseKeeping> vatMonthDropDown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_company_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }
}
