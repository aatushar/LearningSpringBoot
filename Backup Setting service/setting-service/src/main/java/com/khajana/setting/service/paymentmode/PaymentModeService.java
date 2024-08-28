package com.khajana.setting.service.paymentmode;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.paymentmode.PaymentModeRequestDto;
import com.khajana.setting.dto.paymentmode.PaymentModeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.paymentmode.PaymentMode;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.paymentmode.PaymentModeRepository;
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
public class PaymentModeService {

    @Autowired
    PaymentModeRepository paymentModeRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<PaymentModeResponseDto> findAllPaymentModes(Pageable pageable) {
        Page<PaymentMode> page = paymentModeRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public PaymentModeResponseDto findPaymentModeById(Long id) {

        PaymentMode newEntity = paymentModeRepository.findPaymentModeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addPaymentMode(PaymentModeRequestDto dto) {
        try {
            boolean typeExists = paymentModeRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Payment Mode Name not allowed", "error");
            }
            PaymentMode newEntity = paymentModeRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public PaymentModeResponseDto updatePaymentMode(Long id, PaymentModeRequestDto dto) {
//        PaymentMode newEntity = paymentModeRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updatePaymentMode(Long id, PaymentModeRequestDto dto) {
        // Read user id from JWT Token
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = paymentModeRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Payment Mode Name not allowed", "error");
            }

            PaymentMode newEntity = paymentModeRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<PaymentModeResponseDto> getDropDown() {
        List<PaymentMode> page = paymentModeRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deletePaymentMode(Long id) {
        paymentModeRepository.deletePaymentModeById(id);
    }

    private PaymentMode transferToEntity(Long id, PaymentModeRequestDto dto) {
        PaymentMode paymentMode = new PaymentMode();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            PaymentMode newEntity = paymentModeRepository.findPaymentModeById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            paymentMode.setId(newEntity.getId());
            paymentMode.setName(dto.getName());
            paymentMode.setNameBn(dto.getNameBn());
            paymentMode.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            paymentMode.setActive(dto.getActive());
            paymentMode.setUpdatedAt(new Date());
            paymentMode.setUpdatedBy(userData.getId());

            return paymentMode;
        } else {
            paymentMode.setName(dto.getName());
            paymentMode.setNameBn(dto.getNameBn());
            paymentMode.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            paymentMode.setActive(dto.getActive());
            paymentMode.setCreatedAt(new Date());
            paymentMode.setCreatedBy(userData.getId());

            return paymentMode;
        }

    }

    private PaymentModeResponseDto transferToDTO(PaymentMode entity) {
        PaymentModeResponseDto dto = new PaymentModeResponseDto();
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
