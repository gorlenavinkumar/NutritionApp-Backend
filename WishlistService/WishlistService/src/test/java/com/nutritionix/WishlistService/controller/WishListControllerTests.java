package com.nutritionix.WishlistService.controller;

import com.nutritionix.WishlistService.feignClient.AuthClient;
import com.nutritionix.WishlistService.model.BrandedProduct;
import com.nutritionix.WishlistService.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WishListControllerTest {

    @Mock
    private AuthClient authClient;

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private WishListController wishListController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProductToWishList() throws Exception {
        // Mocking
        BrandedProduct product = new BrandedProduct();
        String token = "validToken";
        String username = "testUser";

        when(authClient.validateToken(token)).thenReturn(true);
        when(authClient.getUsername(token)).thenReturn(username);

        ResponseEntity<String> response = wishListController.addProductToWishList(product, token);

        verify(authClient).validateToken(token);
        verify(authClient).getUsername(token);
        verify(wishlistService).addProdctToWishList(product, username);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Added to Wishlist Succesfully!!", response.getBody());
    }

    @Test
    void testGetUserWishList() throws Exception {
        // Mocking
        String token = "validToken";
        String username = "testUser";
        List<BrandedProduct> productList = new ArrayList<>();

        when(authClient.validateToken(token)).thenReturn(true);
        when(authClient.getUsername(token)).thenReturn(username);
        when(wishlistService.getUserProducts(username)).thenReturn(productList);

        ResponseEntity<List<BrandedProduct>> response = wishListController.getUserWishList(token);

        verify(authClient).validateToken(token);
        verify(authClient).getUsername(token);
        verify(wishlistService).getUserProducts(username);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productList, response.getBody());
    }

    @Test
    void testDeleteUserProduct() throws Exception {
        // Mocking
        String itemId = "123";
        String token = "validToken";
        String username = "testUser";

        when(authClient.validateToken(token)).thenReturn(true);
        when(authClient.getUsername(token)).thenReturn(username);

        ResponseEntity<String> response = wishListController.deleteUserProduct(itemId, token);

        verify(authClient).validateToken(token);
        verify(authClient).getUsername(token);
        verify(wishlistService).deleteWishListProduct(itemId, username);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Item Removed from Wishlist", response.getBody());
    }
}

