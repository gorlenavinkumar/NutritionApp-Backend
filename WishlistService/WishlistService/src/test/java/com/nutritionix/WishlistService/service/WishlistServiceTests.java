package com.nutritionix.WishlistService.service;

import com.nutritionix.WishlistService.model.BrandedProduct;
import com.nutritionix.WishlistService.model.UserWishlist;
import com.nutritionix.WishlistService.repository.WishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WishlistServiceTests {

    @Mock
    private WishListRepository wishListRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Existing tests...

    @Test
    void testGetUserProducts_UserExists() throws Exception {
        // Mocking
        String username = "testUser";
        BrandedProduct product = new BrandedProduct();
        List<BrandedProduct> productList = new ArrayList<>();
        productList.add(product);
        UserWishlist existingWishList = new UserWishlist(username, productList);

        when(wishListRepository.findById(username)).thenReturn(Optional.of(existingWishList));

        List<BrandedProduct> result = wishlistService.getUserProducts(username);

        assertNotNull(result);
        assertEquals(productList.size(), result.size());
        assertEquals(productList.get(0), result.get(0));

        verify(wishListRepository).findById(username);
    }

    @Test
    void testGetUserProducts_UserDoesNotExist() {
        // Mocking
        String username = "nonExistingUser";

        when(wishListRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> wishlistService.getUserProducts(username));

        verify(wishListRepository).findById(username);
    }

    @Test
    void testDeleteWishListProduct_ProductExists() throws Exception {
        // Mocking
        String username = "testUser";
        String itemId = "123";
        BrandedProduct product = new BrandedProduct();
        product.setNix_item_id(itemId);
        List<BrandedProduct> productList = new ArrayList<>();
        productList.add(product);

        UserWishlist existingWishList = new UserWishlist(username, productList);

        when(wishListRepository.findById(username)).thenReturn(Optional.of(existingWishList));
        when(wishListRepository.save(any())).thenReturn(null);

        List<BrandedProduct> result = wishlistService.deleteWishListProduct(itemId, username);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(wishListRepository).findById(username);
        verify(wishListRepository).save(any(UserWishlist.class));
    }

    @Test
    void testDeleteWishListProduct_ProductDoesNotExist() throws Exception {
        // Mocking
        String username = "testUser";
        String itemId = "123";
        List<BrandedProduct> productList = new ArrayList<>();

        UserWishlist existingWishList = new UserWishlist(username, productList);

        when(wishListRepository.findById(username)).thenReturn(Optional.of(existingWishList));

        List<BrandedProduct> result = wishlistService.deleteWishListProduct(itemId, username);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(wishListRepository).findById(username);
        verify(wishListRepository, never()).save(any(UserWishlist.class));
    }

    @Test
    void testDeleteWishListProduct_UserDoesNotExist() {
        // Mocking
        String username = "nonExistingUser";
        String itemId = "123";

        when(wishListRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> wishlistService.deleteWishListProduct(itemId, username));

        verify(wishListRepository).findById(username);
        verify(wishListRepository, never()).save(any(UserWishlist.class));
    }
}
