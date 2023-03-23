package com.example.bloompoem.service;

import com.example.bloompoem.repository.BouquetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BouquetService {

    @Autowired
    private BouquetRepository BouquetDao;

}
