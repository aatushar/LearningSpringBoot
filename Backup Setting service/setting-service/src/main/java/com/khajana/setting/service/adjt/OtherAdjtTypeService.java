package com.khajana.setting.service.adjt;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.adjt.OtherAdjtTypeRequestDto;
import com.khajana.setting.dto.adjt.OtherAdjtTypeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.adjt.OtherAdjtType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.adjt.OtherAdjtTypeRepository;
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
public class OtherAdjtTypeService {

    @Autowired
    OtherAdjtTypeRepository otherAdjtTypeRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<OtherAdjtTypeResponseDto> findAllOtherAdjtTypes(Pageable pageable) {
        Page<OtherAdjtType> page = otherAdjtTypeRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public OtherAdjtTypeResponseDto findOtherAdjtTypeById(Long id) {

        OtherAdjtType newEntity = otherAdjtTypeRepository.findOtherAdjtTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addOtherAdjtType(OtherAdjtTypeRequestDto dto) {
        try {
            boolean typeExists = otherAdjtTypeRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Others Adjt Type Name not allowed", "error");
            }
            OtherAdjtType newEntity = otherAdjtTypeRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public OtherAdjtTypeResponseDto updateOtherAdjtType(Long id, OtherAdjtTypeRequestDto dto) {
//        OtherAdjtType newEntity = otherAdjtTypeRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateOtherAdjtType(Long id, OtherAdjtTypeRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = otherAdjtTypeRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Others Adjt Type   Name not allowed", "error");
            }
            OtherAdjtType newEntity = otherAdjtTypeRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<OtherAdjtTypeResponseDto> getDropDown() {
        List<OtherAdjtType> page = otherAdjtTypeRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteOtherAdjtType(Long id) {
        otherAdjtTypeRepository.deleteOtherAdjtTypeById(id);
    }

    private OtherAdjtType transferToEntity(Long id, OtherAdjtTypeRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        OtherAdjtType otherAdjtType = new OtherAdjtType();
        if (id != null && id > 0) {
            OtherAdjtType newEntity = otherAdjtTypeRepository.findOtherAdjtTypeById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            otherAdjtType.setId(newEntity.getId());
            otherAdjtType.setName(dto.getName());
            otherAdjtType.setNameBn(dto.getNameBn());
            otherAdjtType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            otherAdjtType.setActive(dto.getActive());
            otherAdjtType.setUpdatedAt(new Date());
            otherAdjtType.setUpdatedBy(userData.getId());

            return otherAdjtType;
        } else {
            otherAdjtType.setName(dto.getName());
            otherAdjtType.setNameBn(dto.getNameBn());
            otherAdjtType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            otherAdjtType.setActive(dto.getActive());
            otherAdjtType.setCreatedAt(new Date());
            otherAdjtType.setCreatedBy(userData.getId());

            return otherAdjtType;
        }

    }

    private OtherAdjtTypeResponseDto transferToDTO(OtherAdjtType entity) {
        OtherAdjtTypeResponseDto dto = new OtherAdjtTypeResponseDto();
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
