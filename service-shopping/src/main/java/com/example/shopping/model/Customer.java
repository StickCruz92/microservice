package com.example.shopping.model;

import java.time.LocalDate;

import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	private Long idCustomer;
	private String firstName; 
	private String lastName;
	private String email;
    private LocalDate birthDate;
    private int age;
	private String cityBirth;
	private String identificationNumber;
    private String state;
    private DocumentType documentType;
    
    @Transient
    private CustomerImages customerImages;
    
}
