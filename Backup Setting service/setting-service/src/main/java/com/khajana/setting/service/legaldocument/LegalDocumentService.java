package com.khajana.setting.service.legaldocument;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.legaldocument.LegalDocumentRequestDto;
import com.khajana.setting.dto.legaldocument.LegalDocumentResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.companybranch.Company;
import com.khajana.setting.entity.companybranch.CompanyBranch;
import com.khajana.setting.entity.documenttype.DocumentTypesEntity;
import com.khajana.setting.entity.legaldocument.LegalDocument;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyBranchRepository;
import com.khajana.setting.repository.companybranch.CompanyRepository;
import com.khajana.setting.repository.documenttype.DocumentTypesRepository;
import com.khajana.setting.repository.legaldocument.LegalDocumentRepository;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.ConstantValue;
import com.khajana.setting.utils.ContextUser;
import com.khajana.setting.utils.DateUtil;
import com.khajana.setting.utils.SimplePage;
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
public class LegalDocumentService {

    @Autowired
    LegalDocumentRepository legalDocumentRepository;
    @Autowired
    UserCredentialRepository userCredentials;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyBranchRepository companyBranchRepository;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    @Autowired
    DocumentTypesRepository documentTypesRepository;

    public SimplePage<LegalDocumentResponseDto> findAllLegalDocuments(Pageable pageable) {
        Page<LegalDocument> page = legalDocumentRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public LegalDocumentResponseDto findLegalDocumentById(Long id) {

        LegalDocument newEntity = legalDocumentRepository.findLegalDocumentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addLegalDocument(LegalDocumentRequestDto dto) {
        try {
            LegalDocument newEntity = legalDocumentRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public LegalDocumentResponseDto updateLegalDocument(Long id, LegalDocumentRequestDto dto) {
        LegalDocument newEntity = legalDocumentRepository.save(transferToEntity(id, dto));
        return transferToDTO(newEntity);
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.legalDocumentDropdown();
        return result;
    }

    public void deleteLegalDocument(Long id) {
        legalDocumentRepository.deleteLegalDocumentById(id);
    }

    private LegalDocument transferToEntity(Long id, LegalDocumentRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        LegalDocument legalDocument = new LegalDocument();
        if (id != null && id > 0) {
            legalDocument.setId(id);
            legalDocument.setCompanyId(1l);
            legalDocument.setBranchId(95l);
            legalDocument.setDocTypeId(dto.getDocTypeId());
            legalDocument.setDocumentName(dto.getDocumentName());
            legalDocument.setValidTill(dto.getValidTill());
            legalDocument.setUploadDate(dto.getUploadDate());
            if (dto.getAttachments() != null) {
                MultipartFile attachments = dto.getAttachments();

                // Extract file extension from the original file name
                String originalFileName = attachments.getOriginalFilename();
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
                    Files.copy(attachments.getInputStream(), filePath);

                    // Set the file path in your 'company' object (assuming 'company' is an instance
                    // of some class)
                    legalDocument.setAttachments(filePath.toString());
                } catch (IOException e) {
                    // Handle the exception (e.g., log it or throw a custom exception)
                    e.printStackTrace();
                }
            }
            legalDocument.setRemarks(dto.getRemarks());
            legalDocument.setUpdatedAt(new Date());
            legalDocument.setUpdatedBy(userData.getId());

            return legalDocument;
        } else {
            legalDocument.setCompanyId(1l);
            legalDocument.setBranchId(95l);
            legalDocument.setDocTypeId(dto.getDocTypeId());
            legalDocument.setDocumentName(dto.getDocumentName());
            legalDocument.setValidTill(dto.getValidTill());
            legalDocument.setUploadDate(dto.getUploadDate());
            if (dto.getAttachments() != null) {
                MultipartFile attachments = dto.getAttachments();

                // Extract file extension from the original file name
                String originalFileName = attachments.getOriginalFilename();
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
                    Files.copy(attachments.getInputStream(), filePath);

                    // Set the file path in your 'company' object (assuming 'company' is an instance
                    // of some class)
                    legalDocument.setAttachments(filePath.toString());
                } catch (IOException e) {
                    // Handle the exception (e.g., log it or throw a custom exception)
                    e.printStackTrace();
                }
            }
            legalDocument.setRemarks(dto.getRemarks());
            legalDocument.setCreatedAt(new Date());
            legalDocument.setCreatedBy(userData.getId());

            LegalDocument savedLegalDocument = legalDocumentRepository.save(legalDocument);
            savedLegalDocument.setDocumentNo("LD-" + savedLegalDocument.getId());
            legalDocumentRepository.save(savedLegalDocument);

            return savedLegalDocument;
        }

    }

    private LegalDocumentResponseDto transferToDTO(LegalDocument entity) {
        LegalDocumentResponseDto dto = new LegalDocumentResponseDto();
        dto.setId(entity.getId());
        dto.setCompanyId(entity.getCompanyId());
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            if (company != null) {
                dto.setCompanyName(company.getCompName());
            }
        }
        if (entity.getBranchId() != null) {
            CompanyBranch branch = companyBranchRepository.findById(entity.getBranchId()).orElse(null);
            if (branch != null) {
                dto.setBranchName(branch.getBranchUnitName());
            }
        }
        if (entity.getDocTypeId() != null) {
            DocumentTypesEntity documentTypesEntity = documentTypesRepository.findById(entity.getDocTypeId())
                    .orElse(null);
            if (documentTypesEntity != null) {
                dto.setDocTypeName(documentTypesEntity.getDocType());
            }
        }
        dto.setBranchId(entity.getBranchId());
        dto.setDocTypeId(entity.getDocTypeId());
        if (entity.getId() != null) {
            LegalDocument legalDocument = legalDocumentRepository.findById(entity.getId()).orElse(null);
            if (legalDocument != null && legalDocument.getDocumentNo() != null) {
                dto.setDocumentNo(legalDocument.getDocumentNo());
            }
        }
        dto.setDocumentNo(entity.getDocumentNo());
        dto.setDocumentName(entity.getDocumentName());
        dto.setValidTill(DateUtil.convertDateToString(entity.getValidTill(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUploadDate(DateUtil.convertDateToString(entity.getUploadDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        if (entity.getAttachments() != null) {
            // Assuming entity.getCompLogo() returns the relative path with backslashes
            String relativePath = entity.getAttachments();
    
            // Base URL as a string
            String baseStringUrl = "http://192.168.10.10:8181/";
    
            // Convert backslashes to forward slashes for URL
            String url = baseStringUrl + relativePath.replace("\\", "/");
    
            // Set the URL in your DTO
            dto.setAttachments(url);
        }
        dto.setRemarks(entity.getRemarks());
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
