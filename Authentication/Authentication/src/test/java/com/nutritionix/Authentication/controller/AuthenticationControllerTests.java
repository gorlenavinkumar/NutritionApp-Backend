package com.nutritionix.Authentication.controller;

import com.nutritionix.Authentication.model.UserCredentials;
import com.nutritionix.Authentication.model.UserProfile;
import com.nutritionix.Authentication.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationControllerTests {

    @Mock
    private AuthenticationService authService;

    @InjectMocks
    private AuthenticationController authController;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    // Test cases for the loginUser() method

    // Test successful login with valid credentials
    @Test
    void loginUser_ValidCredentials_Success() throws Exception {
        // Mocking user credentials
       // UserCredentials userCredentials = new UserCredentials("username", "password");
        UserCredentials userCredentials = new UserCredentials();

        // Mocking the user profile retrieved from the service
        UserProfile userProfile = new UserProfile("username", "password");
        //UserProfile userProfile = new UserProfile();
        when(authService.findByUsername("username")).thenReturn(userProfile);
        
        // Mocking the token generation
        Map<String, String> token = new HashMap<>();
        token.put("token", "generatedToken");
        when(authService.generateToken(userCredentials)).thenReturn(token);

        // Executing the controller method
        ResponseEntity<?> response = authController.loginUser(userCredentials);

        // Asserting successful login response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Map);
        assertEquals(token, response.getBody());
    }

    // Test login failure with invalid credentials
    @Test
    void loginUser_InvalidCredentials_ExceptionThrown() throws Exception {
        UserCredentials userCredentials = new UserCredentials();
        UserProfile userProfile = new UserProfile("username", "correctPassword");
        //UserCredentials userCredentials = new UserCredentials();
        //UserProfile userProfile = new UserProfile();
        when(authService.findByUsername("username")).thenReturn(userProfile);

        ResponseEntity<?> response = authController.loginUser(userCredentials);

        // Asserting login failure response
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof String);
    }

    // Test cases for the validateUser() method

    // Test successful token validation
    @Test
    void validateUser_ValidToken_Success() {
        String validToken = "validToken";
        when(authService.validateToken(validToken)).thenReturn(true);

        assertTrue(authController.validateUser(validToken));
    }

    // Test token validation failure
    @Test
    void validateUser_InvalidToken_Failure() {
        String invalidToken = "invalidToken";
        when(authService.validateToken(invalidToken)).thenReturn(false);

        assertFalse(authController.validateUser(invalidToken));
    }

    // Test cases for the getUsername() method

    // Test successful username extraction from a valid token
    @Test
    void getUsername_ValidToken_ReturnsUsername() {
        String validToken = "validToken";
        String username = "testUser";
        when(authService.extractUsername(validToken)).thenReturn(username);

        assertEquals(username, authController.getUsername(validToken));
    }

    // Test failure in username extraction from an invalid token
    @Test
    void getUsername_InvalidToken_ReturnsEmpty() {
        String invalidToken = "invalidToken";
        when(authService.extractUsername(invalidToken)).thenReturn(null);

        assertNull(authController.getUsername(invalidToken));
    }
}

