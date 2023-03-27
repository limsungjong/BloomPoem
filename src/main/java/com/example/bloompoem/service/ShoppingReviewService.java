package com.example.bloompoem.service;

import com.example.bloompoem.entity.ProductEntity;
import com.example.bloompoem.entity.ShoppingOrder;
import com.example.bloompoem.entity.ShoppingReview;
import com.example.bloompoem.entity.UserEntity;
import com.example.bloompoem.repository.ShoppingReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingReviewService {
    private final ShoppingReviewRepository shoppingReviewDao;
    public int insertReview(ShoppingReview shoppingReview){
         return shoppingReviewDao.save(shoppingReview).getShoppingReviewNumber();
    }

    public ShoppingReview  reviewSelect(int shoppingReviewNumber){
        return shoppingReviewDao.findByShoppingReviewNumber(shoppingReviewNumber);
    }
    public boolean existsReview (int shoppingOrderNumber, int productNumber){
        ProductEntity product =new ProductEntity();
        product.setProductNumber(productNumber);
        ShoppingOrder shoppingOrder =new ShoppingOrder();
        shoppingOrder.setShoppingOrderNumber(shoppingOrderNumber);
        return shoppingReviewDao.existsByShoppingOrderAndProduct(shoppingOrder,product);
    }

    public Page<ShoppingReview> readReview (String userEmail, Pageable pageable){
        UserEntity user = new UserEntity();
        user.setUserEmail(userEmail);
        return shoppingReviewDao.findByUserOrderByShoppingReviewRegDateDesc(user, pageable);
    }
    public  void deletReview(int shoppingReviewNumber ){
        shoppingReviewDao.deleteById(shoppingReviewNumber);
    }
    public double avgReviewScore(int productNumber){
        return shoppingReviewDao.avgReview(productNumber);
    }
    public Page<ShoppingReview> readAll (int productNumber, Pageable pageable){
        ProductEntity product =new ProductEntity();
        product.setProductNumber(productNumber);
        return shoppingReviewDao.findAllByProduct(product, pageable);
    }

    public List<ShoppingReview> reteHighProductView(){
        return shoppingReviewDao.mainPage();
    }
    
}
