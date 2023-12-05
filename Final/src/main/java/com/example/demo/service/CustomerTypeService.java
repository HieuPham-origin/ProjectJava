package com.example.demo.service;

import com.example.demo.entity.CustomerType;

public interface CustomerTypeService {
    CustomerType getCustomerTypeById(int id);
    CustomerType getCustomerTypeByName(String s);
}
