package com.example.customer.service;

import java.util.List;

import com.example.customer.model.mysql.DocumentType;

public interface DocumentTypeService {

	public DocumentType createDocumentType(DocumentType documentType);
	public DocumentType updateDocumentType(DocumentType documentType);
	public List<DocumentType> getAllDocumentType();
	public DocumentType getDocumentType(Long idDocumentType);
	public DocumentType deleteDocumentType(DocumentType documentType);
	public DocumentType findByDiminutive(String diminutive);

}
