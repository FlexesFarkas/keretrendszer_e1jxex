package com.keretrendszer_e1jxex.keretrendszer_e1jxex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities")
public class KeretrendszerE1jxexApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeretrendszerE1jxexApplication.class, args);
	}

}
