package com.khajana.setting.service.adjt;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.adjt.OtherAdjtRequestDto;
import com.khajana.setting.dto.adjt.OtherAdjtResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.adjt.OtherAdjt;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.adjt.OtherAdjtRepository;
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
public class OtherAdjtService {

    @Autowired
    OtherAdjtRepository otherAdjtRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<OtherAdjtResponseDto> findAllOtherAdjts(Pageable pageable) {
        Page<OtherAdjt> page = otherAdjtRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public OtherAdjtResponseDto findOtherAdjtById(Long id) {

        OtherAdjt newEntity = otherAdjtRepository.findOtherAdjtById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addOtherAdjt(OtherAdjtRequestDto dto) {
        try {

            boolean typeExists = otherAdjtRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Others Adjt Name not allowed", "error");
            }
            OtherAdjt newEntity = otherAdjtRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateOtherAdjt(Long id, OtherAdjtRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = otherAdjtRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Others Adjt  Name not allowed", "error");
            }
            OtherAdjt newEntity = otherAdjtRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<OtherAdjtResponseDto> getDropDown() {
        List<OtherAdjt> page = otherAdjtRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteOtherAdjt(Long id) {
        otherAdjtRepository.deleteOtherAdjtById(id);
    }

    private OtherAdjt transferToEntity(Long id, OtherAdjtRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        OtherAdjt otherAdjt = new OtherAdjt();
        if (id != null && id > 0) {
            OtherAdjt newEntity = otherAdjtRepository.findOtherAdjtById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            otherAdjt.setId(newEntity.getId());
            otherAdjt.setName(dto.getName());
            otherAdjt.setNameBn(dto.getNameBn());
            otherAdjt.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            otherAdjt.setActive(dto.getActive());
            otherAdjt.setUpdatedAt(new Date());
            otherAdjt.setUpdatedBy(userData.getId());
            return otherAdjt;
        } else {
            otherAdjt.setName(dto.getName());
            otherAdjt.setNameBn(dto.getNameBn());
            otherAdjt.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            otherAdjt.setActive(dto.getActive());
            otherAdjt.setCreatedAt(new Date());
            otherAdjt.setCreatedBy(userData.getId());
            return otherAdjt;
        }

    }

    private OtherAdjtResponseDto transferToDTO(OtherAdjt entity) {
        OtherAdjtResponseDto dto = new OtherAdjtResponseDto();
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
