package com.khajana.setting.repository.ioc;

import com.khajana.setting.entity.ioc.IocDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IocDetailRepository extends JpaRepository<IocDetails, Long> {
    Optional<IocDetails> findIocDetailsById(Long id);

    void deleteIocDetailsById(Long id);
}
