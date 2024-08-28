package com.khajana.setting.service.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.address.CityRequestDto;
import com.khajana.setting.dto.address.CityResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.address.City;
import com.khajana.setting.entity.address.Country;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.address.CityRepository;
import com.khajana.setting.repository.address.CountryRepository;
import com.khajana.setting.utils.ConstantValue;
import com.khajana.setting.utils.DateUtil;
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
public class CityService {

    @Autowired
    CityRepository cityRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<CityResponseDto> findAllCitys(Pageable pageable) {
        Page<City> page = cityRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CityResponseDto findCityById(Long id) {

        City newEntity = cityRepository.findCityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCity(CityRequestDto dto) {
        try {
            boolean typeExists = cityRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate City Name not allowed", "error");
            }
            City newEntity = cityRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public CityResponseDto updateCity(Long id, CityRequestDto dto) {
//        City newEntity = cityRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateCity(Long id, CityRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = cityRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate City  Name not allowed", "error");
            }
            City newEntity = cityRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CityResponseDto> getDropDown() {
        List<City> page = cityRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCity(Long id) {
        cityRepository.deleteCityById(id);
    }

    private City transferToEntity(Long id, CityRequestDto dto) {
        City city = new City();
        if (id != null && id > 0) {
            Country country = countryRepository.findById(dto.getCountryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Country", "Id", dto.getCountryId()));
            city.setCountry(country);
            city.setId(id);
            city.setName(dto.getName());
            city.setNameBn(dto.getNameBn());
            city.setCountryId(dto.getCountryId());
            city.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            city.setActive(dto.getActive());
            city.setUpdatedAt(new Date());

            return city;
        } else {
            Country country = countryRepository.findById(dto.getCountryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Country", "Id", dto.getCountryId()));
            city.setCountry(country);
            city.setName(dto.getName());
            city.setNameBn(dto.getNameBn());
            city.setCountryId(dto.getCountryId());
            city.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            city.setActive(dto.getActive());
            city.setCreatedAt(new Date());

            return city;
        }

    }

    private CityResponseDto transferToDTO(City entity) {
        CityResponseDto dto = new CityResponseDto();
        dto.setId(entity.getId());
        dto.setCountryName(entity.getCountry().getName());
        dto.setCountryNameBn(entity.getCountry().getNameBn());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setCountryId(entity.getCountryId());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setActive(entity.getActive());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }
        if (entity.getUpdatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(createdByUser != null ? createdByUser.getName() : null);
        }
        return dto;
    }
}
