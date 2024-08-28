package com.khajana.setting.dto.legaldocument;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalDocumentResponseDto {
    private Long id;
    private Long companyId;
    private String companyName;
    private Long branchId;
    private String branchName;
    private Long docTypeId;
    private String docTypeName;
    private String documentNo;
    private String documentName;
    private String validTill;
    private String uploadDate;
    private String attachments;
    private String remarks;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
