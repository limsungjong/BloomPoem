package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.PickUpDateAndTImeRequest;
import com.example.bloompoem.entity.PickUpOrderDetailEntity;
import com.example.bloompoem.entity.PickUpOrderEntity;
import com.example.bloompoem.repository.PickUpOrderDetailRepository;
import com.example.bloompoem.repository.PickUpOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final PickUpOrderRepository pickUpOrderRepository;

    private final PickUpOrderDetailRepository pickUpOrderDetailRepository;

    private Integer saveOrder(PickUpDateAndTImeRequest request, String userEmail, Integer totalPrice) {

        PickUpOrderEntity pickUpOrderEntity = PickUpOrderEntity
                .builder()
                .userEmail(userEmail)
                .pickUpOrderReservationDate(request.getDate())
                .pickUpOrderReservationTime(request.getTime())
                .pickUpOrderTotalPrice(totalPrice)
                .pickUpOrderRealPrice(totalPrice)
                .pickUpOrderDate(new Date())
                .pickUpOrderStatus('1')
                .build();

        return pickUpOrderRepository.save(pickUpOrderEntity).getPickUpOrderNumber();
    }

    public void detailSaveOrder(PickUpDateAndTImeRequest request, String userEmail, Integer totalPrice) {
        Integer pickUpOrderNum = saveOrder(request, userEmail, totalPrice);
        request.getOrderList().forEach(product -> {
            pickUpOrderDetailRepository.save(PickUpOrderDetailEntity
                    .builder()
                    .userEmail(userEmail)
                    .flowerNumber(product.getFlowerNumber())
                    .pickUpOrderDetailCount(product.getFlowerCount())
                    .pickUpOrderNumber(pickUpOrderNum)
                    .build());
        });
    }
}
