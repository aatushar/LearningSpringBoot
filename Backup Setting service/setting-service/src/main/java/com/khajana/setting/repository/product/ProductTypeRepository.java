package com.khajana.setting.repository.product;

import com.khajana.setting.entity.product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    Optional<ProductType> findProductTypeById(Long id);

    Optional<ProductType> findProductTypeByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    void deleteProductTypeById(Long id);
}
