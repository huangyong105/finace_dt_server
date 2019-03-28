package com.roncoo.eshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.eshop.mapper.UserMapper;
import com.roncoo.eshop.model.User;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	RestTemplate restTemplate;

	public String hiService() {
		return restTemplate.getForObject("http://USER-SERVICE/helloWorld",String.class);
	}
	
	public List<User> findAllUsers() {
		return userMapper.findAllUsers();
	}
	
}
