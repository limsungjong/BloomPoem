package com.example.bloompoem.repository;

import com.example.bloompoem.entity.ProductEntity;
import com.example.bloompoem.entity.ShoppingOrder;
import com.example.bloompoem.entity.ShoppingReview;
import com.example.bloompoem.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ShoppingReviewRepository extends JpaRepository<ShoppingReview, Integer> {
    ShoppingReview findByShoppingReviewNumber(int shoppingReviewNumber);

    boolean existsByShoppingOrderAndProduct(ShoppingOrder shoppingOrder, ProductEntity product);

    Page<ShoppingReview> findByUserOrderByShoppingReviewRegDateDesc(UserEntity user , Pageable pageable);

    Integer countAllByShoppingReviewRegDateBetweenAndUser(Date startDate, Date endDate , UserEntity user);

    List<ShoppingReview> findByProduct(ProductEntity product);

    @Query(value = "    SELECT nvl(AVG(SHOPPING_REVIEW_SCORE),0)\n" +
            "    FROM SHOPPING_REVIEW\n" +
            "    WHERE PRODUCT_NUMBER = :productNumber ", nativeQuery = true)
    double avgReview(@Param("productNumber") int productNumber);

    Page<ShoppingReview> findAllByProduct(ProductEntity product, Pageable pageable);

    @Query(value = "SELECT *\n" +
            "FROM SHOPPING_REVIEW\n" +
            "WHERE PRODUCT_NUMBER IN (\n" +
            "  SELECT PRODUCT_NUMBER\n" +
            "  FROM SHOPPING_REVIEW\n" +
            "  GROUP BY PRODUCT_NUMBER\n" +
            "  ORDER BY AVG(SHOPPING_REVIEW_SCORE) DESC\n" +
            "  FETCH FIRST 3 ROWS ONLY\n" +
            ")FETCH FIRST 3 ROWS ONLY", nativeQuery = true)
    List<ShoppingReview> mainPage();

}
