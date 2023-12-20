package com.nutritionix.Authentication.service;

import com.nutritionix.Authentication.model.UserCredentials;
import com.nutritionix.Authentication.model.UserProfile;
import com.nutritionix.Authentication.repository.UserProfileRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    private final String secretKey = "testSecretKey";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationService = new AuthenticationService();
        authenticationService.secretkey = secretKey;
    }

    @Test
    void findByUsernameAndPassword_ValidCredentials_ReturnsUserProfile() throws Exception {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        UserProfile userProfile = new UserProfile(username, password);
        when(userProfileRepository.findByUsernameAndPassword(username, password)).thenReturn(userProfile);

        // Act
        UserProfile foundUserProfile = authenticationService.findByUsernameAndPassword(username, password);

        // Assert
        assertNotNull(foundUserProfile);
        assertEquals(username, foundUserProfile.getUsername());
        assertEquals(password, foundUserProfile.getPassword());
    }

    @Test
    void findByUsernameAndPassword_InvalidCredentials_ExceptionThrown() throws Exception {
        // Arrange
        String username = "invalidUser";
        String password = "invalidPassword";
        when(userProfileRepository.findByUsernameAndPassword(username, password)).thenReturn(null);

        // Act & Assert
        assertThrows(Exception.class, () -> authenticationService.findByUsernameAndPassword(username, password));
    }

    @Test
    void generateToken_ValidUserCredentials_ReturnsTokenMap() {
        // Arrange
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUsername("testUser");
        userCredentials.setPassword("testPassword");

        // Act
        Map<String, String> tokenMap = authenticationService.generateToken(userCredentials);

        // Assert
        assertNotNull(tokenMap);
        assertTrue(tokenMap.containsKey("token"));
        assertTrue(tokenMap.containsKey("message"));
        assertEquals("Login Successful", tokenMap.get("message"));

        // Validate the generated token
        String generatedToken = tokenMap.get("token");
        assertNotNull(generatedToken);
        assertDoesNotThrow(() -> Jwts.parser().setSigningKey(secretKey).parseClaimsJws(generatedToken));
    }

    @Test
    void validateToken_ValidToken_ReturnsTrue() {
        // Arrange
        String validToken = generateTestToken();

        // Act & Assert
        assertTrue(authenticationService.validateToken(validToken));
    }

    @Test
    void validateToken_InvalidToken_ReturnsFalse() {
        // Arrange
        String invalidToken = "invalidToken";

        // Act & Assert
        assertFalse(authenticationService.validateToken(invalidToken));
    }

    @Test
    void extractUsername_ValidToken_ReturnsUsername() {
        // Arrange
        String validToken = generateTestToken();

        // Act
        String extractedUsername = authenticationService.extractUsername(validToken);

        // Assert
        assertNotNull(extractedUsername);
        assertEquals("testUser", extractedUsername);
    }

    private String generateTestToken() {
        return Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expires in 1 hour
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}

