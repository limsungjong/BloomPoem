package com.example.bloompoem.repository;

import com.example.bloompoem.entity.FloristEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;


public interface FloristRepository extends JpaRepository<FloristEntity, Integer> {

    @Query(value =
            "select * from florist " +
                    "where florist_latitude > :xa " +
                    "and florist_latitude < :xb"+
                    "and florist_latitude < :ya"+
                    "and florist_latitude < :yb", nativeQuery = true)
    List<FloristEntity> searchXY(@Param("xa") BigDecimal xa,@Param("ya") BigDecimal xb,
                                 @Param("ya") BigDecimal ya,@Param("yb") BigDecimal yb);

}
