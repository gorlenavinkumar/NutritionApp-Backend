package com.nutritionix.NutritionService.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.nutritionix.NutritionService.model.BrandedProduct;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class NutritionServiceTests {

    private NutritionService nutritionService;

    @BeforeEach
    void setUp() {
        nutritionService = new NutritionService();
    }

    @Test
    void testSearchProductsFromNutritionApi_Success() throws Exception {
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        nutritionService.setHttpClient(httpClient);

        String mockApiResponse = "{\"common\": [], \"branded\": [{\"nix_item_id\": \"123\", \"food_name\": \"Test Product\", \"nix_brand_id\": \"456\", \"brand_name\": \"Test Brand\"}]}";
        when(httpClient.execute(any())).thenReturn(Mockito.mock(HttpResponse.class));
        when(httpClient.execute(any()).getEntity()).thenReturn(Mockito.mock(HttpEntity.class));
        when(EntityUtils.toString(any())).thenReturn(mockApiResponse);

        List<BrandedProduct> products = nutritionService.seaechProductsfromNutritionApi("TestQuery");

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("123", products.get(0).getNix_item_id());
        assertEquals("Test Product", products.get(0).getNix_item_name());
        assertEquals("456", products.get(0).getNix_brand_id());
        assertEquals("Test Brand", products.get(0).getNix_brand_name());
        // Add more assertions based on your response expectations
    }

    @Test
    void testSearchProductsFromNutritionApi_NoResults() throws Exception {
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        nutritionService.setHttpClient(httpClient);

        String mockApiResponse = "{\"common\": [], \"branded\": []}";
        when(httpClient.execute(any())).thenReturn(Mockito.mock(HttpResponse.class));
        when(httpClient.execute(any()).getEntity()).thenReturn(Mockito.mock(HttpEntity.class));
        when(EntityUtils.toString(any())).thenReturn(mockApiResponse);

        List<BrandedProduct> products = nutritionService.seaechProductsfromNutritionApi("TestQuery");

        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    void testSearchProductsFromNutritionApi_Exception() throws Exception {
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        nutritionService.setHttpClient(httpClient);

        when(httpClient.execute(any())).thenThrow(new IOException());

        assertThrows(Exception.class, () -> nutritionService.seaechProductsfromNutritionApi("TestQuery"));
    }

    @Test
    void testGetBrandedProduct_Success() throws ClientProtocolException, IOException {
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        nutritionService.setHttpClient(httpClient);

        String mockApiResponse = "{\"foods\": [{\"nix_item_id\": \"123\", \"food_name\": \"Test Product\", \"nix_brand_id\": \"456\", \"brand_name\": \"Test Brand\"}]}";
        when(httpClient.execute(any())).thenReturn(Mockito.mock(HttpResponse.class));
        when(httpClient.execute(any()).getEntity()).thenReturn(Mockito.mock(HttpEntity.class));
        when(EntityUtils.toString(any())).thenReturn(mockApiResponse);

        BrandedProduct product = nutritionService.getBrandedProduct("123");

        assertNotNull(product);
        assertEquals("123", product.getNix_item_id());
        assertEquals("Test Product", product.getNix_item_name());
        assertEquals("456", product.getNix_brand_id());
        assertEquals("Test Brand", product.getNix_brand_name());
        // Add more assertions based on your response expectations
    }

    @Test
    void testGetBrandedProduct_Exception() throws ClientProtocolException, IOException {
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        nutritionService.setHttpClient(httpClient);

        when(httpClient.execute(any())).thenThrow(new IOException());

        assertThrows(Exception.class, () -> nutritionService.getBrandedProduct("123"));
    }

    @Test
    void testGetBrandedProduct_EmptyResponse() throws ClientProtocolException, IOException {
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        nutritionService.setHttpClient(httpClient);

        String mockApiResponse = "{\"foods\": []}";
        when(httpClient.execute(any())).thenReturn(Mockito.mock(HttpResponse.class));
        when(httpClient.execute(any()).getEntity()).thenReturn(Mockito.mock(HttpEntity.class));
        when(EntityUtils.toString(any())).thenReturn(mockApiResponse);

        BrandedProduct product = nutritionService.getBrandedProduct("123");

        assertNull(product);
    }

    @Test
    void testGetBrandedProduct_MissingFields() throws ClientProtocolException, IOException {
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        nutritionService.setHttpClient(httpClient);

        String mockApiResponse = "{\"foods\": [{\"nix_item_id\": \"123\"}]}";
        when(httpClient.execute(any())).thenReturn(Mockito.mock(HttpResponse.class));
        when(httpClient.execute(any()).getEntity()).thenReturn(Mockito.mock(HttpEntity.class));
        when(EntityUtils.toString(any())).thenReturn(mockApiResponse);

        BrandedProduct product = nutritionService.getBrandedProduct("123");

        assertNotNull(product);
        assertEquals("123", product.getNix_item_id());
        assertNull(product.getNix_item_name());
        // Add assertions for other fields that might be missing
    }
}

