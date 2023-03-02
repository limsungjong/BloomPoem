package com.example.bloompoem.repository;

import com.example.bloompoem.entity.TestUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TestUserRepository extends JpaRepository<TestUserEntity,String> {
    Optional<TestUserEntity> findByUserEmail(String userEmail);

    @Modifying
    @Query(value = "update TestUserEntity b set b.userOtp = :userOtp where b.userEmail = :userEmail")
    void changeOtp(@Param("userOtp") String userOtp , @Param("userEmail") String userEmail);
}
