package com.itheima;

import com.itheima.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringcloudDay1ResttemplateApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void contextLoads() {
		//1)创建RestTemplate对象的实例，并交给SpringIOC容器管理
		//2)在控制层使用RestTemplate调用http://localhost:18081/user/list
		String url = "http://localhost:18081/user/list";

		//3)将返回的数据转换成JavaBean对象  User  第1个参数：请求的地址  第2个参数：需要将JSON数据转换对象的字节码对象
		String json = restTemplate.getForObject(url,String.class);
		System.out.println(json);
	}

}
