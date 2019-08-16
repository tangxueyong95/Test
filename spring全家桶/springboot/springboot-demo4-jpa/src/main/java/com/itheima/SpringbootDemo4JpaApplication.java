package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootDemo4JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemo4JpaApplication.class, args);
	}

}
