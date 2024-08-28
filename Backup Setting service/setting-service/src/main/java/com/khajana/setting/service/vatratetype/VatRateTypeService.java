package com.khajana.setting.service.vatratetype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.vatratetype.VatRateTypeRequestDto;
import com.khajana.setting.dto.vatratetype.VatRateTypeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.vatratetype.VatRateType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.vatratetype.VatRateTypeRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.ContextUser;
import com.khajana.setting.utils.NumberFormat;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VatRateTypeService {
    @Autowired
    VatRateTypeRepository vatRateTypeRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<VatRateTypeResponseDto> findAllVatRateTypes(Pageable pageable) {

        Page<VatRateType> page = vatRateTypeRepository.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public List<VatRateTypeResponseDto> getDropDown() {
        List<VatRateType> page = vatRateTypeRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public VatRateTypeResponseDto findVatRateTypeById(Long id) {
        VatRateType vatRateType = vatRateTypeRepository.findVatRateTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(vatRateType);
    }

    public ApiResponse addVatRateType(VatRateTypeRequestDto dto) {
        try {
            boolean typeExists = vatRateTypeRepository.existsByVatRateTypeName(dto.getVatRateTypeName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate VAT Rate Type Name not allowed ", "error");
            }
            VatRateType newEntity = vatRateTypeRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateVatRateType(Long id, VatRateTypeRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = vatRateTypeRepository.existsByVatRateTypeNameAndIdNot(dto.getVatRateTypeName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate VAT Rate Type Name not allowed", "error");
            }
            VatRateType newEntity = vatRateTypeRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private VatRateType transferToEntity(Long id, VatRateTypeRequestDto dto) {
        VatRateType vatRateType = new VatRateType();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            VatRateType vatRateTypes = vatRateTypeRepository.findVatRateTypeById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            vatRateType.setId(vatRateTypes.getId());
            vatRateType.setVatRateTypeName(dto.getVatRateTypeName());
            vatRateType.setVatRateTypeNameBn(dto.getVatRateTypeNameBn());
            vatRateType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            vatRateType.setActive(dto.getActive());
            vatRateType.setUpdatedAt(new Date());
            vatRateType.setUpdatedBy(userData.getId());

            return vatRateType;
        } else {
            vatRateType.setVatRateTypeName(dto.getVatRateTypeName());
            vatRateType.setVatRateTypeNameBn(dto.getVatRateTypeNameBn());
            vatRateType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            vatRateType.setActive(dto.getActive());
            vatRateType.setCreatedAt(new Date());
            vatRateType.setCreatedBy(userData.getId());
            return vatRateType;
        }
    }

    private VatRateTypeResponseDto transferToDTO(VatRateType vatRateType) {
        VatRateTypeResponseDto dto = new VatRateTypeResponseDto();
        dto.setId(vatRateType.getId());
        dto.setVatRateTypeName(vatRateType.getVatRateTypeName());
        dto.setVatRateTypeNameBn(vatRateType.getVatRateTypeNameBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(vatRateType.getSeqNo()));
        dto.setActive(vatRateType.getActive());
        dto.setCreatedAt(vatRateType.getCreatedAt());
        dto.setUpdatedAt(vatRateType.getUpdatedAt());
        if (vatRateType.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(vatRateType.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (vatRateType.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(vatRateType.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }
        return dto;
    }
}
