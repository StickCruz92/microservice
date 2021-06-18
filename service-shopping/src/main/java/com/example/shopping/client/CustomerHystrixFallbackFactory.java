package com.example.shopping.client;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.shopping.model.Customer;

@Component
public class CustomerHystrixFallbackFactory implements CustomerClient{

	@Override
	public ResponseEntity<Customer> getCustomer(long id) {
        Customer customer = Customer.builder()
    	.firstName("none")
    	.lastName("none")
    	.email("none")
        .birthDate(null)
        .age(0)
    	.cityBirth("none")
    	.identificationNumber("none")
        .state("none")
        .customerImages(null)
        .documentType(null)
        .build();
        return ResponseEntity.ok(customer);
	}

	@Override
	public ResponseEntity<List<Customer>> listAllCustomers() {
		return null;
	}

}
