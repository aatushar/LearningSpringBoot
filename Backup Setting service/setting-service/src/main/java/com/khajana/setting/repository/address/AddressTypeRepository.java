package com.khajana.setting.repository.address;

import com.khajana.setting.entity.address.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressTypeRepository extends JpaRepository<AddressType,Long> {
    Optional<AddressType> findAddressTypeById(Long id);
    
    void deleteAddressTypeById(Long id);
}
