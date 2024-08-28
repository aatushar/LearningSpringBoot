package com.khajana.setting.service.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.address.DivisionRequestDto;
import com.khajana.setting.dto.address.DivisionResponseDto;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.khajana.setting.entity.address.Division;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.address.DivisionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class DivisionService {

    @Autowired
    DivisionRepository divisionRepository;

    public SimplePage<DivisionResponseDto> findAllDivisions(Pageable pageable) {
        Page<Division> page = divisionRepository.findAll(pageable);
        return new SimplePage<>(page.getContent()
                .stream()
                .map(this::transferToDTO)
                .collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public DivisionResponseDto findDivisionById(Long id) {

        Division newEntity = divisionRepository.findDivisionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionVatRegSource", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addDivision(DivisionRequestDto dto) {
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
            // if (dto.getActive() == null) {
            // return new ApiResponse(404, "Active Status is required in Boolean!", "");
            // }
            // if (dto.getSeqNo() == null || dto.getSeqNo().toString().isEmpty()) {
            // return new ApiResponse(404, "Sequence Number is required!", "");
            // }

            Division newEntity = divisionRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "Data Inserted Successfully", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "");
        }
    }

    public DivisionResponseDto updateDivision(Long id, DivisionRequestDto dto) {
        Division newEntity = divisionRepository.save(transferToEntity(id, dto));
        return transferToDTO(newEntity);
    }

    public List<DivisionResponseDto> getDropDown() {
        List<Division> page = divisionRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteDivision(Long id) {
        divisionRepository.deleteDivisionById(id);
    }

    private Division transferToEntity(Long id, DivisionRequestDto dto) {
        Division division = new Division();
        if (id != null && id > 0) {
            division.setId(id);
            division.setName(dto.getName());
            division.setNameBn(dto.getNameBn());
            division.setCountryId(dto.getCountryId());
            division.setLat(dto.getLat());
            division.setLongitude(dto.getLongitude());

            return division;
        } else {
            division.setName(dto.getName());
            division.setNameBn(dto.getNameBn());
            division.setCountryId(dto.getCountryId());
            division.setLat(dto.getLat());
            division.setLongitude(dto.getLongitude());

            return division;
        }

    }

    private DivisionResponseDto transferToDTO(Division entity) {
        DivisionResponseDto dto = new DivisionResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setCountryId(entity.getCountryId());
        dto.setLat(entity.getLat());
        dto.setLongitude(entity.getLongitude());
        return dto;
    }
}
