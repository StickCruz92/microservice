package com.example.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.model.mysql.Customer;
import com.example.customer.model.mysql.DocumentType;
import com.example.customer.repository.mysql.CustomerRepository;
import com.example.customer.util.DateOptions;

@Service
public class CostumerServiceImp implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private DateOptions dateOptions;

	@Override
	public List<Customer> findCustomerAll() {
		return customerRepository.findAll();
	}

	@Override
	public List<Customer> findCustomersByDocumentType(DocumentType documentType) {
		return customerRepository.findByDocumentType(documentType);
	}

	@Override
	public Customer createCustomer(Customer customer) {
		
        Customer customerDB = customerRepository.findByIdentificationNumber(customer.getIdentificationNumber());
        if (customerDB != null){
            return  customerDB;
        }
         
        int edad = dateOptions.calcularAnios(customer.getBirthDate().getDayOfMonth(), 
        		customer.getBirthDate().getMonthValue(),
        		customer.getBirthDate().getYear());
        
        customer.setAge(edad);
        customer.setState("CREATED");
        customerDB = customerRepository.save ( customer );
        return customerDB;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getIdCustomer());
        if (customerDB == null){
            return  null;
        }
        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setEmail(customer.getEmail());
        customerDB.setBirthDate(customer.getBirthDate());
        customerDB.setCityBirth(customerDB.getCityBirth());
        customerDB.setIdentificationNumber(customerDB.getIdentificationNumber());
        
        int edad = dateOptions.calcularAnios(customerDB.getBirthDate().getDayOfMonth(), 
        		customerDB.getBirthDate().getMonthValue(),
        		customerDB.getBirthDate().getYear());
        
        customerDB.setAge(edad);
        return  customerRepository.save(customerDB);
	}

	@Override
	public Customer deleteCustomer(Customer customer) {
		 Customer customerDB = getCustomer(customer.getIdCustomer());
	        if (customerDB ==null){
	            return  null;
	        }
	        customer.setState("DELETED");
	        return customerRepository.save(customer);
	}

	@Override
	public Customer getCustomer(Long idCustomer) {
		return  customerRepository.findById(idCustomer).orElse(null);
	}

	@Override
	public Customer findByIdentificationNumberAndDocumentType(String numberID, DocumentType documentType) {
		return customerRepository.findByIdentificationNumberAndDocumentType(numberID, documentType);
	}

	@Override
	public Customer findByIdentificationNumber(String numberID) {
		return customerRepository.findByIdentificationNumber(numberID);
	}

	@Override
	public List<Customer> findByAgeBetween(int start, int end) {
		return customerRepository.findByAgeBetween(start, end);
	}

	@Override
	public List<Customer> findByAgeLessThanEqual(int age) {
		return customerRepository.findByAgeLessThanEqual(age);
	}

	@Override
	public List<Customer> findByAgeGreaterThanEqual(int age) {
		return customerRepository.findByAgeGreaterThanEqual(age);
	}
	

	
}
