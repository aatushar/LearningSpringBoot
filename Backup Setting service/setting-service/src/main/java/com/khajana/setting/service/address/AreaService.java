package com.khajana.setting.service.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.address.AreaRequestDto;
import com.khajana.setting.dto.address.AreaResponseDto;
import com.khajana.setting.entity.address.Area;
import com.khajana.setting.entity.address.City;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.address.AreaRepository;
import com.khajana.setting.repository.address.CityRepository;
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
public class AreaService {

    @Autowired
    AreaRepository areaRepository;
    @Autowired
    CityRepository cityRepository;

    public SimplePage<AreaResponseDto> findAllAreas(Pageable pageable) {
        Page<Area> page = areaRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public AreaResponseDto findAreaById(Long id) {

        Area newEntity = areaRepository.findAreaById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addArea(AreaRequestDto dto) {
        try {
            boolean typeExists = areaRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Area Name not allowed", "error");
            }
            Area newEntity = areaRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public AreaResponseDto updateArea(Long id, AreaRequestDto dto) {
////        Area newEntity = areaRepository.save(transferToEntity(id, dto));
////        return transferToDTO(newEntity);
////    }
    public ApiResponse updateArea(Long id, AreaRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = areaRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Area Name not allowed", "error");
            }
            Area newEntity = areaRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<AreaResponseDto> getDropDown() {
        List<Area> page = areaRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteArea(Long id) {
        areaRepository.deleteAreaById(id);
    }

    private Area transferToEntity(Long id, AreaRequestDto dto) {
        Area area = new Area();
        if (id != null && id > 0) {
            City city = cityRepository.findById(dto.getCityId())
                    .orElseThrow(() -> new ResourceNotFoundException("City", "Id", dto.getCityId()));
            area.setCity(city);
            area.setId(id);
            area.setName(dto.getName());
            area.setNameBn(dto.getNameBn());
            area.setCityId(dto.getCityId());
            area.setSeqNo(dto.getSeqNo());
            area.setActive(dto.getActive());
            area.setUpdatedAt(new Date());

            return area;
        } else {
            area.setName(dto.getName());
            City city = cityRepository.findById(dto.getCityId())
                    .orElseThrow(() -> new ResourceNotFoundException("City", "Id", dto.getCityId()));
            area.setCity(city);
            area.setNameBn(dto.getNameBn());
            area.setCityId(dto.getCityId());
            area.setSeqNo(dto.getSeqNo());
            area.setActive(dto.getActive());
            area.setCreatedAt(new Date());

            return area;
        }

    }

    private AreaResponseDto transferToDTO(Area entity) {
        AreaResponseDto dto = new AreaResponseDto();
        dto.setId(entity.getId());
        dto.setCityName(entity.getCity().getName());
        dto.setCityNameBn(entity.getCity().getName());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setCityId(entity.getCityId());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setActive(entity.getActive());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        return dto;
    }
}
