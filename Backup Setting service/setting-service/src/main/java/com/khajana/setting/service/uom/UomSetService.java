package com.khajana.setting.service.uom;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.uom.UomSetRequestDto;
import com.khajana.setting.dto.uom.UomSetResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.uom.UomSet;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.uom.UomSetRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.ConstantValue;
import com.khajana.setting.utils.ContextUser;
import com.khajana.setting.utils.DateUtil;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class UomSetService {
    @Autowired
    UomSetRepository uomSetRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    /*
     * public Page<UomSetDto> findAllUomSets(Pageable pageable) { Page<UomSet> page
     * = uomSetRepository.findAll(pageable); return page.map(this::transferToDTO); }
     */

    public SimplePage<UomSetResponseDto> findAllUomSets(Pageable pageable) {
        Page<UomSet> page = uomSetRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public UomSetResponseDto findUomSetById(Long id) {
        UomSet uomSet = uomSetRepository.findUomSetById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(uomSet);
    }

    //    public UomSetDto addUomSet(UomSetDto dto) {
//        UomSet uomSet = uomSetRepository.save(transferToEntity(dto));
//        return transferToDTO(uomSet);
//    }
    public ApiResponse addUomSet(UomSetRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = uomSetRepository.existsByUomSet(dto.getUomSet());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate UOM Set not allowed", "error");
            }
            UomSet newEntity = uomSetRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public UomSetResponseDto updateUomSet(Long id,UomSetRequestDto dto) {
//        UomSet uomSet = uomSetRepository.save(transferToEntity(id,dto));
//        return transferToDTO(uomSet);
//    }
    public ApiResponse updateUomSet(Long id, UomSetRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean nameExistsForOtherId = uomSetRepository.existsByUomSetAndIdNot(dto.getUomSet(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate UOM Set not allowed", "error");
            }
            UomSet newEntity = uomSetRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private UomSet transferToEntity(Long id, UomSetRequestDto dto) {
        UomSet uomSet = new UomSet();

        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            UomSet uomSets = uomSetRepository.findUomSetById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            uomSet.setId(uomSets.getId());
            uomSet.setUomSet(dto.getUomSet());
            uomSet.setUomSetDesc(dto.getUomSetDesc());
            uomSet.setLocalUomSetDesc(dto.getLocalUomSetDesc());
            uomSet.setActive(dto.getActive());
            uomSet.setUpdatedAt(new Date());
            uomSet.setUpdatedBy(userData.getId());

            return uomSet;
        } else {
            uomSet.setUomSet(dto.getUomSet());
            uomSet.setUomSetDesc(dto.getUomSetDesc());
            uomSet.setLocalUomSetDesc(dto.getLocalUomSetDesc());
            uomSet.setActive(dto.getActive());
            uomSet.setCreatedAt(new Date());
            uomSet.setCreatedBy(userData.getId());

            return uomSet;
        }
    }

    private UomSetResponseDto transferToDTO(UomSet uomSet) {
        UomSetResponseDto dto = new UomSetResponseDto();
        dto.setId(uomSet.getId());
        dto.setUomSet(uomSet.getUomSet());
        dto.setUomSetDesc(uomSet.getUomSetDesc());
        dto.setLocalUomSetDesc(uomSet.getLocalUomSetDesc());
        dto.setActive(uomSet.getActive());
        dto.setCreatedAt(DateUtil.convertDateToString(uomSet.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(uomSet.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (uomSet.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(uomSet.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (uomSet.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(uomSet.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }

        return dto;
    }
}
