package com.roncoo.eshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;

import com.roncoo.eshop.mapper.UserMapper;
import com.roncoo.eshop.model.User;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	LoadBalancerClient loadBalancerClient;

	@Autowired
	RestTemplate restTemplate;

	public String hiService() {
		return restTemplate.getForObject(getUrl("user-service", "/helloWorld"),String.class);
	}
	
	public List<User> findAllUsers() {
		return userMapper.findAllUsers();
	}

	/**
	 * 获取指定url
	 * @param clientApplicationName 指定的服务提供名
	 * @param interfaceName 需要消费的接口名
	 * @return
	 */
	private String getUrl(String clientApplicationName, String interfaceName) {
		// 使用loadBalancerClient的choose函数来负载均衡的选出一个eurekaClient的服务实例
		ServiceInstance serviceInstance = loadBalancerClient.choose(clientApplicationName);
		// 获取之前eurekaClient /all接口地址
		String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + interfaceName;
		System.out.println(url);
		return url;
	}
}
