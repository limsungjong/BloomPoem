package com.example.bloompoem.repository;

import com.example.bloompoem.entity.FloristEntity;
import com.example.bloompoem.entity.Inter.FloristAndReviewScore;
import com.example.bloompoem.entity.Inter.FloristAndReviewScoreAndFlowerName;
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

    boolean existsByFloristName(String floristName);

    @Query(value =
            "select * from florist " +
                    "where florist_latitude > :xa " +
                    "and florist_latitude < :xb " +
                    "and florist_longtitude > :ya " +
                    "and florist_longtitude < :yb ", nativeQuery = true)
    List<FloristEntity> searchXY(@Param("xa") BigDecimal xa, @Param("xb") BigDecimal xb,
                                 @Param("ya") BigDecimal ya, @Param("yb") BigDecimal yb);

    @Query(value =
            "select " +
                    "    a.florist_number, " +
                    "    a.florist_name, " +
                    "    a.florist_address, " +
                    "    a.florist_phone_number, " +
                    "    a.florist_latitude, " +
                    "    a.florist_longtitude, " +
                    "    a.user_email, " +
                    "    avg(b.florist_review_score) as florist_review_score, " +
                    "    count(b.florist_number) as florist_review_count " +
                    " from " +
                    "    florist a, florist_review b " +
                    " where a.florist_number = b.florist_number(+) " +
                    " and florist_latitude > :xa " +
                    " and florist_latitude < :xb " +
                    " and florist_longtitude > :ya " +
                    " and florist_longtitude < :yb " +
                    " group by      " +
                    "    a.florist_number, " +
                    "    a.florist_name, " +
                    "    a.florist_address, " +
                    "    a.florist_phone_number, " +
                    "    a.florist_latitude, " +
                    "    a.florist_longtitude, " +
                    "    a.user_email, " +
                    "    b.florist_number ", nativeQuery = true)
    List<FloristAndReviewScore> searchXY2(@Param("xa") BigDecimal xa, @Param("xb") BigDecimal xb,
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

    @Query(value =
            "select " +
                    "    a.florist_number, " +
                    "    a.florist_name, " +
                    "    a.florist_address, " +
                    "    a.florist_phone_number, " +
                    "    a.florist_latitude, " +
                    "    a.florist_longtitude, " +
                    "    a.user_email, " +
                    "    avg(b.florist_review_score) as florist_review_score, " +
                    "    count(b.florist_number) as florist_review_count " +
                    " from " +
                    "    florist a, florist_review b " +
                    " where a.florist_number = b.florist_number(+) " +
                    " group by      " +
                    "    a.florist_number, " +
                    "    a.florist_name, " +
                    "    a.florist_address, " +
                    "    a.florist_phone_number, " +
                    "    a.florist_latitude, " +
                    "    a.florist_longtitude, " +
                    "    a.user_email, "+
                    "    b.florist_number ", nativeQuery = true)
    List<FloristAndReviewScore> getFloristListAndReviewScore();

    @Query(value =
            "select " +
                    "    a.florist_number, " +
                    "    a.florist_name, " +
                    "    a.florist_address, " +
                    "    a.florist_phone_number, " +
                    "    a.florist_latitude, " +
                    "    a.florist_longtitude, " +
                    "    a.user_email, " +
                    "    c.flower_name, " +
                    "    c.flower_color, " +
                    "    avg(b.florist_review_score) as florist_review_score, " +
                    "    count(b.florist_number) as florist_review_count " +
                    " from " +
                    "    florist a, florist_review b, flower c, florist_product d " +
                    " where a.florist_number = b.florist_number(+) " +
                    " and a.florist_number = d.florist_number " +
                    " and c.flower_number = d.flower_number " +
                    " and c.flower_name = :flowerName " +
                    " group by      " +
                    "    a.florist_number, " +
                    "    a.florist_name, " +
                    "    a.florist_address, " +
                    "    a.florist_phone_number, " +
                    "    a.florist_latitude, " +
                    "    a.florist_longtitude, " +
                    "    a.user_email, "+
                    "    b.florist_number, " +
                    "    c.flower_name, " +
                    "    c.flower_color "
                    , nativeQuery = true)
    List<FloristAndReviewScoreAndFlowerName> getFloristListAndReviewScoreAndFlowerColor(@Param("flowerName") String flowerName);

    @Query(value =
            "select " +
                    "    a.florist_number, " +
                    "    a.florist_name, " +
                    "    a.florist_address, " +
                    "    a.florist_phone_number, " +
                    "    a.florist_latitude, " +
                    "    a.florist_longtitude, " +
                    "    a.user_email, " +
                    "    avg(b.florist_review_score) as florist_review_score, " +
                    "    count(b.florist_number) as florist_review_count " +
                    " from " +
                    "    florist a, florist_review b " +
                    " where a.florist_number = b.florist_number " +
                    " group by      " +
                    "    a.florist_number, " +
                    "    a.florist_name, " +
                    "    a.florist_address, " +
                    "    a.florist_phone_number, " +
                    "    a.florist_latitude, " +
                    "    a.florist_longtitude, " +
                    "    a.user_email, "+
                    "    b.florist_number "+
                    "    ORDER BY florist_review_score DESC " +
                    "    FETCH FIRST 3 ROWS ONLY " , nativeQuery = true)
    List<FloristAndReviewScore> getFloristListAndReviewScoreAndTopThree();
}
