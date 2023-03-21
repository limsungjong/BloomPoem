package com.example.bloompoem.repository;

import com.example.bloompoem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserEmail(String userEmail);
    @Modifying
    @Query(value = "update UserEntity b set b.userOtp = :userOtp where b.userEmail = :userEmail")
    void changeOtp(@Param("userOtp") String userOtp , @Param("userEmail") String userEmail);


}
