package com.khajana.setting.service.port;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.port.PortInfoRequestDto;
import com.khajana.setting.dto.port.PortInfoResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.port.PortInfo;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.port.PortInfoRepository;
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
public class PortInfoService {

    @Autowired
    PortInfoRepository portInfoRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<PortInfoResponseDto> findAllPortInfos(Pageable pageable) {
        Page<PortInfo> page = portInfoRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public PortInfoResponseDto findPortInfoById(Long id) {

        PortInfo newEntity = portInfoRepository.findPortInfoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addPortInfo(PortInfoRequestDto dto) {
        try {
            boolean typeExists = portInfoRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Port Name not allowed", "error");
            }
            PortInfo newEntity = portInfoRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public PortInfoResponseDto updatePortInfo(Long id, PortInfoRequestDto dto) {
//        PortInfo newEntity = portInfoRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updatePortInfo(Long id, PortInfoRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = portInfoRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Port  Name not allowed", "error");
            }
            PortInfo newEntity = portInfoRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<PortInfoResponseDto> getDropDown() {
        List<PortInfo> page = portInfoRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deletePortInfo(Long id) {
        portInfoRepository.deletePortInfoById(id);
    }

    private PortInfo transferToEntity(Long id, PortInfoRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        PortInfo portInfo = new PortInfo();
        if (id != null && id > 0) {
            PortInfo newEntity = portInfoRepository.findPortInfoById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            portInfo.setId(newEntity.getId());
            portInfo.setName(dto.getName());
            portInfo.setNameBn(dto.getNameBn());
            portInfo.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            portInfo.setActive(dto.getActive());
            portInfo.setUpdatedAt(new Date());
            portInfo.setUpdatedBy(userData.getId());

            return portInfo;
        } else {
            portInfo.setName(dto.getName());
            portInfo.setNameBn(dto.getNameBn());
            portInfo.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            portInfo.setActive(dto.getActive());
            portInfo.setCreatedAt(new Date());
            portInfo.setCreatedBy(userData.getId());

            return portInfo;
        }

    }

    private PortInfoResponseDto transferToDTO(PortInfo entity) {
        PortInfoResponseDto dto = new PortInfoResponseDto();
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
