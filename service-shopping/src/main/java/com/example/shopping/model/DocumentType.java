package com.example.shopping.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentType {

	private Long id;
	private String name;
	private String diminutive;
	private String nationality;
	private String status;
	
}
