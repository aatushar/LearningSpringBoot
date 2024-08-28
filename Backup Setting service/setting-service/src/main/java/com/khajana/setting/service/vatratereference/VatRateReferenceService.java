package com.khajana.setting.service.vatratereference;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.vatratereference.VatRateReferenceReqDto;
import com.khajana.setting.dto.vatratereference.VatRateReferenceResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.vatratereference.VatRateReference;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.vatratereference.VatRateReferenceRepository;
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
public class VatRateReferenceService {

    @Autowired
    VatRateReferenceRepository vatRateReferenceRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<VatRateReferenceResponseDto> findAllVatRateReferences(Pageable pageable) {
        Page<VatRateReference> page = vatRateReferenceRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public VatRateReferenceResponseDto findVatRateReferenceById(Long id) {

        VatRateReference newEntity = vatRateReferenceRepository.findVatRateReferenceById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

//    public VatRateReferenceDto addVatRateReference(VatRateReferenceDto dto) {
//        VatRateReference newEntity = vatRateReferenceRepository.save(transferToEntity(dto));
//        return  transferToDTO(newEntity);
//    }

    public ApiResponse addVatRateReference(VatRateReferenceReqDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = vatRateReferenceRepository.existsByVatRateRefName(dto.getVatRateRefName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate VAT Rate Reference Name not allowed", "error");
            }
            VatRateReference newEntity = vatRateReferenceRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateVatRateReference(Long id, VatRateReferenceReqDto dto) {
        try {
            boolean nameExistsForOtherId = vatRateReferenceRepository.existsByVatRateRefNameAndIdNot(dto.getVatRateRefName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate VAT Rate Reference Name not allowed", "error");
            }
            VatRateReference newEntity = vatRateReferenceRepository.save(transferToEntity(id, dto));

            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<VatRateReferenceResponseDto> getDropDown() {
        List<VatRateReference> page = vatRateReferenceRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteVatRateReference(Long id) {
        vatRateReferenceRepository.deleteVatRateReferenceById(id);
    }

    private VatRateReference transferToEntity(Long id, VatRateReferenceReqDto dto) {
        VatRateReference vatRegSource = new VatRateReference();

        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            VatRateReference newEntity = vatRateReferenceRepository.findVatRateReferenceById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            vatRegSource.setId(newEntity.getId());
            vatRegSource.setVatRateRefName(dto.getVatRateRefName());
            vatRegSource.setVatRateRefNameBn(dto.getVatRateRefNameBn());
            vatRegSource.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            vatRegSource.setActive(dto.getActive());
            vatRegSource.setUpdatedAt(new Date());
            vatRegSource.setUpdatedBy(userData.getId());

            return vatRegSource;
        }
        vatRegSource.setVatRateRefName(dto.getVatRateRefName());
        vatRegSource.setVatRateRefNameBn(dto.getVatRateRefNameBn());
        vatRegSource.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
        vatRegSource.setActive(dto.getActive());
        vatRegSource.setCreatedAt(new Date());
        vatRegSource.setCreatedBy(userData.getId());

        return vatRegSource;
    }

    private VatRateReferenceResponseDto transferToDTO(VatRateReference entity) {
        VatRateReferenceResponseDto dto = new VatRateReferenceResponseDto();
        dto.setId(entity.getId());
        dto.setVatRateRefName(entity.getVatRateRefName());
        dto.setVatRateRefNameBn(entity.getVatRateRefNameBn());
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
