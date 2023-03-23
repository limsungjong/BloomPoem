package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.PickUpDateAndTImeRequest;
import com.example.bloompoem.entity.BouquetEntity;
import com.example.bloompoem.entity.Inter.OrderDetailBouquet;
import com.example.bloompoem.entity.Inter.OrderDetailResponse;
import com.example.bloompoem.entity.PickUpOrderDetailEntity;
import com.example.bloompoem.entity.PickUpOrderEntity;
import com.example.bloompoem.repository.PickUpOrderDetailRepository;
import com.example.bloompoem.repository.PickUpOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

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
                .pickUpOrderDate(LocalDate.now())
                .pickUpOrderStatus(1)
                .build();

        return pickUpOrderRepository.save(pickUpOrderEntity).getPickUpOrderNumber();
    }

    public Integer detailSaveOrder(PickUpDateAndTImeRequest request, String userEmail, Integer totalPrice) {
        Integer pickUpOrderNum = saveOrder(request, userEmail, totalPrice);
        request.getOrderList().forEach(product -> {
            if(product.getFlowerNumber() != 99999){
                pickUpOrderDetailRepository.save(PickUpOrderDetailEntity
                        .builder()
                        .userEmail(userEmail)
                        .flowerNumber(product.getFlowerNumber())
                        .pickUpOrderDetailCount(product.getFlowerCount())
                        .pickUpOrderNumber(pickUpOrderNum)
                        .floristNumber(product.getFloristNumber())
                        .build());
            }
            else if(product.getFlowerNumber() == 99999){
                BouquetEntity bouquet =new BouquetEntity();
                System.out.println("bouquet : " +product.getBouquetNumber());
                bouquet.setBouquetNumber(product.getBouquetNumber());
                pickUpOrderDetailRepository.save(PickUpOrderDetailEntity
                        .builder()
                        .userEmail(userEmail)
                        .flowerNumber(product.getFlowerNumber())
                        .pickUpOrderDetailCount(product.getFlowerCount())
                        .pickUpOrderNumber(pickUpOrderNum)
                        .floristNumber(product.getFloristNumber())
                        .bouquet(bouquet)
                        .build());
            }
        });
        return pickUpOrderNum;
    }

    @Transactional
    public void updateOrderStatus(int orderSeq, int status){
        PickUpOrderEntity pickUpOrderEntity = pickUpOrderRepository.findById(orderSeq).get();
        pickUpOrderEntity.setPickUpOrderStatus(status);
    }

    public List<OrderDetailResponse> getOderDetailResponseList(Integer orderNumber, String userEmail) {
        return pickUpOrderRepository.searchPickUpOrderSuccessResponse(userEmail, orderNumber);
    }
    public List<OrderDetailBouquet> getOrderDetailBouquetList(int pickUpOrderNumber, String userEmail){
        return  pickUpOrderRepository.pickUpOrderSuccessResponse(userEmail, pickUpOrderNumber);
    }
    public List<OrderDetailResponse> getOderDetailResponseList(String userEmail) {
        return pickUpOrderRepository.searchPickUpOrderSuccessResponse(userEmail);
    }

    public Page<PickUpOrderEntity> pickUpOrderView(Date startDate, Date endDate ,String userEmail, Pageable pageable) {
        return pickUpOrderRepository.findAllByPickUpOrderDateBetweenAndUserEmailAndPickUpOrderStatusGreaterThanEqualOrderByPickUpOrderNumberDesc(
                LocalDate.ofInstant(startDate.toInstant(), ZoneId.systemDefault()),
                LocalDate.ofInstant(endDate.toInstant(), ZoneId.systemDefault()) ,
                pageable,
                userEmail,
                3);
    }

    public int countBouquet (int orderNumber){
        return pickUpOrderDetailRepository.countByBouquet(orderNumber);
    }
}
