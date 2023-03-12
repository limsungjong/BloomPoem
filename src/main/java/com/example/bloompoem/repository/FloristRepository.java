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
    List<BigInteger> searchFloristFlower(@Param("floristNumber") Integer floristNumber);

    @Query(value =
            "SELECT c.flower_number, c.flower_name, " +
                    "c.flower_language, c.flower_tag, " +
                    "c.flower_season , c.flower_color, " +
                    "b.florist_main_image, b.florist_sub_image1, " +
                    "b.florist_sub_image2, b.florist_product_price," +
                    "b.florist_product_quantity " +
                    "from florist a, florist_product b, flower c " +
                    "WHERE a.florist_number = b.florist_number " +
                    "and b.flower_number = c.flower_number " +
                    "AND b.florist_number = :floristNumber ", nativeQuery = true)
    List<FloristFlowerInterFace> searchFloristFlowerDetail(@Param("floristNumber") Integer floristNumber);


    @Query(value =
            "SELECT c.flower_number, c.flower_name, " +
                    "c.flower_language, c.flower_tag, " +
                    "c.flower_season , c.flower_color, " +
                    "b.florist_main_image, b.florist_sub_image1, " +
                    "b.florist_sub_image2, b.florist_product_price," +
                    "b.florist_product_quantity " +
                    "from florist a, florist_product b, flower c " +
                    "WHERE a.florist_number = b.florist_number " +
                    "and b.flower_number = c.flower_number " +
                    "AND b.florist_number = :floristNumber " +
                    "AND c.flower_number = :flowerNumber ", nativeQuery = true)
    FloristFlowerInterFace searchFloristFlowerDetail(@Param("floristNumber") Integer floristNumber, @Param("flowerNumber") Integer flowerNumber);
}
