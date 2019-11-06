package com.java;

import com.java.domain.User;
import com.java.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest	//@SpringBootTest
public class SpringbootDemo3MybatisApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
		List<User> users = userService.findAll();
		for (User user : users) {
			System.out.println(user);
		}
	}

}
