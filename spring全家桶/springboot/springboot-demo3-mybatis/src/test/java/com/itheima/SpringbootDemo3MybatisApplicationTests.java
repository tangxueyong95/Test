package com.itheima;

import com.itheima.domain.User;
import com.itheima.service.UserService;
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
