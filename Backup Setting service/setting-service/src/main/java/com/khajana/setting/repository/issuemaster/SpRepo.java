package com.khajana.setting.repository.issuemaster;

import com.khajana.setting.entity.issuemaster.SpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpRepo extends JpaRepository<SpEntity, Long> {
    @Procedure(name = "GetAllSellIndexData")
    List<SpEntity> getAllSellIndexData(@Param("prodTypeId") int prodTypeId);
//List<SpoHouseKeeping> getAllSellIndexData();
//    @Query(value = "EXEC [dbo].[trasSellDropdownHK]", nativeQuery = true)
//    List<SpoHouseKeeping> callTrasSellDropdownHK();
}
