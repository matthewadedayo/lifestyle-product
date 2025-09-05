package com.lifestyle.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource({"file:/u01/app/lifestyle-product.properties"})
@SpringBootApplication
public class LifestyleProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifestyleProductServiceApplication.class, args);
	}

}
