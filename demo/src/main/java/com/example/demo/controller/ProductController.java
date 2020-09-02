package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductDto;
import com.example.demo.security.RequestAuthorization;
import com.example.demo.tenant.service.ProductService;

import java.io.Serializable;

@RestController
@RequestMapping("/api/product")
public class ProductController implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private ProductService productService;

	 @GetMapping(value = "/all") public ResponseEntity<Object> getAllProduct() {
	  LOGGER.info("getAllProduct() method call..."); return new
	  ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK); 
	  }
	 
	 @PostMapping(value="/add")
	 public ResponseEntity<Object> addProduct(@RequestBody ProductDto productDto) {
		 productService.addProduct(productDto);
		  LOGGER.info("add all products ..."); return null; 
		  }
}
