package com.example.bloompoem.repository;

import com.example.bloompoem.entity.FloristEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface FloristRepository extends JpaRepository<FloristEntity, Integer> {

    @Query(value =
            "select * from florist " +
                    "where florist_latitude > :xa " +
                    "and florist_latitude < :xb" +
                    "and florist_longtitude > :ya" +
                    "and florist_longtitude < :yb", nativeQuery = true)
    List<FloristEntity> searchXY(@Param("xa") BigDecimal xa,@Param("ya") BigDecimal xb,
                                 @Param("ya") BigDecimal ya,@Param("yb") BigDecimal yb);

}
