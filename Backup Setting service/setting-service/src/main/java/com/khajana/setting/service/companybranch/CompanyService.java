package com.khajana.setting.service.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.companybranch.CompanyRequestDto;
import com.khajana.setting.dto.companybranch.CompanyResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.address.Country;
import com.khajana.setting.entity.companybranch.Company;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.address.CountryRepository;
import com.khajana.setting.repository.companybranch.CompanyRepository;
import com.khajana.setting.repository.companybranch.CompanyTypeRepository;
import com.khajana.setting.repository.currency.CurrencyRepository;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.service.procedure.HouseKeepingService;
import com.khajana.setting.utils.ConstantValue;
import com.khajana.setting.utils.ContextUser;
import com.khajana.setting.utils.DateUtil;
import com.khajana.setting.utils.SimplePage;

import io.micrometer.common.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    UserCredentialRepository userCredentials;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    HouseKeepingService houseKeepingService;

    @Autowired
    CountryRepository countryRepository;
    @Autowired
    CompanyTypeRepository companyTypeRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    public SimplePage<CompanyResponseDto> findAllCompanys(String compName, Pageable pageable) {
        Page<Company> page;

        if (StringUtils.isNotBlank(compName)) {
            // If compName is provided, filter by CompName
            page = companyRepository.findAllCompanyByCompNameContaining(compName, pageable);
        } else {
            // If compName is empty, retrieve all companies
            page = companyRepository.findAll(pageable);
        }

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CompanyResponseDto findCompanyById(Long id) {

        Company newEntity = companyRepository.findCompanyById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCompany(CompanyRequestDto dto) {
        try {
            Company newEntity = companyRepository.save(transferToEntity(0l, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateCompany(Long id, CompanyRequestDto dto) {
        try {
            Company newEntity = companyRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.companyListDropDown();
        return result;
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteCompanyById(id);
    }

    private Company transferToEntity(Long id, CompanyRequestDto dto) {
        Company company = new Company();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            company.setId(id);

            company.setCountryId(dto.getCountryId());
            company.setCurrencyId(dto.getCurrencyId());
            company.setCompCode(dto.getCompCode());
            company.setCompTypeId(dto.getCompTypeId());
            company.setRegPersonNid(dto.getRegPersonNid());
            company.setRegPersonNidBn(dto.getRegPersonNidBn());
            company.setCompName(dto.getCompName());
            company.setCompNameBn(dto.getCompNameBn());
            company.setCompType(dto.getCompType());
            company.setRegPersonName(dto.getRegPersonName());
            company.setRegPersonNameBn(dto.getRegPersonNameBn());
            company.setRegPersonNid(dto.getRegPersonNid());
            if (dto.getCompLogo() != null) {
                MultipartFile compLogo = dto.getCompLogo();

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
                    company.setCompLogo(filePath.toString());
                } catch (IOException e) {
                    // Handle the exception (e.g., log it or throw a custom exception)
                    e.printStackTrace();
                }
            } else {
                company.setCompLogo(company.getCompLogo());
            }
            // company.setCompLogo(dto.getCompLogo());
            // company.setCompIcon(dto.getCompIcon());
            // company.setNatureOfBiz(dto.getNatureOfBiz());
            company.setCompShortName(dto.getCompShortName());
            company.setCompShortNameBn(dto.getCompShortNameBn());
            company.setCompAddress(dto.getCompAddress());
            company.setCompAddressBn(dto.getCompAddressBn());
            company.setAreaCode(dto.getAreaCode());
            company.setAreaCodeBn(dto.getAreaCodeBn());
            company.setPhoneNumber(dto.getPhoneNumber());
            company.setEmailAddress(dto.getEmailAddress());

            company.setActive(true);
            company.setUpdatedAt(new Date());
            company.setUpdatedBy(userData.getId());

            return company;
        } else {

            company.setCountryId(dto.getCountryId());
            company.setCurrencyId(dto.getCurrencyId());
            company.setCompCode(dto.getCompCode());
            company.setCompTypeId(dto.getCompTypeId());
            // company.setCompBusinessId(dto.getCompBusinessId());
            // company.setCompBusinessOthers(dto.getCompBusinessOthers());
            company.setCompName(dto.getCompName());
            company.setCompNameBn(dto.getCompNameBn());
            company.setCompType(dto.getCompType());
            company.setRegPersonName(dto.getRegPersonName());
            company.setRegPersonNameBn(dto.getRegPersonNameBn());

            company.setRegPersonNid(dto.getRegPersonNid());
            company.setRegPersonNidBn(dto.getRegPersonNidBn());

            if (dto.getCompLogo() != null) {
                MultipartFile compLogo = dto.getCompLogo();

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
                    company.setCompLogo(filePath.toString());
                } catch (IOException e) {
                    // Handle the exception (e.g., log it or throw a custom exception)
                    e.printStackTrace();
                }
            }

            // company.setCompIcon(dto.getCompIcon());
            // company.setNatureOfBiz(dto.getNatureOfBiz());
            company.setCompShortName(dto.getCompShortName());
            company.setCompShortNameBn(dto.getCompShortNameBn());
            company.setCompAddress(dto.getCompAddress());
            company.setCompAddressBn(dto.getCompAddressBn());
            company.setAreaCode(dto.getAreaCode());
            company.setAreaCodeBn(dto.getAreaCodeBn());
            company.setPhoneNumber(dto.getPhoneNumber());
            company.setEmailAddress(dto.getEmailAddress());
            company.setActive(true);

            company.setCreatedAt(new Date());
            company.setCreatedBy(userData.getId());

            return company;
        }

    }

    private CompanyResponseDto transferToDTO(Company entity) {
        CompanyResponseDto dto = new CompanyResponseDto();
        dto.setId(entity.getId());

        dto.setCountryId(entity.getCountryId());
        if (entity.getCountryId() != null && entity.getCountryId() != null) {
            Country country = countryRepository.findById(entity.getCountryId()).orElse(null);
            if (country != null) {
                dto.setCountryName(country.getName());
            }
        }
        dto.setCurrencyId(entity.getCurrencyId());
        if (entity.getCurrencyId() != null && entity.getCurrencyId() != null) {
            com.khajana.setting.entity.currency.Currency currency = currencyRepository.findById(entity.getCurrencyId())
                    .orElse(null);
            if (currency != null) {
                dto.setCurrencyName(currency.getCurrencyDesc());
            }
        }
        dto.setCompCode(entity.getCompCode());
        dto.setCompTypeId(entity.getCompTypeId());
        // dto.setCompBusinessId(entity.getCompBusinessId());
        // dto.setCompBusinessOthers(entity.getCompBusinessOthers());
        dto.setCompName(entity.getCompName());
        dto.setCompNameBn(entity.getCompNameBn());
        dto.setCompType(entity.getCompType());
        dto.setRegPersonName(entity.getRegPersonName());
        dto.setRegPersonNameBn(entity.getRegPersonNameBn());
        dto.setRegPersonNid(entity.getRegPersonNid());
        dto.setRegPersonNidBn(entity.getRegPersonNidBn());

        if (entity.getCompLogo() != null) {
            // Assuming entity.getCompLogo() returns the relative path with backslashes
            String relativePath = entity.getCompLogo();

            // Base URL as a string
            String baseStringUrl = "http://192.168.10.10:8181/";

            // Convert backslashes to forward slashes for URL
            String url = baseStringUrl + relativePath.replace("\\", "/");

            // Set the URL in your DTO
            dto.setCompLogo(url);
        } else {
            dto.setCompLogo(null);
        }

        // dto.setCompLogo(entity.getCompLogo());
        // dto.setCompIcon(entity.getCompIcon());
        // dto.setNatureOfBiz(entity.getNatureOfBiz());
        dto.setCompShortName(entity.getCompShortName());
        dto.setCompShortNameBn(entity.getCompShortNameBn());
        dto.setCompAddress(entity.getCompAddress());
        dto.setCompAddressBn(entity.getCompAddressBn());
        dto.setAreaCode(entity.getAreaCode());
        dto.setAreaCodeBn(entity.getAreaCodeBn());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmailAddress(entity.getEmailAddress());
        dto.setActive(entity.getActive());
        if (entity.getCompanyTypeEntity() != null && entity.getCompanyTypeEntity().getCompanyTypeCode() != null) {

            dto.setCompanyTypeName(entity.getCompanyTypeEntity().getCompanyType());
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
