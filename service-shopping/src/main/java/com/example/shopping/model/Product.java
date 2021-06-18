package com.example.shopping.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	private Long id;
	
	private String name;
	
	private String description;
	
	private Double stock;
	
	private Double price;
	private String status;

	private Date createAt;
	
	private Category category;
}
