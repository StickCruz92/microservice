package com.example.customer.config;

import java.util.Collection;
import java.util.Collections;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


@Configuration
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = "com.example.customer.repository.mongo")
public class MongoConfig extends AbstractMongoClientConfiguration{

		@Autowired
		private Environment environment;

	    @Bean
	    public MongoClient mongo() {
	        ConnectionString connectionString = new ConnectionString(environment.getProperty("mongodb.data.mongodb.uri"));
	        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
	          .applyConnectionString(connectionString)
	          .build();
	        
	        return MongoClients.create(mongoClientSettings);
	    }
	    
		@Override
		protected String getDatabaseName() {
	        return environment.getProperty("mongodb.datasource.database");
		}
		
	    @Override
	    public Collection<String> getMappingBasePackages() {
	        return Collections.singleton("com.example.customer.model.mongo");
	    }
	    

}
