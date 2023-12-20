package com.nutritionix.Authentication.repository;

import com.nutritionix.Authentication.model.UserProfile;
import com.nutritionix.Authentication.service.AuthenticationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileRepositoryTests {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private AuthenticationService userProfileService; // Assuming a service that uses UserProfileRepository

    @Test
    void findByUsernameAndPassword_ValidCredentials_ReturnsUserProfile() throws Exception {
        // Arrange
        String username = "testUsername";
        String password = "testPassword";
        UserProfile userProfile = new UserProfile(username, password);
        when(userProfileRepository.findByUsernameAndPassword(username, password)).thenReturn(userProfile);

        // Act
        UserProfile foundUserProfile = userProfileService.findByUsernameAndPassword(username, password);

        // Assert
        assertNotNull(foundUserProfile);
        assertEquals(username, foundUserProfile.getUsername());
        assertEquals(password, foundUserProfile.getPassword());
    }

    @Test
    void findByUsernameAndPassword_InvalidCredentials_ReturnsNull() throws Exception {
        // Arrange
        String username = "invalidUsername";
        String password = "invalidPassword";
        when(userProfileRepository.findByUsernameAndPassword(username, password)).thenReturn(null);

        // Act
        UserProfile foundUserProfile = userProfileService.findByUsernameAndPassword(username, password);

        // Assert
        assertNull(foundUserProfile);
    }

    @Test
    void findByUsername_ValidUsername_ReturnsUserProfile() throws Exception {
        // Arrange
        String username = "testUsername";
        UserProfile userProfile = new UserProfile(username, "password");
        when(userProfileRepository.findByUsername(username)).thenReturn(userProfile);

        // Act
        UserProfile foundUserProfile = userProfileService.findByUsername(username);

        // Assert
        assertNotNull(foundUserProfile);
        assertEquals(username, foundUserProfile.getUsername());
    }

    @Test
    void findByUsername_InvalidUsername_ReturnsNull() throws Exception {
        // Arrange
        String invalidUsername = "invalidUsername";
        when(userProfileRepository.findByUsername(invalidUsername)).thenReturn(null);

        // Act
        UserProfile foundUserProfile = userProfileService.findByUsername(invalidUsername);

        // Assert
        assertNull(foundUserProfile);
    }
}

