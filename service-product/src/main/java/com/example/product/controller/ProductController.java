package com.example.product.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.product.model.Category;
import com.example.product.model.Product;
import com.example.product.service.ProductService;
import com.example.product.util.FormatMessageError;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/v1/products")
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productService;
	
    @Autowired
    private FormatMessageError formatMessageError;
	
	@GetMapping
	public @ResponseBody ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryid", required = false) Long category_id) {
		List<Product> products = new ArrayList<>();
		if (category_id == null) {
			 products = productService.listAllProduct();
			 if (products.isEmpty()) {
				 return ResponseEntity.noContent().build();
			 }
		} else {
			products = productService.findByCategory(Category.builder().id(category_id).build());
			 if (products.isEmpty()) {
				 return ResponseEntity.notFound().build();
			 }
		}
		
		if (products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(products);
		
	}
	
	@GetMapping(value = "/{id}")
	public @ResponseBody ResponseEntity<Product> getProduct(@PathVariable(name = "id") Long idProducto) {
		Product product = productService.getProduct(idProducto);
			 if (product == null) {
				 return ResponseEntity.noContent().build();
			 }

		return ResponseEntity.ok(product);
		
	}
	
	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result) 
	{
        if (result.hasErrors()){
	        log.info("hay errores" + result.hasErrors());
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessageError.formatMessage(result));
        }
		
		Product productCreate = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
		
	}
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) 
	{
		product.setId(id);
		Product productDB = productService.updateProduct(product);
		if (productDB == null) {
			 return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(productDB);
		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) 
	{
		Product productDelete = productService.deleteProduct(id);
		if (productDelete == null) {
			 return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(productDelete);
		
	}
	
	@GetMapping(value = "/{id}/stock")
	public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id, @RequestParam(name = "quantity", required = true) Double quantity) 
	{
		Product productDB = productService.updateStock(id, quantity);
		if (productDB == null) {
			 return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(productDB);
		
	}
	
}
