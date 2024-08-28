package com.khajana.setting.service.uom;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.uom.UomRequestDto;
import com.khajana.setting.dto.uom.UomResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.uom.Uom;
import com.khajana.setting.entity.uom.UomSet;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.uom.UomRepository;
import com.khajana.setting.repository.uom.UomSetRepository;
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
public class UomService {

    @Autowired
    UomRepository uomRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    UomSetRepository uomSetRepository;

    public SimplePage<UomResponseDto> findAllUoms(Pageable pageable) {

        Page<Uom> page = uomRepository.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);

        /*
         * final Page<UomResponseDto> page = uomRepository.getJoinInformation(pageable);
         * return new SimplePage<>( page.getContent(), page.getTotalElements(),
         * pageable);
         */
    }

    public List<UomResponseDto> getDropDown() {
        List<Uom> page = uomRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public UomResponseDto findUomById(Long id) {

        Uom uom = uomRepository.findUomById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(uom);

        // UomResponseDto dto = uomRepository.findUomById(id);
        // return dto;
    }

    public ApiResponse addUom(UomRequestDto dto) {
        try {
            boolean typeExists = uomRepository.existsByUomShortCode(dto.getUomShortCode());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate UomShortCode Not Allowed", "error");
            }
            Uom newEntity = uomRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateUom(Long id, UomRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean nameExistsForOtherId = uomRepository.existsByUomShortCodeAndIdNot(dto.getUomShortCode(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate UomShortCode Not Allowed", "error");
            }
            Uom newEntity = uomRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private Uom transferToEntity(Long id, UomRequestDto dto) {
        Uom uom = new Uom();

        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            Uom uoms = uomRepository.findUomById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            uom.setId(uoms.getId());
            uom.setUomSetId(dto.getUomSetId());
            uom.setUomShortCode(dto.getUomShortCode());
            uom.setUomDesc(dto.getUomDesc());
            uom.setLocalDesc(dto.getUomLocalDesc());
            uom.setRelativeFactor(NumberFormat.get2DigitDecimal(dto.getRelativeFactor()));
            uom.setFractionAllow(dto.getFractionAllow());
            uom.setActive(dto.getActive());
            uom.setUpdatedAt(new Date());
            uom.setUpdatedBy(userData.getId());

            return uom;
        } else {
            uom.setUomSetId(dto.getUomSetId());
            uom.setUomShortCode(dto.getUomShortCode());
            uom.setUomDesc(dto.getUomDesc());
            uom.setLocalDesc(dto.getUomLocalDesc());
            uom.setRelativeFactor(NumberFormat.get2DigitDecimal(dto.getRelativeFactor()));
            uom.setFractionAllow(dto.getFractionAllow());
            uom.setActive(dto.getActive());
            uom.setCreatedAt(new Date());
            uom.setCreatedBy(userData.getId());

            return uom;
        }
    }

    private UomResponseDto transferToDTO(Uom uom) {
        UomResponseDto dto = new UomResponseDto();
        dto.setId(uom.getId());
        dto.setUomSetId(uom.getUomSetId());
        if (uom.getUomSetId() != null) {
            UomSet uomSet = uomSetRepository.findUomSetById(uom.getUomSetId()).orElse(null);
            dto.setUomSetName(uomSet != null ? uomSet.getUomSet() : null);
            dto.setUomSetDesc(uomSet != null ? uomSet.getUomSetDesc() : null);
            dto.setUomSetLocalDesc(uomSet != null ? uomSet.getLocalUomSetDesc() : null);
        }
        dto.setUomShortCode(uom.getUomShortCode());
        dto.setUomDesc(uom.getUomDesc());
        dto.setUomLocalDesc(uom.getLocalDesc());
        dto.setRelativeFactor(NumberFormat.get2DigitDecimal(uom.getRelativeFactor()));
        dto.setFractionAllow(uom.getFractionAllow());
        dto.setActive(uom.isActive());
        dto.setCreatedAt(DateUtil.convertDateToString(uom.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(uom.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (uom.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(uom.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (uom.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(uom.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }
        return dto;
    }
}
