package com.example.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.model.mongo.CustomerImages;
import com.example.customer.repository.mongo.CustomerImagesRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerImagesServiceImp implements CustomerImagesService {

	
	@Autowired
	private CustomerImagesRepository customerImagesRepository;
	
	@Override
	public List<CustomerImages> findCustomerImagesAll() {
		return customerImagesRepository.findAll();
	}

	@Override
	public CustomerImages findByIdCustomer(Long id) {
		return customerImagesRepository.findByIdCustomer(id);
	}

	@Override
	public CustomerImages findByPublishedAndIdCustomer(boolean published, Long id) {
		return customerImagesRepository.findByPublishedAndIdCustomer(published, id);
	}

	@Override
	public CustomerImages createCustomerImages(CustomerImages customer) {
		
		 CustomerImages customerImagesDB = customerImagesRepository.findByIdCustomer(customer.getIdCustomer());
	        if (customerImagesDB != null){
	        	return updateCustomerImages(customer);
	        }

	        customer.setPublished(true);
	        customerImagesDB = customerImagesRepository.save(customer);
	        return customerImagesDB;
	}

	@Override
	public CustomerImages updateCustomerImages(CustomerImages customer) {
		
		 CustomerImages customerImagesDB = customerImagesRepository.findByIdCustomer(customer.getIdCustomer());
		 log.info("find -> ", customerImagesDB);
	        if (customerImagesDB == null){
	            return  customerImagesDB;
	        }
	        
	   customerImagesDB.setPhotoUrl(customer.getPhotoUrl());
		return customerImagesRepository.save(customerImagesDB);
	}

	@Override
	public CustomerImages deleteCustomerImages(CustomerImages customer) {
		
		 CustomerImages customerImagesDB = customerImagesRepository.findByIdCustomer(customer.getIdCustomer());
	        if (customerImagesDB == null){
	            return  customerImagesDB;
	        }
	        
	        customer.setPublished(false);
	        customerImagesDB = customerImagesRepository.save(customer);
	        return customerImagesDB;
	}

	@Override
	public CustomerImages getCustomerImages(String id) {
		return customerImagesRepository.findById(id).orElse(null);
	}



}
