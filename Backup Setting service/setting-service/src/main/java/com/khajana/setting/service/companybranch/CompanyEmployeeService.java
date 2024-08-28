package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companybranch.CompanyEmployeeRequestDto;
import com.khajana.setting.dto.companybranch.CompanyEmployeeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.companybranch.CompanyDepartment;
import com.khajana.setting.entity.companybranch.CompanyDesignation;
import com.khajana.setting.entity.companybranch.CompanyEmployee;
import com.khajana.setting.entity.companybranch.CompanySection;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.*;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.ConstantValue;
import com.khajana.setting.utils.ContextUser;
import com.khajana.setting.utils.DateUtil;
import com.khajana.setting.utils.SimplePage;

import io.micrometer.common.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyEmployeeService {

    @Autowired
    CompanyEmployeeRepository companyEmployeeRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyDepartmentRepository companyDepartmentRepository;
    @Autowired
    CompanySectionRepository companySectionRepository;
    @Autowired
    CompanyDesignationRepository companyDesignationRepository;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    public SimplePage<CompanyEmployeeResponseDto> findAllCompanyEmployees(String filter, Pageable pageable) {
        Page<CompanyEmployee> page;
        if (StringUtils.isNotEmpty(filter)) {
            page = companyEmployeeRepository.findAllCompanyEmployeeByNameContaining(filter,
                    pageable);
        } else {

            page = companyEmployeeRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CompanyEmployeeResponseDto findCompanyEmployeeById(Long id) {

        CompanyEmployee newEntity = companyEmployeeRepository.findCompanyEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCompanyEmployee(CompanyEmployeeRequestDto dto) {
        try {
            CompanyEmployee newEntity = companyEmployeeRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCompanyEmployee(Long id, CompanyEmployeeRequestDto dto) {
        // Read user id from JWT Token
        try {
            CompanyEmployee newEntity = companyEmployeeRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.companyEmployeeDropdown();
        return result;
    }

    public void deleteCompanyEmployee(Long id) {
        companyEmployeeRepository.deleteCompanyEmployeeById(id);
    }

    private CompanyEmployee transferToEntity(Long id, CompanyEmployeeRequestDto dto) {
        CompanyEmployee companyEmployee = new CompanyEmployee();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            companyEmployee.setId(id);

            companyEmployee.setDepartmentId(dto.getDepartmentId());
            companyEmployee.setSectionId(dto.getSectionId());
            companyEmployee.setDesignationId(dto.getDesignationId());
            companyEmployee.setCode(dto.getCode());
            companyEmployee.setCodeBn(dto.getCodeBn());
            companyEmployee.setName(dto.getName());
            companyEmployee.setNameBn(dto.getNameBn());
            companyEmployee.setType(dto.getType());
            companyEmployee.setNid(dto.getNid());
            companyEmployee.setNidDoc(dto.getNidDoc());
            companyEmployee.setAddress(dto.getAddress());
            companyEmployee.setAddressBn(dto.getAddressBn());
            companyEmployee.setEmail(dto.getEmail());
            // companyEmployee.setPhoto(dto.getPhoto());
            if (dto.getPhoto() != null) {
                MultipartFile compLogo = dto.getPhoto();

                // Extract file extension from the original file name
                String originalFileName = compLogo.getOriginalFilename();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);

                // Generate timestamp for the file name
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String timestamp = dateFormat.format(new Date());

                // Define the upload directory
                String uploadDirectory = "uploads"; // Change this to your desired upload directory

                // Ensure the upload directory exists
                Path uploadPath = Paths.get(uploadDirectory);
                if (!Files.exists(uploadPath)) {
                    try {
                        Files.createDirectories(uploadPath);
                    } catch (IOException e) {
                        // Handle the exception (e.g., log it or throw a custom exception)
                        e.printStackTrace();
                    }
                }

                // Generate the new file name
                String fileName = timestamp + "." + fileExtension;
                Path filePath = uploadPath.resolve(fileName);

                try {
                    // Copy the file to the upload directory
                    Files.copy(compLogo.getInputStream(), filePath);

                    // Set the file path in your 'company' object (assuming 'company' is an instance
                    // of some class)
                    companyEmployee.setPhoto(filePath.toString());
                } catch (IOException e) {
                    // Handle the exception (e.g., log it or throw a custom exception)
                    e.printStackTrace();
                }
            }
            companyEmployee.setPhone(dto.getPhone());

            companyEmployee.setActive(dto.getActive());

            companyEmployee.setUpdatedAt(new Date());
            companyEmployee.setUpdatedBy(userData.getId());

            return companyEmployee;
        } else {

            companyEmployee.setDepartmentId(dto.getDepartmentId());
            companyEmployee.setSectionId(dto.getSectionId());
            companyEmployee.setDesignationId(dto.getDesignationId());
            companyEmployee.setCode(dto.getCode());
            companyEmployee.setCodeBn(dto.getCodeBn());
            companyEmployee.setName(dto.getName());
            companyEmployee.setNameBn(dto.getNameBn());
            companyEmployee.setType(dto.getType());
            companyEmployee.setNid(dto.getNid());
            companyEmployee.setNidDoc(dto.getNidDoc());
            companyEmployee.setAddress(dto.getAddress());
            companyEmployee.setAddressBn(dto.getAddressBn());
            companyEmployee.setEmail(dto.getEmail());
            if (dto.getPhoto() != null) {
                MultipartFile compLogo = dto.getPhoto();

                // Extract file extension from the original file name
                String originalFileName = compLogo.getOriginalFilename();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);

                // Generate timestamp for the file name
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String timestamp = dateFormat.format(new Date());

                // Define the upload directory
                String uploadDirectory = "uploads"; // Change this to your desired upload directory

                // Ensure the upload directory exists
                Path uploadPath = Paths.get(uploadDirectory);
                if (!Files.exists(uploadPath)) {
                    try {
                        Files.createDirectories(uploadPath);
                    } catch (IOException e) {
                        // Handle the exception (e.g., log it or throw a custom exception)
                        e.printStackTrace();
                    }
                }

                // Generate the new file name
                String fileName = timestamp + "." + fileExtension;
                Path filePath = uploadPath.resolve(fileName);

                try {
                    // Copy the file to the upload directory
                    Files.copy(compLogo.getInputStream(), filePath);

                    // Set the file path in your 'company' object (assuming 'company' is an instance
                    // of some class)
                    companyEmployee.setPhoto(filePath.toString());
                } catch (IOException e) {
                    // Handle the exception (e.g., log it or throw a custom exception)
                    e.printStackTrace();
                }
            }
            companyEmployee.setPhone(dto.getPhone());

            companyEmployee.setActive(dto.getActive());

            companyEmployee.setCreatedAt(new Date());
            companyEmployee.setCreatedBy(userData.getId());

            return companyEmployee;
        }

    }

    private CompanyEmployeeResponseDto transferToDTO(CompanyEmployee entity) {
        CompanyEmployeeResponseDto dto = new CompanyEmployeeResponseDto();
        dto.setId(entity.getId());

        dto.setDepartmentId(entity.getDepartmentId());
        if (entity.getDepartmentId() != null) {
            CompanyDepartment companyDepartment = companyDepartmentRepository.findById(entity.getDepartmentId())
                    .orElse(null);
            dto.setDepartmentName(companyDepartment != null ? companyDepartment.getDepartmentName() : null);
        }
        if (entity.getSectionId() != null) {
            CompanySection companySection = companySectionRepository.findById(entity.getSectionId()).orElse(null);
            dto.setSectionName(companySection != null ? companySection.getSecName() : null);
        }
        if (entity.getDesignationId() != null) {
            CompanyDesignation companyDesignation = companyDesignationRepository.findById(entity.getDesignationId())
                    .orElse(null);
            dto.setDesignationName(companyDesignation != null ? companyDesignation.getDesigName() : null);
        }
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setSectionId(entity.getSectionId());
        dto.setDesignationId(entity.getDesignationId());
        dto.setCode(entity.getCode());
        dto.setCodeBn(entity.getCodeBn());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setType(entity.getType());
        dto.setNid(entity.getNid());
        dto.setNidDoc("emp_doc_111.docx");
        dto.setAddress(entity.getAddress());
        dto.setAddressBn(entity.getAddressBn());
        dto.setEmail(entity.getEmail());
        // dto.setPhoto("profile.jpg");
        dto.setPhone(entity.getPhone());

        dto.setActive(entity.getActive());
        if (entity.getPhoto() != null) {
            // Assuming entity.getCompLogo() returns the relative path with backslashes
            String relativePath = entity.getPhoto();

            // Base URL as a string
            String baseStringUrl = "http://192.168.10.10:8181/";

            // Convert backslashes to forward slashes for URL
            String url = baseStringUrl + relativePath.replace("\\", "/");

            // Set the URL in your DTO
            dto.setPhoto(url);
        }

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
