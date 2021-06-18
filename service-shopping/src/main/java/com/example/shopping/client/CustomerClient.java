package com.example.shopping.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.shopping.model.Customer;



/*@FeignClient(value = "service-customer",
url = "http://localhost:8081/v1/customers",
configuration = ClientConfiguration.class,
fallback = CustomerHystrixFallbackFactory.class)*/
@FeignClient(name = "service-customer", url="http://localhost:8081/v1/customers", fallbackFactory = CustomerHystrixFallbackFactory.class)
public interface CustomerClient {

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id);
    
    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers();
	
}
