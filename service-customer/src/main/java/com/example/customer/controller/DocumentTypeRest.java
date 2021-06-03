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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.customer.model.mysql.DocumentType;
import com.example.customer.service.DocumentTypeService;
import com.example.customer.util.FormatMessageError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/documents")
public class DocumentTypeRest {

	@Autowired
	private DocumentTypeService documentTypeService;
	
    @Autowired
    private FormatMessageError formatMessageError;
	
	// -------------------Retrieve All Documents --------------------------------------------
	
	@GetMapping
	public ResponseEntity<List<DocumentType>> listAllDocumentType() {
		
		List<DocumentType> list = new ArrayList<>();
		log.info("list all Document Type : {}", list);
		list = documentTypeService.getAllDocumentType();
		
		if (list.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(list);
		
	}
	
	// -------------------Retrieve Single Document --------------------------------------------
	
	@GetMapping(value = "{id}")
	public ResponseEntity<DocumentType> getDocumentType(@PathVariable("id") long id) {
		DocumentType documentType = documentTypeService.getDocumentType(id);
		
		if (documentType == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(documentType);
	}
	
   // -------------------Create a Document -------------------------------------------
	
	
    @PostMapping
    public ResponseEntity<DocumentType> createDocumentType(@Valid @RequestBody DocumentType documentType, BindingResult result) {
        log.info("Creating Document Type : {}", documentType);
        if (result.hasErrors()){
        	log.info("has Errors : {}", result);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessageError.formatMessage(result));
        }

        DocumentType documentTypeDB = documentTypeService.createDocumentType(documentType);

        return  ResponseEntity.status( HttpStatus.CREATED).body(documentTypeDB);
    }
	
    // ------------------- Update a Document ------------------------------------------------

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDocumentType(@PathVariable("id") long id, @RequestBody DocumentType documentType) {
        log.info("Updating Document Type with id {}", id);

        DocumentType currentDocumentType = documentTypeService.getDocumentType(id);

        if ( null == currentDocumentType ) {
            log.error("Unable to update. Document Type with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        documentType.setId(id);
        currentDocumentType=documentTypeService.updateDocumentType(currentDocumentType);
        return  ResponseEntity.ok(currentDocumentType);
    }

    // ------------------- Delete a Document-----------------------------------------

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DocumentType> deleteDocumentType(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Document Type with id {}", id);

        DocumentType documentType = documentTypeService.getDocumentType(id);
        if ( null == documentType ) {
            log.error("Unable to delete. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        documentType = documentTypeService.deleteDocumentType(documentType);
        return  ResponseEntity.ok(documentType);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
