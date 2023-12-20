package com.nutritionix.NutritionService.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nutritionix.NutritionService.model.BrandedProduct;
import com.nutritionix.NutritionService.service.NutritionService;

@RestController
@RequestMapping("/nutrition")
public class NutritionController {

	
	@Autowired
    private NutritionService nutritionService;
	

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam String query) throws Exception {
    	
        try {
            List<BrandedProduct> products = nutritionService.seaechProductsfromNutritionApi(query);
            // Process the response and return
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error while fetching nutrition information", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/search/{itemId}")
    public ResponseEntity<?> searchProductByItemId(@PathVariable String itemId) throws Exception {
    	
        try {
            BrandedProduct product = nutritionService.getBrandedProduct(itemId);
            // Process the response and return
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while fetching nutrition information", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
