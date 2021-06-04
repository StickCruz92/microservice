package com.example.customer.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.customer.model.mysql.Customer;
import com.example.customer.model.mysql.DocumentType;
import com.example.customer.service.CustomerImagesService;
import com.example.customer.service.CustomerService;
import com.example.customer.service.DocumentTypeService;
import com.example.customer.util.FormatMessageError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Slf4j
@RestController
@RequestMapping("/v1/customers")
@Api(tags = "/v1/customer", produces = "application/json")
public class CustomerRest {

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private DocumentTypeService documentTypeService;
    
    @Autowired
    private CustomerImagesService customerImagesService;
    
    @Autowired
    private FormatMessageError formatMessageError;

    // -------------------Retrieve All Customers--------------------------------------------

    @ApiOperation(value = "List all customers", notes = "View a list of available customers")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers(@RequestParam(name = "documentTypeId" , required = false) Long documentTypeId ) {
        List<Customer> customers =  new ArrayList<>();
        if (null ==  documentTypeId) {
            customers = customerService.findCustomerAll();
            if (customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
            	for (Customer cust : customers) {
            		cust.setCustomerImages(customerImagesService.findByIdCustomer(cust.getIdCustomer()));
				}
            }
        }else{
            DocumentType documentType= new DocumentType();
            documentType.setId(documentTypeId);
            customers = customerService.findCustomersByDocumentType(documentType);
            if ( null == customers ) {
                log.error("Customers with Region id {} not found.", documentTypeId);
                return  ResponseEntity.notFound().build();
            }
        }

        return  ResponseEntity.ok(customers);
    }

    // -------------------Retrieve Single Customer------------------------------------------

    @ApiOperation(value = "Search a customer with an ID",response = Customer.class)
    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id) {
        log.info("Fetching Customer with id {}", id);
        Customer customer = customerService.getCustomer(id);
        if (  null == customer) {
            log.error("Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        
        customer.setCustomerImages(customerImagesService.findByIdCustomer(customer.getIdCustomer()));
        return  ResponseEntity.ok(customer);
    }

    // -------------------Create a Customer-------------------------------------------

    @ApiOperation(value = "Add a customer")
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        log.info("Creating Customer : {}", customer);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessageError.formatMessage(result));
        }

       Customer customerDB = customerService.createCustomer (customer);

        return ResponseEntity.status( HttpStatus.CREATED).body(customerDB);
    }

    // ------------------- Update a Customer ------------------------------------------------

    @ApiOperation(value = "Update a customer")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
        log.info("Updating Customer with id {}", id);

        Customer currentCustomer = customerService.getCustomer(id);

        if ( null == currentCustomer ) {
            log.error("Unable to update. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        customer.setIdCustomer(id);
        currentCustomer=customerService.updateCustomer(customer);
        return  ResponseEntity.ok(currentCustomer);
    }

    // ------------------- Delete a Customer-----------------------------------------

    @ApiOperation(value = "Delete a customer")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Customer with id {}", id);

        Customer customer = customerService.getCustomer(id);
        if ( null == customer ) {
            log.error("Unable to delete. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        customer = customerService.deleteCustomer(customer);
        return  ResponseEntity.ok(customer);
    }
        
    @ApiOperation(value = "Search a customer with an NumberID And Diminutive document Type",response = Customer.class)
    @GetMapping(value = "/{numberid}/tipo")
	public ResponseEntity<Customer> getCustomerIdentificacionAndTipo(@PathVariable("numberid") String numberId, 
			@RequestParam(name = "tipodocumento", required = true)  String tipoDocumento) 
	{
    	log.info("buscar numero de id" + numberId + " con tipo de identificacion diminutivo  " + tipoDocumento );
    	
		DocumentType documentType = documentTypeService.findByDiminutive(tipoDocumento);
		
		log.info("existe el tipo de docuemnto  {}", documentType);
		if (documentType == null) {
			return ResponseEntity.noContent().build();
		}
		
		Customer customer = customerService.findByIdentificationNumberAndDocumentType(numberId, documentType);
		log.info("existe el usuario con ese tipo de documento  {}", customer);
		customer.setCustomerImages(customerImagesService.findByIdCustomer(customer.getIdCustomer()));
		
		
		log.info("respuesta {}", customer);
		return ResponseEntity.ok(customer);
	}
    
    @ApiOperation(value = "Search a customer with an Age customer greater than equal")
    @GetMapping(value = "/agegreaterthanequal/{age}")
	public ResponseEntity<List<Customer>> getCustomerAgeGreaterThanEqual(@PathVariable("age") int age) 
	{
    	log.info("age greater than equal{}", age);
    	
    	if (age <= 0) {
			return ResponseEntity.noContent().build();
		}
    	
		List<Customer> customers = customerService.findByAgeGreaterThanEqual(age);
		log.info("respuesta {}", customers.isEmpty());
		if (customers.isEmpty()) {
	    	
			return ResponseEntity.noContent().build();
			
		} else {
			
			for (Customer cust : customers) 
			{
	    		cust.setCustomerImages(customerImagesService.findByIdCustomer(cust.getIdCustomer()));
			}
			
			return ResponseEntity.ok(customers);
		}		
		
	}
    
    @ApiOperation(value = "Search a customer with an Age customer less than equal")
    @GetMapping(value = "/agelessthanequal/{age}")
	public ResponseEntity<List<Customer>> getCustomerAgeLessThanEqual(@PathVariable("age") int age) 
	{
    	log.info("age less than equal {} ", age);
    	
    	if (age <= 0) {
			return ResponseEntity.noContent().build();
		}
		
		List<Customer> customers = customerService.findByAgeLessThanEqual(age);
		log.info("respuesta {}", customers.isEmpty());
		if (customers.isEmpty()) {
	    	
			return ResponseEntity.noContent().build();
			
		} else {
			
			for (Customer cust : customers) 
			{
	    		cust.setCustomerImages(customerImagesService.findByIdCustomer(cust.getIdCustomer()));
			}
			
			return ResponseEntity.ok(customers);
		}
	}
}
