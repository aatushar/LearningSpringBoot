package com.khajana.setting.service.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.address.PoliceStationRequestDto;
import com.khajana.setting.dto.address.PoliceStationResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.address.District;
import com.khajana.setting.entity.address.PoliceStation;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.address.DistrictRepository;
import com.khajana.setting.repository.address.PoliceStationRepository;
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
public class PoliceStationService {

    @Autowired
    PoliceStationRepository policeStationRepository;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<PoliceStationResponseDto> findAllPoliceStations(Pageable pageable) {
        Page<PoliceStation> page = policeStationRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public PoliceStationResponseDto findPoliceStationById(Long id) {

        PoliceStation newEntity = policeStationRepository.findPoliceStationById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addPoliceStation(PoliceStationRequestDto dto) {
        try {
            PoliceStation newEntity = policeStationRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public PoliceStationResponseDto updatePoliceStation(Long id, PoliceStationRequestDto dto) {
        PoliceStation newEntity = policeStationRepository.save(transferToEntity(id, dto));
        return transferToDTO(newEntity);
    }

    public List<PoliceStationResponseDto> getDropDown() {
        List<PoliceStation> page = policeStationRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deletePoliceStation(Long id) {
        policeStationRepository.deletePoliceStationById(id);
    }

    private PoliceStation transferToEntity(Long id, PoliceStationRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        PoliceStation policeStation = new PoliceStation();
        if (id != null && id > 0) {
            policeStation.setId(id);
            policeStation.setDistrictId(dto.getDistrictId());
            policeStation.setName(dto.getName());
            policeStation.setNameBn(dto.getNameBn());
            policeStation.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            policeStation.setActive(dto.getActive());
            policeStation.setUpdatedAt(new Date());

            return policeStation;
        } else {
            policeStation.setDistrictId(dto.getDistrictId());
            policeStation.setName(dto.getName());
            policeStation.setNameBn(dto.getNameBn());
            policeStation.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            policeStation.setActive(dto.getActive());
            policeStation.setCreatedAt(new Date());
            policeStation.setCreatedBy(userData.getId());

            return policeStation;
        }

    }

    private PoliceStationResponseDto transferToDTO(PoliceStation entity) {
        PoliceStationResponseDto dto = new PoliceStationResponseDto();
        dto.setDistrictId(entity.getDistrictId());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setActive(entity.getActive());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        if (entity.getDistrictId() != null) {
            District district = districtRepository.findById(entity.getDistrictId()).orElse(null);
            dto.setDistrictName(district != null ? district.getName() : null);
        }
        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }
        return dto;
    }
}
