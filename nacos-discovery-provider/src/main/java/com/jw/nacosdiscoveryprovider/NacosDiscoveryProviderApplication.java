package com.jw.nacosdiscoveryprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosDiscoveryProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosDiscoveryProviderApplication.class, args);
	}

	@RestController
	static class TestController {

		@GetMapping("/echo")
		public String echo(String name) {
			return "provider: " + name;
		}
	}
}
