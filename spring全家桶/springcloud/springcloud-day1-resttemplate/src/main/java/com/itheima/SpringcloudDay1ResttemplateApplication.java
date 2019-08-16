package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringcloudDay1ResttemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudDay1ResttemplateApplication.class, args);
	}


	/****
	 * 创建了一个RestTemplate对象.并且将该对象交给了SpringIOC容器管理
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
