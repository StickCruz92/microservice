package com.example.customer.repository.mysql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.customer.model.mysql.Customer;
import com.example.customer.model.mysql.DocumentType;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

    public Customer findByIdentificationNumber(String numberID);
    public List<Customer> findByLastName(String lastName);
    public List<Customer> findByDocumentType(DocumentType documentType);
    public Customer findByIdentificationNumberAndDocumentType(String numberID, DocumentType documentType);
    public List<Customer> findByAgeBetween(int start, int end); 
    
    public List<Customer> findByAgeLessThanEqual(int age); 
    public List<Customer> findByAgeGreaterThanEqual(int age); 

	
}
