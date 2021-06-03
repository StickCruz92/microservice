package com.example.customer.model.mysql;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder 
@NoArgsConstructor
@Entity
@Table(name = "tbl_document_type")
public class DocumentType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "El campo nombre no puede ser vacío")
	@Size( min = 2, max = 50, message = "El tamaño del campo nombre debe estar entre 2 a 50 caracteres")
	private String name;
	
	@NotEmpty(message = "El campo del nombre diminutivo de la identificación no puede ser vacío")
	@Size( min = 1 , max = 50, message = "El tamaño del campo diminutivo debe estar entre 1 a 50 caracteres")
	private String diminutive;
	
	@NotEmpty(message = "El campo nacionalidad no puede ser vacío")
	@Size( min = 3 , max = 50, message = "El tamaño del campo nacionalidad debe estar entre 3 a 50 caracteres")
	private String nationality;
	
	private String status;
	
}
