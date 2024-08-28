package com.khajana.setting.service.debitnotefor;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.debitnotefor.DebitNoteForRequestDto;
import com.khajana.setting.dto.debitnotefor.DebitNoteForResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.debitnotefor.DebitNoteFor;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.debitnotefor.DebitNoteForRepository;
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
public class DebitNoteForService {

    @Autowired
    DebitNoteForRepository debitNoteForRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<DebitNoteForResponseDto> findAllDebitNoteFors(Pageable pageable) {
        Page<DebitNoteFor> page = debitNoteForRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public DebitNoteForResponseDto findDebitNoteForById(Long id) {

        DebitNoteFor newEntity = debitNoteForRepository.findDebitNoteForById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addDebitNoteFor(DebitNoteForRequestDto dto) {
        try {

            boolean typeExists = debitNoteForRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Debit Note For Name not allowed", "error");
            }
            DebitNoteFor newEntity = debitNoteForRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public DebitNoteForResponseDto updateDebitNoteFor(Long id, DebitNoteForRequestDto dto) {
//        DebitNoteFor newEntity = debitNoteForRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateDebitNoteFor(Long id, DebitNoteForRequestDto dto) {
        // Read user id from JWT Token
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = debitNoteForRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Debit Note For Name not allowed", "error");
            }
            DebitNoteFor newEntity = debitNoteForRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<DebitNoteForResponseDto> getDropDown() {
        List<DebitNoteFor> page = debitNoteForRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteDebitNoteFor(Long id) {
        debitNoteForRepository.deleteDebitNoteForById(id);
    }

    private DebitNoteFor transferToEntity(Long id, DebitNoteForRequestDto dto) {
        DebitNoteFor debitNoteFor = new DebitNoteFor();

        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            DebitNoteFor newEntity = debitNoteForRepository.findDebitNoteForById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            debitNoteFor.setId(newEntity.getId());
            debitNoteFor.setName(dto.getName());
            debitNoteFor.setNameBn(dto.getNameBn());
            debitNoteFor.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            debitNoteFor.setActive(dto.getActive());

            debitNoteFor.setUpdatedAt(new Date());
            debitNoteFor.setUpdatedBy(userData.getId());

            return debitNoteFor;
        } else {
            debitNoteFor.setName(dto.getName());
            debitNoteFor.setNameBn(dto.getNameBn());
            debitNoteFor.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            debitNoteFor.setActive(dto.getActive());
            debitNoteFor.setCreatedAt(new Date());
            debitNoteFor.setCreatedBy(userData.getId());

            return debitNoteFor;
        }

    }

    private DebitNoteForResponseDto transferToDTO(DebitNoteFor entity) {
        DebitNoteForResponseDto dto = new DebitNoteForResponseDto();
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
