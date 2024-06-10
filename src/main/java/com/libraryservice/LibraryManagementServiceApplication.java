package com.libraryservice;

import com.libraryservice.config.DataSourceConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class LibraryManagementServiceApplication {
	@Autowired
	private DataSourceConfig dataSourceConfig;

	@Autowired
	private Environment env;

	private static final Logger log = LoggerFactory.getLogger(LibraryManagementServiceApplication.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LibraryManagementServiceApplication.class, args);
		LibraryManagementServiceApplication app = context.getBean(LibraryManagementServiceApplication.class);
		// app.entityManagerFactory();

	}

	public void entityManagerFactory() {
		Map<String, String> properties = new HashMap<>();
		properties.put("jakarta.persistence.jdbc.url", env.getProperty("spring.datasource.url"));
		properties.put("jakarta.persistence.jdbc.user", env.getProperty("spring.datasource.username"));
		properties.put("jakarta.persistence.jdbc.password", env.getProperty("spring.datasource.password"));

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("default", properties);
		EntityManager em = emf.createEntityManager();
		em.close();
	}

}
