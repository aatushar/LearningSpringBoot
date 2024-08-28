package com.khajana.setting.dto.purchaseOrder;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoInsertDto {


    private Long id;
    private List<PoInsertChildDto> child;
}
