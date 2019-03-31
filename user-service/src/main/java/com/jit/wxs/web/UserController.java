package com.jit.wxs.web;

import java.util.List;

import com.jit.wxs.entity.SysUser;
import com.jit.wxs.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/uc")
public class UserController {

	@Value("${foo}")
	String foo;

	@Autowired
	private SysUserService userService;
	
	@RequestMapping("/findAllUsers")
	public SysUser findAllUsers() {
		return userService.getById(1);
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
