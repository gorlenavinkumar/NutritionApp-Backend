package com.nutritionix.UserProfile.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nutritionix.UserProfile.model.UserProfile;
import com.nutritionix.UserProfile.service.UserProfileService;

public class UserProfileControllerTests {

    private UserProfileController userProfileController;
    private UserProfileService userProfileService;

    @BeforeEach
    public void setUp() {
        userProfileService = mock(UserProfileService.class);
        userProfileController = new UserProfileController();
       // userProfileController.setUserProfileService(userProfileService);
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        UserProfile user = new UserProfile(); // Create a sample user profile
        user.setUserId((long) 1);
        user.setUsername("abc");
        user.setPassword("12345");
        user.setEmail("abc@gmail.com");

        //doNothing().when(userProfileService.registerUser(userProfile)); // Mock registerUser method to do nothing

        ResponseEntity<String> response = userProfileController.registerUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
        verify(userProfileService, times(1)).registerUser(user); // Verify registerUser was called once
    }

    @Test
    public void testRegisterUser_Exception() throws Exception {
        UserProfile userProfile = new UserProfile(); // Create a sample user profile

        doThrow(Exception.class).when(userProfileService).registerUser(userProfile); // Mock registerUser to throw an exception

        ResponseEntity<String> response = userProfileController.registerUser(userProfile);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody()); // In case of exception, the response body should be null
        verify(userProfileService, times(1)).registerUser(userProfile); // Verify registerUser was called once
    }
}

