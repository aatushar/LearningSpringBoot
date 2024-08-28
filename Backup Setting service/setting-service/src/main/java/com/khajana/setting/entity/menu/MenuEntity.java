package com.khajana.setting.entity.menu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "1b1_vms_menu")
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "menu_name_bn", nullable = false)
    private String menuNameBn;

    @Column(name = "menu_parent_id", nullable = true)
    private Long menuParentId;

    @Column(name = "e_is_active", nullable = true)
    private Short eIsActive;

    @Column(name = "is_child", nullable = true)
    private Short isChild;

    @Column(name = "sub_menu_id", nullable = true)
    private Integer subMenuId;

    @Column(name = "icon_name", nullable = true)
    private String iconName;

    @Column(name = "route_id", nullable = true)
    private Long routeId;

    @Column(name = "route_name", nullable = true)
    private String routeName;

    @Column(name = "seq_number", nullable = true)
    private Float seqNumber;

    @Column(name = "is_newtab", nullable = true)
    private Short isNewtab;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "updated_by", nullable = true)
    private Integer updatedBy;

}
