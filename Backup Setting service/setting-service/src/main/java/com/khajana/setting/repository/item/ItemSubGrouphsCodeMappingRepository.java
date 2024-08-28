package com.khajana.setting.repository.item;

import com.khajana.setting.entity.item.ItemSubGroupHsCodeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemSubGrouphsCodeMappingRepository extends JpaRepository<ItemSubGroupHsCodeMapping, Long> {
    Optional<ItemSubGroupHsCodeMapping> findItemSubGroupHsCodeMappingById(Long id);

    void deleteItemSubGroupHsCodeMappingById(Long id);


    Optional<ItemSubGroupHsCodeMapping> findByItmSubGrpId(Long itmSubGrpId);

}
