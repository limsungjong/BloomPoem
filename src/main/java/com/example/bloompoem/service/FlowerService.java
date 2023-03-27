package com.example.bloompoem.service;

import com.example.bloompoem.entity.FlowerEntity;
import com.example.bloompoem.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlowerService {
    private final FlowerRepository flowerRepository;

    public Page<FlowerEntity> findFlowerAll(Pageable pageable){
        return flowerRepository.findAll(pageable);
    }

    public Page<FlowerEntity> searchTag(String tag ,Pageable pageable){
        return flowerRepository.findByFlowerTagContaining(tag,pageable);
    }
}
