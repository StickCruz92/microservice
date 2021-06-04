package com.example.customer;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.customer.service.DocumentTypeService;

@SpringBootTest(properties = "spring.datasource.url=jdbc:mysql://localhost:3306/examplebackend?useSSL=false")
class CustomerServiceTestMock {
    
    @Autowired
    private DocumentTypeService documentTypeService;
 
    
    @Test
    void test() {
        assertEquals(4, documentTypeService.getAllDocumentType().size());
    }
    


}
