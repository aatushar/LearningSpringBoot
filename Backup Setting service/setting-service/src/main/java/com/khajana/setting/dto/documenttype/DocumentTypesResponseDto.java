package com.khajana.setting.dto.documenttype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTypesResponseDto {

    private Long id;

    private String docType;

    private String docTypeBn;

    private Double seqNo;
    private Boolean active;

    private String createdAt;

    private String updatedAt;

    private String createdBy;

    private String updatedBy;
}
