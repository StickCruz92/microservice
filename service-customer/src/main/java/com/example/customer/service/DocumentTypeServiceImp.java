package com.example.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.model.mysql.DocumentType;
import com.example.customer.repository.mysql.DocumentTypeRepository;

@Service
public class DocumentTypeServiceImp implements DocumentTypeService {

	@Autowired
	private DocumentTypeRepository documentTypeRepository;
	
	@Override
	public DocumentType createDocumentType(DocumentType documentType) {
		DocumentType documentTypeDB = documentTypeRepository.findByName(documentType.getName());
		if (documentTypeDB != null) {
			return documentTypeDB;
		}
		
		documentType.setStatus("CREATED");
		documentTypeDB = documentTypeRepository.save(documentType);
		return documentTypeDB;
	}

	@Override
	public DocumentType updateDocumentType(DocumentType documentType) {
		DocumentType documentTypeDB = getDocumentType(documentType.getId());
		if (documentTypeDB == null) {
			return null;
		}
		documentTypeDB.setName(documentType.getName());
		documentTypeDB.setNationality(documentType.getNationality());
		
		return documentTypeRepository.save(documentTypeDB);
	}

	@Override
	public List<DocumentType> getAllDocumentType() {
		return documentTypeRepository.findAll();
	}

	@Override
	public DocumentType getDocumentType(Long id) {
		return documentTypeRepository.findById(id).orElse(null);
	}

	@Override
	public DocumentType deleteDocumentType(DocumentType documentType) {
		DocumentType documentTypeDB = getDocumentType(documentType.getId());
		if (documentTypeDB == null) {
			return null;
		}
		documentTypeDB.setStatus("DELETED");
		return documentTypeRepository.save(documentTypeDB);

	}

	@Override
	public DocumentType findByDiminutive(String diminutive) {
		return documentTypeRepository.findByDiminutive(diminutive);
	}

}
