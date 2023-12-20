package com.nutritionix.Authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author hp
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthenticationApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}

}
