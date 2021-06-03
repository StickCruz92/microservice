package com.example.customer.model.mongo;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tbl_custumer_Images")
public class CustomerImages {

	  @Id
	  private String id;
	  private Long idCustomer;
	  private String photoUrl;
	  private boolean published;
	
}
