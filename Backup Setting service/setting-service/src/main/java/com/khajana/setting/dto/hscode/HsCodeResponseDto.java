package com.khajana.setting.dto.hscode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HsCodeResponseDto {
    private Long id;
    private String hsCodeChapter;
    private String hsCodeHeading;
    private String hsCodeSection;
    private Long productCategoryId;
    private String productCategoryName;
    private String hsCode;
    private String description;
    private String descriptionBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}