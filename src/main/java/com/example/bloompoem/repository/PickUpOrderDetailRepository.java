package com.example.bloompoem.repository;

import com.example.bloompoem.entity.PickUpOrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PickUpOrderDetailRepository extends JpaRepository<PickUpOrderDetailEntity, Integer> {
    Optional<List<PickUpOrderDetailEntity>> findByUserEmail(String userEmail);

    Optional<List<PickUpOrderDetailEntity>> findByPickUpOrderNumber(Integer orderNumber);


    @Query(value = "select count(bouquet_number)\n" +
            "  from pick_up_order_detail\n" +
            " where pick_up_order_number = :orderNumber", nativeQuery = true)
    int countByBouquet(int orderNumber);
}
