package com.khajana.setting.service.currency;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.currency.CurrencyRequestDto;
import com.khajana.setting.dto.currency.CurrencyResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.currency.Currency;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.currency.CurrencyRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;

import io.micrometer.common.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    /*
     * public Page<CurrencyDto> findAllCurrencys(Pageable pageable) { Page<Currency>
     * page = currencyRepository.findAll(pageable); return
     * page.map(this::transferToDTO); }
     */

    public SimplePage<CurrencyResponseDto> findAllCurrencys(String filter, Pageable pageable) {
        Page<Currency> page;
        if (StringUtils.isNotEmpty(filter)) {

            page = currencyRepository.findAllCurrencyByCurrencyShortCode(filter, pageable);
        } else {

            page = currencyRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CurrencyResponseDto findCurrencyById(Long id) {

        Currency newEntity = currencyRepository.findCurrencyById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionCurrency", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCurrency(CurrencyRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = currencyRepository.existsByCurrencyShortCode(dto.getCurrencyShortCode());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Currency not allowed", "error");
            }
            Currency newEntity = currencyRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCurrency(Long id, CurrencyRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean nameExistsForOtherId = currencyRepository
                    .existsByCurrencyShortCodeAndIdNot(dto.getCurrencyShortCode(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Currency not allowed", "error");
            }
            Currency newEntity = currencyRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CurrencyResponseDto> getDropDown() {
        List<Currency> page = currencyRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCurrency(Long id) {
        currencyRepository.deleteCurrencyById(id);
    }

    private Currency transferToEntity(Long id, CurrencyRequestDto dto) {
        Currency currency = new Currency();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            Currency newEntity = currencyRepository.findCurrencyById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("TransactionCurrency", "Id", id));
            currency.setId(newEntity.getId());
            currency.setCurrencyShortCode(dto.getCurrencyShortCode());
            currency.setCurrencyDesc(dto.getCurrencyDesc());
            currency.setSeqNo(dto.getSeqNo());
            currency.setActive(dto.getActive());
            currency.setUpdatedAt(new Date());
            currency.setUpdatedBy(userData.getId());

            return currency;
        } else {
            currency.setCurrencyShortCode(dto.getCurrencyShortCode());
            currency.setCurrencyDesc(dto.getCurrencyDesc());
            currency.setSeqNo(dto.getSeqNo());
            currency.setActive(dto.getActive());
            currency.setCreatedAt(new Date());
            currency.setCreatedBy(userData.getId());

            return currency;
        }
    }

    private CurrencyResponseDto transferToDTO(Currency entity) {
        CurrencyResponseDto dto = new CurrencyResponseDto();
        dto.setId(entity.getId());
        dto.setCurrencyShortCode(entity.getCurrencyShortCode());
        dto.setCurrencyDesc(entity.getCurrencyDesc());
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
