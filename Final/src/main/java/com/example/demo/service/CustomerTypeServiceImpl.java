package com.example.demo.service;

import com.example.demo.entity.CustomerType;
import com.example.demo.repository.CustomerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerTypeServiceImpl implements CustomerTypeService{

    @Autowired
    CustomerTypeRepository customerTypeRepository;
    @Override
    public CustomerType getCustomerTypeById(int id) {
        return customerTypeRepository.findByTypeId(id);
    }
}
