package com.example.shopping.model;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerImages {

	  @Id
	  private String id;
	  private Long idCustomer;
	  private String photoUrl;
	  private boolean published;
	
}
