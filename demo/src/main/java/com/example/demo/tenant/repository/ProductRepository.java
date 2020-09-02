package com.example.demo.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.tenant.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {
}
