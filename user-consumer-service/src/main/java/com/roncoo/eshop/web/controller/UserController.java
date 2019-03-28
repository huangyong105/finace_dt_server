package com.roncoo.eshop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.roncoo.eshop.model.User;
import com.roncoo.eshop.service.UserService;

@RestController
public class UserController {


	@Autowired
	private UserService userService;
	@RequestMapping(value = "/hi")
	public String hiService() {
		return userService.hiService();
	}

	@RequestMapping(value = "/helloWorld")
	public String helloWorld() {
		return userService.helloWorld();
	}
}
