package com.khajana.setting.service.currency;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.currency.CurrencyExchangeRateRequestDto;
import com.khajana.setting.dto.currency.CurrencyExchangeRateResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.currency.CurrencyExchangeRate;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.currency.CurrencyExchangeRateRepository;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyExchangeRateService {

    @Autowired
    CurrencyExchangeRateRepository currencyExchangeRateRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    /*
     * public Page<CurrencyExchangeRateDto> findAllCurrencyExchangeRates(Pageable
     * pageable) { Page<CurrencyExchangeRate> page =
     * currencyExchangeRateRepository.findAll(pageable); return
     * page.map(this::transferToDTO); }
     */

    public SimplePage<CurrencyExchangeRateResponseDto> findAllCurrencyExchangeRates(Pageable pageable) {
        Page<CurrencyExchangeRate> page = currencyExchangeRateRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CurrencyExchangeRateResponseDto findCurrencyExchangeRateById(Long id) {

        CurrencyExchangeRate newEntity = currencyExchangeRateRepository.findCurrencyExchangeRateById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionCurrencyExchangeRate", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCurrencyExchangeRate(CurrencyExchangeRateRequestDto dto) {
        // Read user id from JWT Token
        try {

            boolean typeExists = currencyExchangeRateRepository.existsByExchangeRate(dto.getExchangeRate());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Currency Exchange Rate Not Allowed", "error");
            }
            CurrencyExchangeRate newEntity = currencyExchangeRateRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCurrencyExchangeRate(Long id, CurrencyExchangeRateRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean nameExistsForOtherId = currencyExchangeRateRepository.existsByExchangeRateAndIdNot(dto.getExchangeRate(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Currency Exchange Rate Not Allowed", "error");
            }
            CurrencyExchangeRate newEntity = currencyExchangeRateRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CurrencyExchangeRateResponseDto> getDropDown() {
        List<CurrencyExchangeRate> page = currencyExchangeRateRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCurrencyExchangeRate(Long id) {
        currencyExchangeRateRepository.deleteCurrencyExchangeRateById(id);
    }

    private CurrencyExchangeRate transferToEntity(Long id, CurrencyExchangeRateRequestDto dto) {
        CurrencyExchangeRate currencyExchangeRate = new CurrencyExchangeRate();

        CustomUserDetails userData = ContextUser.getLoginUserData();
        if (id != null && id > 0) {
            CurrencyExchangeRate newEntity = currencyExchangeRateRepository.findCurrencyExchangeRateById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("TransactionCurrencyExchangeRate", "Id", id));
            currencyExchangeRate.setId(newEntity.getId());
            currencyExchangeRate.setCurrencyInfoId(dto.getCurrencyInfoId());
            currencyExchangeRate.setExchangeRate(dto.getExchangeRate());
            currencyExchangeRate.setExchangeRateDate(dto.getExchangeRateDate());
            currencyExchangeRate.setSource(dto.getSource());
            currencyExchangeRate.setActive(dto.getActive());
            currencyExchangeRate.setUpdatedAt(new Date());
            currencyExchangeRate.setUpdatedBy(userData.getId());
            return currencyExchangeRate;
        } else {
            currencyExchangeRate.setCurrencyInfoId(dto.getCurrencyInfoId());
            currencyExchangeRate.setExchangeRate(dto.getExchangeRate());
            currencyExchangeRate.setExchangeRateDate(dto.getExchangeRateDate());
            currencyExchangeRate.setSource(dto.getSource());
            currencyExchangeRate.setActive(dto.getActive());
            currencyExchangeRate.setCreatedAt(new Date());
            currencyExchangeRate.setCreatedBy(userData.getId());
            return currencyExchangeRate;
        }
    }

    private CurrencyExchangeRateResponseDto transferToDTO(CurrencyExchangeRate entity) {
        CurrencyExchangeRateResponseDto dto = new CurrencyExchangeRateResponseDto();
        dto.setId(entity.getId());
        dto.setCurrencyInfoId(entity.getCurrencyInfoId());
        if (entity.getCurrency() != null) {
            dto.setCurrencyShortCode(entity.getCurrency().getCurrencyShortCode());
            dto.setCurrencyDesc(entity.getCurrency().getCurrencyDesc());
        }
        dto.setExchangeRate(entity.getExchangeRate());
        dto.setExchangeRateDate(entity.getExchangeRateDate());
        dto.setSource(entity.getSource());
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
