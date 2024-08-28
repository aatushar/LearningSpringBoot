package com.khajana.setting.dto.language;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageResponseDto {

    private Long id;
    private String name;
    private String code;
    private String appLangCode;
    private Integer rtl;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}