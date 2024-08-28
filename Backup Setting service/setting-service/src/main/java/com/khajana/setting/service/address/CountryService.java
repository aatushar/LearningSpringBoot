package com.khajana.setting.service.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.address.CountryRequestDto;
import com.khajana.setting.dto.address.CountryResponseDto;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.khajana.setting.entity.address.Country;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.address.CountryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public SimplePage<CountryResponseDto> findAllCountrys(Pageable pageable) {
        Page<Country> page = countryRepository.findAll(pageable);
        return new SimplePage<>(page.getContent()
                .stream()
                .map(this::transferToDTO)
                .collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CountryResponseDto findCountryById(Long id) {

        Country newEntity = countryRepository.findCountryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionVatRegSource", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCountry(CountryRequestDto dto) {
        try {

            if (dto == null) {
                return new ApiResponse(404, "No Parameter Found!", "");
            }
            if (dto.getCode() == null || dto.getCode().isEmpty()) {
                return new ApiResponse(404, "Name is required!", "");
            }
            if (dto.getCodeBn() == null || dto.getCode().isEmpty()) {
                return new ApiResponse(404, "Name Bn is required!", "");
            }
            if (dto.getName() == null || dto.getName().isEmpty()) {
                return new ApiResponse(404, "Name is required!", "");
            }
            if (dto.getNameBn() == null || dto.getNameBn().isEmpty()) {
                return new ApiResponse(404, "Name Bn is required!", "");
            }
            if (dto.getActive() == null) {
                return new ApiResponse(404, "Active Status is required in Boolean!", "");
            }
            if (dto.getSeqNo() == null || dto.getSeqNo().toString().isEmpty()) {
                return new ApiResponse(404, "Sequence Number is required!", "");
            }

            Country newEntity = countryRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "Data Inserted Successfully", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "");
        }
    }

    public CountryResponseDto updateCountry(Long id, CountryRequestDto dto) {
        Country newEntity = countryRepository.save(transferToEntity(id, dto));
        return transferToDTO(newEntity);
    }

    public List<CountryResponseDto> getDropDown() {
        List<Country> page = countryRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCountry(Long id) {
        countryRepository.deleteCountryById(id);
    }

    private Country transferToEntity(Long id, CountryRequestDto dto) {
        Country country = new Country();
        if (id != null && id > 0) {
            country.setId(id);
            country.setName(dto.getName());
            country.setNameBn(dto.getNameBn());
            country.setSeqNo(dto.getSeqNo());
            country.setActive(dto.getActive());

            return country;
        } else {
            country.setName(dto.getName());
            country.setNameBn(dto.getNameBn());
            country.setSeqNo(dto.getSeqNo());
            country.setActive(dto.getActive());

            return country;
        }

    }

    private CountryResponseDto transferToDTO(Country entity) {
        CountryResponseDto dto = new CountryResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setSeqNo(entity.getSeqNo());
        dto.setActive(entity.isActive());
        return dto;
    }
}
