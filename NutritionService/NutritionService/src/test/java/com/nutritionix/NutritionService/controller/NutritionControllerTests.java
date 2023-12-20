package com.nutritionix.NutritionService.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nutritionix.NutritionService.model.BrandedProduct;
import com.nutritionix.NutritionService.service.NutritionService;

public class NutritionControllerTests {

    @Mock
    private NutritionService nutritionServiceMock;

    @InjectMocks
    private NutritionController nutritionController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchProducts_Success() throws Exception {
        // Mock data
        List<BrandedProduct> mockProducts = new ArrayList<>();
        mockProducts.add(new BrandedProduct(/* Add mock data here */));

        // Mock behavior
        //when(nutritionServiceMock.seaechProductsfromNutritionApi("query")).thenReturn(mockProducts);

        // Perform the test
        ResponseEntity<?> response = nutritionController.searchProducts("hamburger");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(mockProducts, response.getBody());
    }

    @Test
    public void testSearchProducts_Exception() throws Exception {
        // Mock behavior to throw an exception
        when(nutritionServiceMock.seaechProductsfromNutritionApi("query")).thenThrow(IOException.class);

        // Perform the test
        ResponseEntity<?> response = nutritionController.searchProducts("query");

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error while fetching nutrition information", response.getBody());
    }

    @Test
    public void testSearchProductByItemId_Success() throws Exception {
        // Mock data
        BrandedProduct mockProduct = new BrandedProduct(/* Add mock data here */);

        // Mock behavior
        when(nutritionServiceMock.getBrandedProduct("itemId")).thenReturn(mockProduct);

        // Perform the test
        ResponseEntity<?> response = nutritionController.searchProductByItemId("itemId");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
    }

    @Test
    public void testSearchProductByItemId_Exception() throws Exception {
        // Mock behavior to throw an exception
        when(nutritionServiceMock.getBrandedProduct("itemId")).thenThrow(Exception.class);

        // Perform the test
        ResponseEntity<?> response = nutritionController.searchProductByItemId("itemId");

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error while fetching nutrition information", response.getBody());
    }
}

