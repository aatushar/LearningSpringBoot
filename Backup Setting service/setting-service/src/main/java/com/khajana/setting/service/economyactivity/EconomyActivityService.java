package com.khajana.setting.service.economyactivity;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.economyactivity.EconomyActivityRequestDto;
import com.khajana.setting.dto.economyactivity.EconomyActivityResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.economyactivity.EconomyActivity;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
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
public class EconomyActivityService {
    @Autowired
    EconomyActivityRepository economyActivityRepository;

    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<EconomyActivityResponseDto> findAllEconomyActivitys(Pageable pageable) {

        Page<EconomyActivity> page = economyActivityRepository.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public List<EconomyActivityResponseDto> getDropDown() {
        List<EconomyActivity> page = economyActivityRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public EconomyActivityResponseDto findEconomyActivityById(Long id) {
        EconomyActivity economyActivity = economyActivityRepository.findEconomyActivityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(economyActivity);
    }

    public ApiResponse addEconomyActivity(EconomyActivityRequestDto dto) {
        try {
            boolean typeExists = economyActivityRepository.existsByEconomicActivityName(dto.getEconomicActivityName());

            if (typeExists) {
                return new ApiResponse(400, "economicActivityName already exists", "error");
            }
            EconomyActivity newEntity = economyActivityRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateEconomyActivity(Long id, EconomyActivityRequestDto dto) {
        try {
            boolean nameExistsForOtherId = economyActivityRepository.existsByEconomicActivityNameAndIdNot(dto.getEconomicActivityName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "economicActivityName already exists", "error");
            }
            EconomyActivity newEntity = economyActivityRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private EconomyActivity transferToEntity(Long id, EconomyActivityRequestDto dto) {
        EconomyActivity economyActivity = new EconomyActivity();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            economyActivity.setId(id);
            economyActivity.setEconomicActivityName(dto.getEconomicActivityName());
            economyActivity.setEconomicActivityNameBn(dto.getEconomicActivityNameBn());
            economyActivity.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            economyActivity.setActive(dto.getActive());
            economyActivity.setUpdatedAt(new Date());
            economyActivity.setUpdatedBy(userData.getId());

            return economyActivity;
        } else {
            economyActivity.setEconomicActivityName(dto.getEconomicActivityName());
            economyActivity.setEconomicActivityNameBn(dto.getEconomicActivityNameBn());
            economyActivity.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            economyActivity.setActive(dto.getActive());
            economyActivity.setCreatedAt(new Date());
            economyActivity.setCreatedBy(userData.getId());
            return economyActivity;
        }
    }

    private EconomyActivityResponseDto transferToDTO(EconomyActivity economyActivity) {
        EconomyActivityResponseDto dto = new EconomyActivityResponseDto();
        dto.setId(economyActivity.getId());
        dto.setEconomicActivityName(economyActivity.getEconomicActivityName());
        dto.setEconomicActivityNameBn(economyActivity.getEconomicActivityNameBn());
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
