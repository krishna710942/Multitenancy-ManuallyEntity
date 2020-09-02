package com.example.demo.tenant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDto;
import com.example.demo.tenant.entity.Product;
import com.example.demo.tenant.repository.ProductRepository;

import java.util.List;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

	public Object addProduct(ProductDto productDto) {
		Product product=new Product();
		product.setProductName(productDto.getProductName());
		product.setQuantity(productDto.getQuantity());
		product.setSize(productDto.getSize());
		return productRepository.save(product);
	}
}
