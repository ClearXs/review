package com.jw.nacosdiscoveryconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class NacosDiscoveryConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosDiscoveryConsumerApplication.class, args);
	}

	@Configuration
	static class RestTemplateConfiguration {

		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
	}

	@RestController
	static class TestController {

		@Autowired
		private DiscoveryClient discoveryClient;

		@Autowired
		private RestTemplate restTemplate;

		@Autowired
		private LoadBalancerClient balancerClient;

		@GetMapping("/hello")
		public String hello(String name) {
			ServiceInstance serviceInstance;
			List<ServiceInstance> instances = discoveryClient.getInstances("demo-provider");
			if (CollectionUtils.isEmpty(instances)) {
				serviceInstance = balancerClient.choose("demo-provider");
			} else {
				serviceInstance = instances.get(0);
			}
			if (serviceInstance == null) {
				throw new IllegalArgumentException("无法获取服务实例");
			}
			return restTemplate.getForObject(serviceInstance.getUri() + "/echo?name=" + name, String.class);
		}
	}
}
