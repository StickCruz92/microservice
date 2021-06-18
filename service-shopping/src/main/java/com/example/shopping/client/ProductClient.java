package com.example.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.shopping.model.Product;

@FeignClient(name = "service-product", url="http://localhost:8082/v1/products", fallbackFactory = CustomerHystrixFallbackFactory.class)
public interface ProductClient {

	@GetMapping(value = "/{id}")
	public @ResponseBody ResponseEntity<Product> getProduct(@PathVariable(name = "id") Long idProducto);

	@GetMapping(value = "/{id}/stock")
	public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id, @RequestParam(name = "quantity", required = true) Double quantity) ;
	
	
    }
