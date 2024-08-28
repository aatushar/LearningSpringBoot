package com.khajana.setting.service.policestation;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.policestation.PoliceStationResponseDto;
import com.khajana.setting.dto.policestation.PoliceStationsRequestDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.policestation.Districts;
import com.khajana.setting.entity.policestation.PoliceStationEntity;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.policestation.DistrictsRepository;
import com.khajana.setting.repository.policestation.PoliceStationsRepository;
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
public class PoliceStationServices {
    @Autowired
    PoliceStationsRepository policeStationRepository;
    @Autowired
    DistrictsRepository districtsRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<PoliceStationResponseDto> findAllPoliceStation(Pageable pageable) {

        Page<PoliceStationEntity> page = policeStationRepository.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public List<PoliceStationResponseDto> getDropDown() {
        List<PoliceStationEntity> page = policeStationRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public PoliceStationResponseDto findPoliceStationById(Long id) {
        PoliceStationEntity economyActivity = policeStationRepository.findPolicestationById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(economyActivity);
    }

    public ApiResponse addPoliceStation(PoliceStationsRequestDto dto) {
        try {
            boolean typeExists = policeStationRepository.existsByPsName(dto.getPsName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Area of Police Station  Name not allowed", "error");
            }
            PoliceStationEntity newEntity = policeStationRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updatePoliceStation(Long id, PoliceStationsRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = policeStationRepository.existsByPsNameAndIdNot(dto.getPsName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Area of Police Station  Name not allowed", "error");
            }
            PoliceStationEntity newEntity = policeStationRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private PoliceStationEntity transferToEntity(Long id, PoliceStationsRequestDto dto) {
        PoliceStationEntity economyActivity = new PoliceStationEntity();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            PoliceStationEntity police = policeStationRepository.findPolicestationById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

            police.setDistrictId(dto.getDistrictId());
            police.setPsName(dto.getPsName());
            police.setPsNameBn(dto.getPsNameBn());
            police.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            police.setActive(dto.getActive());
            police.setUpdatedAt(new Date());
            police.setUpdatedBy(userData.getId());

            return police;
        } else {
            economyActivity.setDistrictId(dto.getDistrictId());
            economyActivity.setPsName(dto.getPsName());
            economyActivity.setPsNameBn(dto.getPsNameBn());
            economyActivity.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            economyActivity.setActive(dto.getActive());
            economyActivity.setCreatedAt(new Date());
            economyActivity.setCreatedBy(userData.getId());
            return economyActivity;
        }
    }

    private PoliceStationResponseDto transferToDTO(PoliceStationEntity economyActivity) {
        PoliceStationResponseDto dto = new PoliceStationResponseDto();
        dto.setId(economyActivity.getId());
        dto.setDistrictId(economyActivity.getDistrictId());

        if (economyActivity.getDistrictId() != null) {
            Districts transactionSourceType = districtsRepository
                    .findById(economyActivity.getDistrictId()).orElse(null);

            dto.setDistrictName(transactionSourceType.getName());
            // dto.set(transactionSourceType.getTranSourceTypeNameBN());
        }

        dto.setPsName(economyActivity.getPsName());
        dto.setPsNameBn(economyActivity.getPsNameBn());
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
