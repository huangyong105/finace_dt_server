package com.roncoo.eshop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.roncoo.eshop.model.User;
import com.roncoo.eshop.service.UserService;

@RestController
@RequestMapping(value = "/uc")
public class UserController {

	@Value("${foo}")
	String foo;

	@Autowired
	private UserService userService;
	
	@RequestMapping("/findAllUsers")
	@ResponseBody
	public List<User> findAllUsers() {
		return userService.findAllUsers();
	}


	@RequestMapping(value = "/helloWorld",method = RequestMethod.GET)
	@ResponseBody
	public String helloWorld() {
		return "helloWorld"+foo;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}
}
