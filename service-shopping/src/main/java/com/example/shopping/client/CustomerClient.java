package com.example.shopping.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.shopping.model.Customer;

//@FeignClient(name = "service-customer", url="http://localhost:8081/v1/customers", fallback = CustomerHystrixFallbackFactory.class)
//@RequestMapping("/customers")
@FeignClient(name = "service-customer", url="http://localhost:8081/v1/customers")
public interface CustomerClient {

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id);
    
    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers();
	
}
