package com.khajana.setting.service.vehicletype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.vehicletype.VehicleTypeRequestDto;
import com.khajana.setting.dto.vehicletype.VehicleTypeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.vehicletype.VehicleType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.vehicletype.VehicleTypeRepository;
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
public class VehicleTypeService {

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<VehicleTypeResponseDto> findAllVehicleTypes(Pageable pageable) {
        Page<VehicleType> page = vehicleTypeRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public VehicleTypeResponseDto findVehicleTypeById(Long id) {

        VehicleType newEntity = vehicleTypeRepository.findVehicleTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addVehicleType(VehicleTypeRequestDto dto) {
        try {
            boolean typeExists = vehicleTypeRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Vehicle Type Name not allowed", "error");
            }
            VehicleType newEntity = vehicleTypeRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateVehicleType(Long id, VehicleTypeRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = vehicleTypeRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Vehicle Type Name not allowed", "error");
            }
            VehicleType newEntity = vehicleTypeRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<VehicleTypeResponseDto> getDropDown() {
        List<VehicleType> page = vehicleTypeRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteVehicleType(Long id) {
        vehicleTypeRepository.deleteVehicleTypeById(id);
    }

    private VehicleType transferToEntity(Long id, VehicleTypeRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        VehicleType vehicleType = new VehicleType();
        if (id != null && id > 0) {
            vehicleType.setId(id);
            vehicleType.setName(dto.getName());
            vehicleType.setNameBn(dto.getNameBn());
            vehicleType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            vehicleType.setActive(dto.getActive());
            vehicleType.setUpdatedAt(new Date());
            vehicleType.setUpdatedBy(userData.getId());
            return vehicleType;
        } else {
            vehicleType.setName(dto.getName());
            vehicleType.setNameBn(dto.getNameBn());
            vehicleType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            vehicleType.setActive(dto.getActive());
            vehicleType.setCreatedAt(new Date());
            vehicleType.setCreatedBy(userData.getId());
            return vehicleType;
        }

    }

    private VehicleTypeResponseDto transferToDTO(VehicleType entity) {
        VehicleTypeResponseDto dto = new VehicleTypeResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setActive(entity.getActive());
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
}
