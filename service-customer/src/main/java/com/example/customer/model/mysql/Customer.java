package com.example.customer.model.mysql;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.customer.model.mongo.CustomerImages;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idCustomer;
	
	@NotEmpty(message = "El campo nombre no puede ser vacío")
	@Size( min = 3 , max = 50, message = "El tamaño del campo nombre debe estar entre 3 a 50 caracteres")
	@Column(name = "first_name")
	private String firstName; 
	
	@NotEmpty(message = "El campo apellido no puede ser vacío")
	@Size( min = 3 , max = 50, message = "El tamaño del campo apellido debe estar entre 3 a 50 caracteres")
	@Column(name = "last_name")
	private String lastName;
	
    @NotEmpty(message = "el correo electrónico no puede estar vacío")
    @Email(message = "no es un dirección de correo electrónico bien formada")
    @Column(unique=true, nullable=false)
	private String email;
	
    @Past(message = "fecha inavlida")
	@Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Por farvor ingresa un fecha.")
    private LocalDate birthDate;
    
    private int age;
	
    @NotEmpty(message = "el campo ciudad de nacieminto no puede estar vacío")
    @Size( min = 3 , max = 50, message = "El tamaño del campo ciudad de nacieminto debe estar entre 3 a 50 caracteres")
	@Column(name = "city_birth")
	private String cityBirth;
	
    @NotEmpty(message = "el campo número de identificación no puede estar vacío")
    @Size( min = 3 , max = 50, message = "El tamaño del campo número de identificación debe estar entre 3 a 50 caracteres")
	@Column(name = "identification_number")
	private String identificationNumber;
	
    private String state;
    
    @NotNull(message = "el tipo de documento no puede estar vacío")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private DocumentType documentType;
    
    @Transient
    private CustomerImages customerImages;
    

    
	
}
