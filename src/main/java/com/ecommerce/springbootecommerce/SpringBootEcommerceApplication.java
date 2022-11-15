package com.ecommerce.springbootecommerce;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ecommerce.springbootecommerce.api.manager.CategoryAPI;
import com.ecommerce.springbootecommerce.api.seller.ProductAPI;

@SpringBootApplication
public class SpringBootEcommerceApplication {
	public static void main(String[] args) {
	    
	    new File(ProductAPI.uploadDirectory).mkdir();
	    new File(CategoryAPI.uploadDirectory).mkdir();
	    
		SpringApplication.run(SpringBootEcommerceApplication.class, args);
	}

}
