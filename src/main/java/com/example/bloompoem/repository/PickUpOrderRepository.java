package com.example.bloompoem.repository;

import com.example.bloompoem.entity.Inter.OrderDetailBouquet;
import com.example.bloompoem.entity.Inter.OrderDetailResponse;
import com.example.bloompoem.entity.Inter.PickUpOrderResponse;
import com.example.bloompoem.entity.PickUpOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PickUpOrderRepository extends JpaRepository<PickUpOrderEntity,Integer> {
    Optional<List<PickUpOrderEntity>> findByUserEmail(String userEmail);

    @Query(value =
            "SELECT a.pick_up_order_reservation_time, " +
                    "       a.pick_up_order_reservation_date, " +
                    "       a.user_email, " +
                    "       a.pick_up_order_date, " +
                    "       a.pick_up_order_real_price, " +
                    "       d.flower_name, " +
                    "       a.pick_up_order_number, " +
                    "       c.flower_count, " +
                    "       c.florist_number " +
                    " from pick_up_order a, pick_up_order_detail b, pick_up_cart c, flower d " +
                    " WHERE a.pick_up_order_number = b.pick_up_order_number " +
                    " and c.flower_number = b.flower_number " +
                    " and c.flower_number = d.flower_number " +
                    " and c.flower_count = b.pick_up_order_detail_count " +
                    " and a.user_email = :userEmail " +
                    " and a.pick_up_order_number = :pickUpOrderSeq ", nativeQuery = true)
    List<PickUpOrderResponse> searchPickUpOrderResponse(
            @Param("userEmail") String userEmail,
            @Param("pickUpOrderSeq") Integer pickUpOrderSeq);

    @Query(value =
            "select a.pick_up_order_reservation_time, " +
                    "       a.pick_up_order_reservation_date, " +
                    "       a.user_email, " +
                    "       a.pick_up_order_date, " +
                    "       a.pick_up_order_real_price, " +
                    "       c.flower_name, " +
                    "       c.flower_number, " +
                    "       a.pick_up_order_number, " +
                    "       b.pick_up_order_detail_count, " +
                    "       b.florist_number, " +
                    "       d.florist_main_image, " +
                    "       d.florist_product_price, " +
                    "       e.florist_name " +
                    " from pick_up_order a, pick_up_order_detail b, flower c, florist_product d, florist e " +
                    " where a.pick_up_order_number = b.pick_up_order_number " +
                    " and c.flower_number = b.flower_number " +
                    " and c.flower_number = d.flower_number " +
                    " and d.florist_number = b.florist_number " +
                    " and b.florist_number = e.florist_number " +
                    " and a.user_email = :userEmail " +
                    " and a.pick_up_order_number = :pickUpOrderSeq ", nativeQuery = true)
    List<OrderDetailResponse> searchPickUpOrderSuccessResponse(
            @Param("userEmail") String userEmail,
            @Param("pickUpOrderSeq") Integer pickUpOrderSeq);

    @Query(value =
            "select a.pick_up_order_reservation_time, " +
                    "       a.pick_up_order_reservation_date, " +
                    "       a.user_email, " +
                    "       a.pick_up_order_date, " +
                    "       a.pick_up_order_real_price, " +
                    "       c.flower_name, " +
                    "       c.flower_number, " +
                    "       a.pick_up_order_number, " +
                    "       b.pick_up_order_detail_count, " +
                    "       b.florist_number, " +
                    "       d.florist_main_image, " +
                    "       d.florist_product_price " +
                    " from pick_up_order a, pick_up_order_detail b, flower c, florist_product d " +
                    " where a.pick_up_order_number = b.pick_up_order_number " +
                    " and c.flower_number = b.flower_number " +
                    " and c.flower_number = d.flower_number " +
                    " and d.florist_number = b.florist_number " +
                    " and a.user_email = :userEmail " , nativeQuery = true)
    List<OrderDetailResponse> searchPickUpOrderSuccessResponse(
            @Param("userEmail") String userEmail);

    Page<PickUpOrderEntity> findAllByPickUpOrderDateBetweenAndUserEmailAndPickUpOrderStatusGreaterThanEqualOrderByPickUpOrderNumberDesc(LocalDate startDate, LocalDate endDate , Pageable pageable, String userEmail , int status);

    List<PickUpOrderEntity> findAllByPickUpOrderDateBetweenAndUserEmailAndPickUpOrderStatusGreaterThanEqualOrderByPickUpOrderNumberDesc(LocalDate startDate, LocalDate endDate ,String userEmail , int status);


    @Query(value = "    select " +
            "        a.pick_up_order_reservation_time, " +
            "        a.pick_up_order_reservation_date, " +
            "        a.user_email, " +
            "        a.pick_up_order_date, " +
            "        a.pick_up_order_real_price, " +
            "        c.flower_name, " +
            "        c.flower_number, " +
            "        a.pick_up_order_number, " +
            "        b.pick_up_order_detail_count, " +
            "        b.florist_number, " +
            "        d.bouquet_main_image, " +
            "        e.florist_name ," +
            "        d.bouquet_price" +
            "    from " +
            "        pick_up_order a, " +
            "        pick_up_order_detail b, " +
            "        flower c, " +
            "        bouquet d, " +
            "        florist e " +
            "    where " +
            "        a.pick_up_order_number = b.pick_up_order_number   " +
            "        and b.florist_number = d.florist_number " +
            "        and e.florist_number = d.florist_number " +
            "        and c.flower_number = b.flower_number " +
            "        and d.bouquet_number = b.bouquet_number " +
            "        and a.user_email = :userEmail " +
            "        and a.pick_up_order_number = :pickUpOrderNumber "
            , nativeQuery = true)
    List<OrderDetailBouquet> pickUpOrderSuccessResponse(String userEmail, Integer pickUpOrderNumber);
}