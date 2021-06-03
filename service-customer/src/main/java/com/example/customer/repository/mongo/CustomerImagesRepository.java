package com.example.customer.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.customer.model.mongo.CustomerImages;

@Repository
public interface CustomerImagesRepository extends MongoRepository<CustomerImages, String> {

	  CustomerImages findByIdCustomer(Long idCustomer);
	  
	  CustomerImages findByPublishedAndIdCustomer(boolean published, Long idCustomer);
	
}
