package com.khajana.setting.dto.customer;

import com.khajana.setting.dto.issue.IssueMasterVdsResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerIssueResponseDto {
    private Long id;
    private String customerName;
    private String customerNameBn;

    private List<IssueMasterVdsResponseDto> vdsSellerChildRequestDtos;
}
