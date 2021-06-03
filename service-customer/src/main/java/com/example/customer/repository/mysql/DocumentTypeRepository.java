package com.example.customer.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.customer.model.mysql.DocumentType;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long>{

	public DocumentType findByName(String name);
	public DocumentType findByDiminutive(String diminutive);
	
}
