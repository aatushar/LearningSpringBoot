package com.khajana.setting.service.economyactivity;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.economyactivity.EconomicActivityAreaRequestDto;
import com.khajana.setting.dto.economyactivity.EconomicActivityAreaResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.economyactivity.EconomicActivityAreaEntity2;
import com.khajana.setting.entity.economyactivity.EconomyActivity;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.economyactivity.EconomyActivityAreaRepository;
import com.khajana.setting.repository.economyactivity.EconomyActivityRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EconomicActivityAreaService {
    @Autowired
    EconomyActivityAreaRepository economyActivityAreaRepository;
    @Autowired
    EconomyActivityRepository economyActivityRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<EconomicActivityAreaResponseDto> findAllEconomicActivityArea(Pageable pageable) {

        Page<EconomicActivityAreaEntity2> page = economyActivityAreaRepository.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public List<EconomicActivityAreaResponseDto> getDropDown() {
        List<EconomicActivityAreaEntity2> page = economyActivityAreaRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public EconomicActivityAreaResponseDto findEconomyActivityAreaById(Long id) {
        EconomicActivityAreaEntity2 economyActivity = economyActivityAreaRepository.findEconomyActivityAreaById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(economyActivity);
    }

    public ApiResponse addEconomicActivityArea(EconomicActivityAreaRequestDto dto) {
        try {
            boolean typeExists = economyActivityAreaRepository.existsByEconomicActivityAreaName(dto.getEconomicActivityAreaName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Area of Economic Activity Name is not allowed", "error");
            }
            EconomicActivityAreaEntity2 newEntity = economyActivityAreaRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateEconomyActivity(Long id, EconomicActivityAreaRequestDto dto) {
        try {
            boolean nameExistsForOtherId = economyActivityAreaRepository.existsByEconomicActivityAreaNameAndIdNot(dto.getEconomicActivityAreaName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Area of Economic Activity Name is not allowed", "error");
            }
            EconomicActivityAreaEntity2 newEntity = economyActivityAreaRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private EconomicActivityAreaEntity2 transferToEntity(Long id, EconomicActivityAreaRequestDto dto) {
        EconomicActivityAreaEntity2 economyActivity = new EconomicActivityAreaEntity2();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            EconomicActivityAreaEntity2 economy = economyActivityAreaRepository.findEconomyActivityAreaById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            economyActivity.setId(economy.getId());
            economy.setEconomicActivityId(dto.getEconomicActivityId());
            economy.setEconomicActivityAreaName(dto.getEconomicActivityAreaName());
            economy.setEconomicActivityAreaNameBn(dto.getEconomicActivityAreaNameBn());
            economy.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            economy.setActive(dto.getActive());
            economy.setUpdatedAt(new Date());
            economy.setUpdatedBy(userData.getId());

            return economy;
        } else {
            economyActivity.setEconomicActivityId(dto.getEconomicActivityId());
            economyActivity.setEconomicActivityAreaName(dto.getEconomicActivityAreaName());
            economyActivity.setEconomicActivityAreaNameBn(dto.getEconomicActivityAreaNameBn());
            economyActivity.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            economyActivity.setActive(dto.getActive());
            economyActivity.setCreatedAt(new Date());
            economyActivity.setCreatedBy(userData.getId());
            return economyActivity;
        }
    }

    private EconomicActivityAreaResponseDto transferToDTO(EconomicActivityAreaEntity2 economyActivity) {
        EconomicActivityAreaResponseDto dto = new EconomicActivityAreaResponseDto();
        dto.setId(economyActivity.getId());
        dto.setEconomicActivityId(economyActivity.getEconomicActivityId());
//        if (economyActivity.getEconomyActivities() != null) {
////            TransactionSourceType transactionSourceType = transactionSourceTypeRepository
////                    .findById(entity.getSourceTypeId()).orElse(null);
//
//            dto.setEconomicActivityName(economyActivity.getEconomyActivities().getEconomicActivityName());
//        }
        if (economyActivity.getEconomicActivityId() != null) {
            EconomyActivity transactionSourceType = economyActivityRepository
                    .findById(economyActivity.getEconomicActivityId()).orElse(null);

            dto.setEconomicActivityName(transactionSourceType.getEconomicActivityName());
            // dto.set(transactionSourceType.getTranSourceTypeNameBN());
        }

        dto.setEconomicActivityAreaName(economyActivity.getEconomicActivityAreaName());
        dto.setEconomicActivityAreaNameBn(economyActivity.getEconomicActivityAreaNameBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(economyActivity.getSeqNo()));
        dto.setActive(economyActivity.getActive());
        dto.setCreatedAt(
                DateUtil.convertDateToString(economyActivity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(
                DateUtil.convertDateToString(economyActivity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        ;

        if (economyActivity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(economyActivity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (economyActivity.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(economyActivity.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }

        return dto;
    }
}
