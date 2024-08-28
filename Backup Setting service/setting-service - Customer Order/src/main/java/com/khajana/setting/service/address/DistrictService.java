package com.khajana.setting.service.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.address.DistrictRequestDto;
import com.khajana.setting.dto.address.DistrictResponseDto;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.khajana.setting.entity.address.District;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.address.DistrictRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class DistrictService {

    @Autowired
    DistrictRepository districtRepository;

    public SimplePage<DistrictResponseDto> findAllDistricts(Pageable pageable) {
        Page<District> page = districtRepository.findAll(pageable);
        return new SimplePage<>(page.getContent()
                .stream()
                .map(this::transferToDTO)
                .collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public DistrictResponseDto findDistrictById(Long id) {

        District newEntity = districtRepository.findDistrictById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionVatRegSource", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addDistrict(DistrictRequestDto dto) {
        try {

            if (dto == null) {
                return new ApiResponse(404, "No Parameter Found!", "");
            }
            if (dto.getCountryId() == null) {
                return new ApiResponse(404, "Country ID is required!", "");
            }
            if (dto.getLat() == null || dto.getLat().isEmpty()) {
                return new ApiResponse(404, "Latitude is required!", "");
            }
            if (dto.getLongitude() == null || dto.getLongitude().isEmpty()) {
                return new ApiResponse(404, "Longitude is required!", "");
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

            District newEntity = districtRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "Data Inserted Successfully", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "");
        }
    }

    public DistrictResponseDto updateDistrict(Long id, DistrictRequestDto dto) {
        District newEntity = districtRepository.save(transferToEntity(id, dto));
        return transferToDTO(newEntity);
    }

    public List<DistrictResponseDto> getDropDown() {
        List<District> page = districtRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteDistrict(Long id) {
        districtRepository.deleteDistrictById(id);
    }

    private District transferToEntity(Long id, DistrictRequestDto dto) {
        District district = new District();
        if (id != null && id > 0) {
            district.setId(id);
            district.setName(dto.getName());
            district.setNameBn(dto.getNameBn());
            district.setCountryId(dto.getCountryId());
            district.setLat(dto.getLat());
            district.setLongitude(dto.getLongitude());
            district.setSeqNo(dto.getSeqNo());
            district.setActive(dto.getActive());

            return district;
        } else {
            district.setName(dto.getName());
            district.setNameBn(dto.getNameBn());
            district.setCountryId(dto.getCountryId());
            district.setLat(dto.getLat());
            district.setLongitude(dto.getLongitude());
            district.setSeqNo(dto.getSeqNo());
            district.setActive(dto.getActive());

            return district;
        }

    }

    private DistrictResponseDto transferToDTO(District entity) {
        DistrictResponseDto dto = new DistrictResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setCountryId(entity.getCountryId());
        dto.setLat(entity.getLat());
        dto.setLongitude(entity.getLongitude());
        dto.setSeqNo(entity.getSeqNo());
        dto.setActive(entity.getActive());
        return dto;
    }
}
