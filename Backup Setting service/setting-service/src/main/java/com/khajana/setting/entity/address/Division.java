package com.khajana.setting.entity.address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2R3_division")
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable=false, updatable=false)
    private Long id;

    @Column(name ="country_id",nullable = false, updatable = true)
    private Long countryId;
    
    @Column(name ="name",nullable = false, updatable = true)
    private String name;

    @Column(name ="bn_name", nullable = false, updatable = true)
    private String nameBn;

    @Column(name ="lat", nullable = false, updatable = true)
    private String lat;

    @Column(name ="long", nullable = false, updatable = true)
    private String longitude;;

    @Column(name ="created_at", nullable = true, updatable = true)
    private Date createdAt;

    @Column(name ="updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name ="created_by", nullable = true, updatable = true)
    private Long createdBy;

    // @Column(name ="updated_by", nullable = true, updatable = true)
    // private Long updatedBy;

}
