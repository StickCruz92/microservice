package com.example.customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "spring.data.mongodb.uri=mongodb://localhost:27017/backend-images")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class CustomerImagesTestMock {

	private static final Logger logger = LoggerFactory.getLogger(CustomerControllerTest.class);

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	void getCustomersImages() throws Exception {

		logger.debug("[GET BY ID INVOKE] /v1/customerimages");
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		logger.debug("Current Time Stamp: " + ts);

		String response = mockMvc.perform(get("/v1/customerimages")).andExpect(status()
				.is(HttpStatus.OK.value()))
				.andReturn().getResponse()
				.getContentAsString();
	
		logger.info("customerimages response: " + response);
	}
}
