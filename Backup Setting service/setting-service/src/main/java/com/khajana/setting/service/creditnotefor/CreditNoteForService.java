package com.khajana.setting.service.creditnotefor;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.creditnotefor.CreditNoteForRequestDto;
import com.khajana.setting.dto.creditnotefor.CreditNoteForResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.creditnotefor.CreditNoteFor;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.creditnotefor.CreditNoteForRepository;
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
public class CreditNoteForService {

    @Autowired
    CreditNoteForRepository creditNoteForRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<CreditNoteForResponseDto> findAllCreditNoteFors(Pageable pageable) {
        Page<CreditNoteFor> page = creditNoteForRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CreditNoteForResponseDto findCreditNoteForById(Long id) {

        CreditNoteFor newEntity = creditNoteForRepository.findCreditNoteForById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCreditNoteFor(CreditNoteForRequestDto dto) {
        try {
            boolean typeExists = creditNoteForRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Credit Note for Name not allowed", "error");
            }
            CreditNoteFor newEntity = creditNoteForRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public CreditNoteForResponseDto updateCreditNoteFor(Long id, CreditNoteForRequestDto dto) {
//        CreditNoteFor newEntity = creditNoteForRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateCreditNoteFor(Long id, CreditNoteForRequestDto dto) {
        // Read user id from JWT Token
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = creditNoteForRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Credit Note for Name not allowed", "error");
            }
            CreditNoteFor newEntity = creditNoteForRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CreditNoteForResponseDto> getDropDown() {
        List<CreditNoteFor> page = creditNoteForRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCreditNoteFor(Long id) {
        creditNoteForRepository.deleteCreditNoteForById(id);
    }

    private CreditNoteFor transferToEntity(Long id, CreditNoteForRequestDto dto) {
        CreditNoteFor creditNoteFor = new CreditNoteFor();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            CreditNoteFor newEntity = creditNoteForRepository.findCreditNoteForById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            creditNoteFor.setId(newEntity.getId());
            creditNoteFor.setName(dto.getName());
            creditNoteFor.setNameBn(dto.getNameBn());
            creditNoteFor.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            creditNoteFor.setActive(dto.getActive());

            creditNoteFor.setUpdatedAt(new Date());
            creditNoteFor.setUpdatedBy(userData.getId());

            return creditNoteFor;
        } else {
            creditNoteFor.setName(dto.getName());
            creditNoteFor.setNameBn(dto.getNameBn());
            creditNoteFor.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            creditNoteFor.setActive(dto.getActive());
            creditNoteFor.setCreatedAt(new Date());
            creditNoteFor.setCreatedBy(userData.getId());

            return creditNoteFor;
        }

    }

    private CreditNoteForResponseDto transferToDTO(CreditNoteFor entity) {
        CreditNoteForResponseDto dto = new CreditNoteForResponseDto();
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
