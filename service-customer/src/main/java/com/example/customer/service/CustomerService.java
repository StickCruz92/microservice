package com.example.customer.service;

import java.util.List;

import com.example.customer.model.mysql.Customer;
import com.example.customer.model.mysql.DocumentType;

public interface CustomerService {

    public List<Customer> findCustomerAll();
    public List<Customer> findCustomersByDocumentType(DocumentType documentType);

    public Customer createCustomer(Customer customer);
    public Customer updateCustomer(Customer customer);
    public Customer deleteCustomer(Customer customer);
    public Customer getCustomer(Long id);
    public Customer findByIdentificationNumberAndDocumentType(String numberID, DocumentType documentType);
    public Customer findByIdentificationNumber(String numberID);
    public List<Customer> findByAgeBetween(int start, int end);
    
    public List<Customer> findByAgeLessThanEqual(int age); 
    public List<Customer> findByAgeGreaterThanEqual(int age); 
}
