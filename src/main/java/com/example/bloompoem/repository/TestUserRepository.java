package com.example.bloompoem.repository;

import com.example.bloompoem.entity.TestUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestUserRepository extends JpaRepository<TestUserEntity,String> {
    Optional<TestUserEntity> findByUserEmail(String userEmail);
}
