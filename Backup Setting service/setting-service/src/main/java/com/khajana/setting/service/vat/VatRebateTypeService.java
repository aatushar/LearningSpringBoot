package com.khajana.setting.service.vat;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.vat.VatRebateTypeRequestDto;
import com.khajana.setting.dto.vat.VatRebateTypeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.vat.VatRebateType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.vat.VatRebateTypeRepo;
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
public class VatRebateTypeService {
    @Autowired
    VatRebateTypeRepo vatRebateTypeRepo;

    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<VatRebateTypeResponseDto> findAllVatRebateTypes(Pageable pageable) {

        Page<VatRebateType> page = vatRebateTypeRepo.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public List<VatRebateTypeResponseDto> findByVatRebateName(String name) {
        List<VatRebateType> result = vatRebateTypeRepo.findByVatRebateNameIgnoreCaseContaining(name);

        return result.stream().map(this::transferToDTO).collect(Collectors.toList());
    }


    public List<VatRebateTypeResponseDto> getDropDown() {
        List<VatRebateType> page = vatRebateTypeRepo.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public VatRebateTypeResponseDto findVatRebateTypeById(Long id) {
        VatRebateType vatRebateType = vatRebateTypeRepo.findVatRebateTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(vatRebateType);
    }

    //    public ApiResponse addVatRebateType(VatRebateTypeRequestDto dto) {
//        try {
//            boolean typeExists = vatRebateTypeRepo.existsByVatRebateName(dto.getVatRebateName());
//
//            if (typeExists) {
//                return new ApiResponse(400, "Not VatRebateName  ", "error");
//            }
//            boolean seqNoExists = vatRebateTypeRepo.existsBySeqNo(dto.getSeqNo());
//            if (seqNoExists) {
//                return new ApiResponse(400, "SeqNo already exists", "error");
//            }
//
//            VatRebateType newEntity = vatRebateTypeRepo.save(transferToEntity(0L, dto));
//            return new ApiResponse(200, "ok", transferToDTO(newEntity));
//        } catch (Exception e) {
//            return new ApiResponse(500, e.getMessage(), "error");
//        }
//    }
    public ApiResponse addVatRebateType(VatRebateTypeRequestDto dto) {
        try {
            boolean typeExists = vatRebateTypeRepo.existsByVatRebateName(dto.getVatRebateName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate VAT Rebate name not allowed", "error");
            }


            VatRebateType newEntity = vatRebateTypeRepo.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateVatRebateType(Long id, VatRebateTypeRequestDto dto) {
        try {
            boolean nameExistsForOtherId = vatRebateTypeRepo.existsByVatRebateNameAndIdNot(dto.getVatRebateName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate VAT Rebate name not allowed", "error");
            }

            VatRebateType newEntity = vatRebateTypeRepo.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    private VatRebateType transferToEntity(Long id, VatRebateTypeRequestDto dto) {
        VatRebateType vatRebateType = new VatRebateType();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            VatRebateType vatRebateTypes = vatRebateTypeRepo.findVatRebateTypeById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            vatRebateType.setId(vatRebateTypes.getId());
            vatRebateType.setVatRebateName(dto.getVatRebateName());
            vatRebateType.setVatRebateNameBn(dto.getVatRebateNameBn());
            vatRebateType.setSeqNo(dto.getSeqNo());
            vatRebateType.setActive(dto.getActive());
            vatRebateType.setUpdatedAt(new Date());
            vatRebateType.setUpdatedBy(userData.getId());

            return vatRebateType;
        } else {
            vatRebateType.setVatRebateName(dto.getVatRebateName());
            vatRebateType.setVatRebateNameBn(dto.getVatRebateNameBn());
            vatRebateType.setSeqNo(dto.getSeqNo());
            vatRebateType.setActive(dto.getActive());
            vatRebateType.setCreatedAt(new Date());
            vatRebateType.setCreatedBy(userData.getId());
            return vatRebateType;
        }
    }

    private VatRebateTypeResponseDto transferToDTO(VatRebateType entity) {
        VatRebateTypeResponseDto dto = new VatRebateTypeResponseDto();
        dto.setId(entity.getId());
        dto.setVatRebateName(entity.getVatRebateName());
        dto.setVatRebateNameBn(entity.getVatRebateNameBn());
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
