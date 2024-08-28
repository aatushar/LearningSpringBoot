package com.khajana.setting.dto.legaldocument;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalDocumentRequestDto {
    @NotNull(message = "required field can not be null or empty")
    private Long docTypeId;

    // @NotNull(message = "required field can not be null or empty")
    // @Size(max = 20, message = "max length will be maximum of 20 characters")
    // private String documentNo;

    @NotNull(message = "required field can not be null or empty")
    @Size(max = 200, message = "max length will be maximum of 200 characters")
    private String documentName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date uploadDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validTill;

    @NotNull
    private MultipartFile attachments;

    @NotNull(message = "required field can not be null or empty")
    @Size(max = 200, message = "max length will be maximum of 200 characters")
    private String remarks;
}
