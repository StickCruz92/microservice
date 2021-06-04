package com.example.customer.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.customer.model.mongo.CustomerImages;
import com.example.customer.model.mysql.Customer;
import com.example.customer.service.CustomerImagesService;
import com.example.customer.service.CustomerService;
import com.example.customer.util.ImageOptions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/customerimages")
@Api(tags = "/v1/customerimages", produces = "application/json")
public class CustomerImagesRest {
	
    @Autowired
    private CustomerService customerService;
	
    @Autowired
    private ImageOptions imagesOprions;
    
    @Autowired
    private CustomerImagesService customerImagesService;

    /*--------------------------------IMAGES-------------------------------------*/

    @ApiOperation(value = "View a list of available customers imsages")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping
    public ResponseEntity<List<CustomerImages>> listAllCustomerImages() {
        List<CustomerImages> customersImages =  new ArrayList<>();
        
        customersImages = customerImagesService.findCustomerImagesAll();
            if (customersImages.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

        return  ResponseEntity.ok(customersImages);
    }
    
    // -------------------Retrieve Single Customer------------------------------------------

    @ApiOperation(value = "Add a customer images")
    @GetMapping(value = "/customer/{idCustomer}")
    public ResponseEntity<CustomerImages> getCustomerImages(@PathVariable("idCustomer") long id) {
        log.info("Fetching Customer with id {}", id);
        CustomerImages customerImages = customerImagesService.findByIdCustomer(id);
        if (null == customerImages) {
            log.error("Customer Images with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(customerImages);
    }

    
    // -------------------Create a CustomerImages-------------------------------------------

    @ApiOperation(value = "Create or Update a customer images")
	@PostMapping(headers ="content-type=multipart/form-data")
	public ResponseEntity<?> createCustomerImages(@RequestParam(required = true, name = "numberid") String id,
			@RequestParam("file") MultipartFile multipartFile, UriComponentsBuilder componentsBuilder) {
		
		CustomerImages customerImagesDB = null;
		
		log.info("Creating Customer Images : {}", id); log.info("miltipart : {}", multipartFile);
		
		if (multipartFile == null || multipartFile.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		Customer customerDB = customerService.findByIdentificationNumber(id);

		log.info("customer DB : {}", customerDB);
		if (customerDB == null) {
			return ResponseEntity.noContent().build();
		}

		customerImagesDB = customerImagesService.findByIdCustomer(customerDB.getIdCustomer());

		log.info("customer Images DB : {}", customerImagesDB);
		if (customerImagesDB != null) {
			log.info("customer Images diferente de null DB : {}", customerImagesDB);
			if (!customerImagesDB.getPhotoUrl().isEmpty() || customerImagesDB.getPhotoUrl() != null) {
				log.info("validar la ruta de la images : {}", customerImagesDB.getPhotoUrl().isEmpty());
				imagesOprions.removePinture(customerImagesDB.getPhotoUrl());
			}
		} else {
			customerImagesDB = new CustomerImages();
		}
		
		String filePath = imagesOprions.savePinture(multipartFile, customerDB.getIdCustomer());
		
		if (!filePath.isEmpty()) {
			log.info("ruta de archivo : {}", filePath);
			customerImagesDB.setPhotoUrl(filePath);
			customerImagesDB.setIdCustomer(customerDB.getIdCustomer());

			 return  ResponseEntity.ok(customerImagesService.createCustomerImages(customerImagesDB));
			 
		} else {
			
			return ResponseEntity.badRequest().build();
		}
	}
    
    // ------------------- Update a Customer ------------------------------------------------

    @ApiOperation(value = "Update a customer images")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCustomerImages(@PathVariable("id") long id, @RequestBody CustomerImages customer) {
        log.info("Updating Customer with id {}", id);

        CustomerImages currentCustomerImages = customerImagesService.findByIdCustomer(id);

        if ( null == currentCustomerImages ) {
            log.error("Unable to update. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        
        log.info("Customer Images ecnontrado {}", currentCustomerImages);
        
        customer.setIdCustomer(currentCustomerImages.getIdCustomer());
        customer.setId(currentCustomerImages.getId());
        log.info("Customer with id {}", customer);

        currentCustomerImages=customerImagesService.updateCustomerImages(customer);
        return  ResponseEntity.ok(currentCustomerImages);
    }
	
}
