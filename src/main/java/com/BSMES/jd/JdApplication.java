package com.BSMES.jd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JdApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdApplication.class, args);
	}

}
