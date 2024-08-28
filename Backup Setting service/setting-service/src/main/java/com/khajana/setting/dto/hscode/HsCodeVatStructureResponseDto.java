package com.khajana.setting.dto.hscode;

import com.khajana.setting.dto.vat.VatStructureResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HsCodeVatStructureResponseDto {
    List<VatStructureResponseDto> vatStructures;
    private Long id;
    private String hsCode;
    private Double seqNo;
    private Boolean active;

}