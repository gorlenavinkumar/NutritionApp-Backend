package com.nutritionix.Authentication.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutritionix.Authentication.kafka.KafkaProducer;
import com.nutritionix.Authentication.model.UserCredentials;
import com.nutritionix.Authentication.model.UserProfile;
import com.nutritionix.Authentication.service.AuthenticationService;

/**
 * @author hp
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	AuthenticationService authService;
	
	@Autowired
	KafkaProducer kafkaProducer;
	
	/**
	 * @param userCredentials
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserCredentials userCredentials) throws Exception {
		System.out.println(authService.findByUsername(userCredentials.getUsername()));
        try {
            if (userCredentials.getUsername() == null || userCredentials.getPassword() == null) {
                throw new Exception("username and password cannot be null");
            }
            	UserProfile userProfile = authService.findByUsername(userCredentials.getUsername());
            if (!(userCredentials.getPassword().equals(userProfile.getPassword()))) {
                throw new Exception("Invalid Password");
            }
            Map<String,String> token=authService.generateToken(userCredentials);
            kafkaProducer.sendMessage("Logged In Successfully!! Token got generated !");
            return new ResponseEntity<>(token,HttpStatus.OK);
            /*
             * Create ResponseEntity with token generated by calling generateToken method of JwtTokenGenerator
             */
        } catch (Exception e) {
//            responseEntity = 
            	return	new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
//        return responseEntity;
    }
	
	@GetMapping("/validateToken")
	public boolean validateUser(@RequestHeader("Authorization")String token) {
		
		return authService.validateToken(token);
	}
	
	@GetMapping("/getUsername")
	public String getUsername(@RequestHeader("Authorization")  String token) {
		
		return authService.extractUsername(token);
	}

}