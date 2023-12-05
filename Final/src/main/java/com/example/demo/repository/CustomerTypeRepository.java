package com.example.demo.repository;

import com.example.demo.entity.Airport;
import com.example.demo.entity.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.List;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, Integer> {
    CustomerType findByTypeId(int id);
}
