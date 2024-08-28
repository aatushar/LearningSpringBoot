package com.khajana.setting.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressTypeResponseDto {

    private Long id;
    private String name;
    private String nameBn;
    private Double seqNo;
    private Boolean active;
    private Date createdAt;
    private Date updatedAt;
    private Long createdBy;
    private Long updatedBy;

}