package com.example.customer.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "mysqlEntityManagerFactory", transactionManagerRef = "mysqlTtransactionManager",
basePackages = {"com.example.customer.repository.mysql"})
public class MySQLConfig {

	@Autowired
	private Environment environment;

	@Bean(name = "mysqlDataSource")
	public DataSource mysqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		log.info("properties url {} ", environment.getProperty("mysql.datasource.url"));
		log.info("properties username {} ", environment.getProperty("mysql.datasource.username"));
		log.info("properties password {} ", environment.getProperty("mysql.datasource.password"));
		log.info("properties driver-class-name {} ", environment.getProperty("mysql.datasource.driver-class-name"));
		
		dataSource.setUrl(environment.getProperty("mysql.datasource.url"));
		dataSource.setUsername(environment.getProperty("mysql.datasource.username"));
		dataSource.setPassword(environment.getProperty("mysql.datasource.password"));
		dataSource.setDriverClassName(environment.getProperty("mysql.datasource.driver-class-name"));
		
		return dataSource;
	}
	
	@Bean(name = "mysqlEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(mysqlDataSource());
		em.setPackagesToScan("com.example.customer.model.mysql");
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", environment.getProperty("mysql.jpa.hibernate.ddl-auto"));
		properties.put("hibernate.show-sql", environment.getProperty("mysql.jpa.show-sql"));
		properties.put("hibernate.dialect", environment.getProperty("mysql.jpa.database-platform"));
		
		em.setJpaPropertyMap(properties);
		
		return em;
		
	}
	
	@Bean(name = "mysqlTtransactionManager")
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		
		return transactionManager;
	}
	
}
