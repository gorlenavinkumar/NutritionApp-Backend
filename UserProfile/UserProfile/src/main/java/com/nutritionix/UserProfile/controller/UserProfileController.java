package com.nutritionix.UserProfile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutritionix.UserProfile.model.UserProfile;
import com.nutritionix.UserProfile.service.UserProfileService;

@RestController
@RequestMapping("/userProfile")
public class UserProfileController {
	@Autowired
    private UserProfileService userProfileService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserProfile user) throws Exception {
        userProfileService.registerUser(user);
        return new ResponseEntity<String>("User registered successfully",HttpStatus.CREATED);
    }
}
