package com.khajana.setting.repository;

import com.khajana.setting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
