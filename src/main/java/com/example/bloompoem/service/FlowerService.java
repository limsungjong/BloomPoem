package com.example.bloompoem.service;

import com.example.bloompoem.entity.FlowerEntity;
import com.example.bloompoem.repository.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FlowerService {
    @Autowired
    private  FlowerRepository flowerRepository;

    public Page<FlowerEntity> findFlowerAll(Pageable pageable){
        return flowerRepository.findAll(pageable);
    }
}
