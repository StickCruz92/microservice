package com.example.customer.service;

import java.util.List;

import com.example.customer.model.mongo.CustomerImages;

public interface CustomerImagesService {

    public List<CustomerImages> findCustomerImagesAll();
    public CustomerImages findByIdCustomer(Long id);
    public CustomerImages findByPublishedAndIdCustomer(boolean published, Long id);
    
    
    public CustomerImages createCustomerImages(CustomerImages customer);
    public CustomerImages updateCustomerImages(CustomerImages customer);
    public CustomerImages deleteCustomerImages(CustomerImages customer);
    public CustomerImages getCustomerImages(String id);
	
}
