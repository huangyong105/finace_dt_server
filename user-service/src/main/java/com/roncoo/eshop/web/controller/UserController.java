package com.roncoo.eshop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.roncoo.eshop.model.User;
import com.roncoo.eshop.service.UserService;

@RestController
@RefreshScope
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


	@RequestMapping("/helloWorld")
	@ResponseBody
	public String helloWorld() {
		return "helloWorld"+foo;
	}


}
