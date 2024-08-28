package com.khajana.setting.dto.menudto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
    private Long id;

    private String menuName;

    private String menuNameBn;

    private Long menuParentId;

    private Short eIsActive;

    private Short isChild;

    private Integer subMenuId;

    private String iconName;

    private Long routeId;

    private String routeName;

    private Float seqNumber;

    private Short isNewtab;

    private Date createdAt;

    private Date updatedAt;

    private Integer createdBy;

    private Integer updatedBy;
}