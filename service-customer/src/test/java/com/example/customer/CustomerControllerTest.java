package com.example.customer;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.customer.model.mongo.CustomerImages;
import com.example.customer.model.mysql.Customer;
import com.example.customer.model.mysql.DocumentType;
import com.example.customer.service.CustomerImagesService;
import com.example.customer.service.CustomerService;
import com.example.customer.service.DocumentTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(properties = "spring.datasource.url=jdbc:mysql://localhost:3306/examplebackend?useSSL=false")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class CustomerControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(CustomerControllerTest.class);

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectmapper;

	@Autowired
	private DocumentTypeService documentTypeService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerImagesService ci;

	@Test
	@Order(1)
	void getCustomerId2() throws Exception {

		logger.debug("[GET BY ID INVOKE] /v1/customers/{id}");
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		logger.debug("Current Time Stamp: " + ts);
		Customer customerDB = customerService.getCustomer(2L);
		logger.info("response Customer Service : " + customerDB.getFirstName());

		String response = mockMvc.perform(get("/v1/customers/{id}/", 2)).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(jsonPath("$.firstName", is(customerDB.getFirstName()))).andReturn().getResponse()
				.getContentAsString();

		logger.info("response: " + response);
	}

	@Test
	@Order(2)
	void addCustomerError() throws Exception {
		int cr = customerService.findCustomerAll().size() + 1;
		logger.info("response Customer Service : " + cr);

		String id = String.valueOf((cr));

		logger.debug("[POST ERROR INVOKE] /v1/customers");

		DocumentType documentType = documentTypeService.findByDiminutive("cc");
		CustomerImages customerImages = ci.findByIdCustomer(2L);

		Customer customer = new Customer(cr + 1L, "test", "test", "test@test.com", LocalDate.now(), 10, "soledad",
				"1234567" + id, "CREATE", documentType, customerImages);

		String response = mockMvc
				.perform(post("/v1/customers/").content(objectmapper.writeValueAsString(customer))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn().getResponse().getContentAsString();

		logger.info(response);

	}

	@Test
	@Order(3)
	void addCustomer() throws Exception {
		logger.debug("[POST CUSTOMER] /v1/customers");

		int cr = customerService.findCustomerAll().size() + 1;
		logger.info("response Customer Service : " + cr);

		String id = String.valueOf((cr));

		DocumentType documentType = documentTypeService.findByDiminutive("cc");

		CustomerImages customerImages = ci.findByIdCustomer(2L);

		Customer customer = new Customer(cr + 1L, "test", "test", "test" + id + "@test.com", LocalDate.of(1994, 2, 20),
				10, "soledad", "1234567" + id, "CREATE", documentType, customerImages);

		String response = mockMvc
				.perform(post("/v1/customers/").content(objectmapper.writeValueAsString(customer))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.CREATED.value())).andReturn().getResponse().getContentAsString();

		logger.info(response);
	}

	@Test
	@Order(4)
	void deleteCustomer() throws Exception {
		logger.debug("[DELETE CUSTOMER] /v1/customers");
		Long Id = 0L;

		List<Customer> customers = customerService.findCustomerAll();

		for (Customer cus : customers) {
			Id = (Id < cus.getIdCustomer()) ? cus.getIdCustomer() : Id;
		}

		logger.info("response Customer Service : " + Id);

		String response = mockMvc.perform(delete("/v1/customers/{id}/", Id))
				.andExpect(status().is(HttpStatus.OK.value())).andExpect(jsonPath("$.state", is("DELETED"))).andReturn()
				.getResponse().getContentAsString();

		logger.info(response);
	}

}
