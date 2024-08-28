package com.khajana.setting.service.vat;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.vat.VatRegistrationTypeRequestDto;
import com.khajana.setting.dto.vat.VatRegistrationTypeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.vat.VatRegistrationType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.vat.VatRegistrationTypeRepository;
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
public class VatRegistrationTypeService {
    @Autowired
    VatRegistrationTypeRepository vatRegistrationTypeRepository;

    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<VatRegistrationTypeResponseDto> findAllVatRegistrationTypes(Pageable pageable) {

        Page<VatRegistrationType> page = vatRegistrationTypeRepository.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);

    }

    public List<VatRegistrationTypeResponseDto> getDropDown() {
        List<VatRegistrationType> page = vatRegistrationTypeRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public VatRegistrationTypeResponseDto findVatRegistrationTypeById(Long id) {
        VatRegistrationType vatRegistrationType = vatRegistrationTypeRepository.findVatRegistrationTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(vatRegistrationType);
    }

    public ApiResponse addVatRegistrationType(VatRegistrationTypeRequestDto dto) {
        try {
            boolean typeExists = vatRegistrationTypeRepository.existsByVatRegistrationName(dto.getVatRegistrationName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate VatRegistrationName not allowed", "error");
            }
            VatRegistrationType newEntity = vatRegistrationTypeRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateVatRegistrationType(Long id, VatRegistrationTypeRequestDto dto) {
        try {
            boolean nameExistsForOtherId = vatRegistrationTypeRepository.existsByVatRegistrationNameAndIdNot(dto.getVatRegistrationName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate VatRegistrationName not allowed", "error");
            }
            VatRegistrationType newEntity = vatRegistrationTypeRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private VatRegistrationType transferToEntity(Long id, VatRegistrationTypeRequestDto dto) {
        VatRegistrationType vatRegistrationType = new VatRegistrationType();
        CustomUserDetails userData = ContextUser.getLoginUserData();
        if (id != null && id > 0) {
            vatRegistrationType.setId(id);

            vatRegistrationType.setVatRegistrationName(dto.getVatRegistrationName());
            vatRegistrationType.setVatRegistrationNameBn(dto.getVatRegistrationNameBn());
            vatRegistrationType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            if (dto.getReqBin() != null) {

                vatRegistrationType.setReqBin(dto.getReqBin());
            } else {
                vatRegistrationType.setReqBin(null);

            }
            vatRegistrationType.setActive(dto.getActive());
            vatRegistrationType.setUpdatedAt(new Date());
            vatRegistrationType.setUpdatedBy(userData.getId());

            return vatRegistrationType;

        } else {
            vatRegistrationType.setVatRegistrationName(dto.getVatRegistrationName());
            vatRegistrationType.setVatRegistrationNameBn(dto.getVatRegistrationNameBn());
            vatRegistrationType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            if (dto.getReqBin() != null) {

                vatRegistrationType.setReqBin(dto.getReqBin());
            } else {
                vatRegistrationType.setReqBin(null);

            }
            vatRegistrationType.setActive(dto.getActive());
            vatRegistrationType.setCreatedAt(new Date());
            vatRegistrationType.setCreatedBy(userData.getId());
            return vatRegistrationType;
        }
    }

    private VatRegistrationTypeResponseDto transferToDTO(VatRegistrationType vatRegistrationType) {
        VatRegistrationTypeResponseDto dto = new VatRegistrationTypeResponseDto();
        dto.setId(vatRegistrationType.getId());
        dto.setVatRegistrationName(vatRegistrationType.getVatRegistrationName());
        dto.setVatRegistrationNameBn(vatRegistrationType.getVatRegistrationNameBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(vatRegistrationType.getSeqNo()));
        dto.setActive(vatRegistrationType.getActive());
        dto.setReqBin(vatRegistrationType.getReqBin());
        dto.setCreatedAt(
                DateUtil.convertDateToString(vatRegistrationType.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(
                DateUtil.convertDateToString(vatRegistrationType.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

        if (vatRegistrationType.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(vatRegistrationType.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (vatRegistrationType.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(vatRegistrationType.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }

        return dto;
    }
}
