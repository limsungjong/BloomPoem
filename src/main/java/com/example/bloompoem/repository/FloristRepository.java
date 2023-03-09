package com.example.bloompoem.repository;

import com.example.bloompoem.entity.FloristEntity;
import com.example.bloompoem.entity.Inter.FloristFlowerInterFace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


@Repository
public interface FloristRepository extends JpaRepository<FloristEntity, Integer> {

    FloristEntity findByFloristName(String floristName);

    FloristEntity findFloristEntityByFloristName(String floristName);

//    ProductEntity findByProductNumber(int productNumber);

    @Query(value =
            "select * from florist " +
                    "where florist_latitude > :xa " +
                    "and florist_latitude < :xb " +
                    "and florist_longtitude > :ya " +
                    "and florist_longtitude < :yb ", nativeQuery = true)
    List<FloristEntity> searchXY(@Param("xa") BigDecimal xa, @Param("xb") BigDecimal xb,
                                 @Param("ya") BigDecimal ya, @Param("yb") BigDecimal yb);

    @Query(value =
            "SELECT b.flower_number " +
                    "from florist a, florist_product b " +
                    "WHERE a.florist_number = b.florist_number " +
                    "AND b.florist_number = :floristNumber ", nativeQuery = true)
    List<BigInteger> searchFloristFlower(@Param("floristNumber") Long floristNumber);

    @Query(value =
            "SELECT c.flower_number, c.flower_name, " +
                    "c.flower_language, c.flower_tag, " +
                    "c.flower_season , c.flower_color, " +
                    "c.flower_image " +
                    "from florist a, florist_product b, flower c " +
                    "WHERE a.florist_number = b.florist_number " +
                    "and b.flower_number = c.flower_number "+
                    "AND b.florist_number = :floristNumber ", nativeQuery = true)
    List<FloristFlowerInterFace> searchFloristFlower2(@Param("floristNumber") Long floristNumber);
}
