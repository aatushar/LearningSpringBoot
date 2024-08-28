package com.khajana.setting.dto.issuemaster;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueMasterDto {
    private Long id;
    //    private Long companyIdD;
    private String DeliveryNoD;
    private Date deliveryDateD;
    private String customerNameD;
    private String VatChallanNo;
    private Date ChallanDateD;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    private Long createdBy;
    private Long updatedBy;
}
