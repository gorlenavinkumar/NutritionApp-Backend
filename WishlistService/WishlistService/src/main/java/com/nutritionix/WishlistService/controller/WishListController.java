package com.nutritionix.WishlistService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutritionix.WishlistService.feignClient.AuthClient;
import com.nutritionix.WishlistService.model.BrandedProduct;
import com.nutritionix.WishlistService.service.WishlistService;

@RestController
@RequestMapping("/wish")
public class WishListController {
	
	@Autowired 
	AuthClient authClient;
	@Autowired 
	WishlistService wishListService;
	
	private void validateToken(String token) throws Exception {
		
		try {
			if (authClient.validateToken(token)==false || !authClient.validateToken(token)) throw new Exception();
		} catch (Exception e) {
			throw new Exception("feign Client Exception"+e);
		}
	}
	
	public String  getUsername(String token) {
		return authClient.getUsername(token);
	}
//	@GetMapping("/test")
//	public String test(@RequestHeader(name = "Authorization", required = true) String token) throws Exception {
////		System.out.println(token);
////		validateToken(token);
////		return "testing";
//		return authClient.getUsername(token);
//	}
	
	@PostMapping("/addItem")
	public ResponseEntity<String> addProductToWishList(
			@RequestBody BrandedProduct product,
			@RequestHeader(name = "Authorization", required = true) String token) throws Exception{
		validateToken(token);
		String username=getUsername(token);
		 //List<BrandedProduct> products = wishListService.addProdctToWishList(product,username);
		wishListService.addProdctToWishList(product,username);
		return new ResponseEntity<String>("Added to Wishlist Succesfully!!", HttpStatus.CREATED);
	}
	
	
	@GetMapping("/getUserWishList")
	public ResponseEntity<List<BrandedProduct>> getUserWishList(
			@RequestHeader(name = "Authorization", required = true) String token) throws Exception{
		validateToken(token);
		String username=getUsername(token);
		 List<BrandedProduct> products = wishListService.getUserProducts(username);
		return new ResponseEntity<List<BrandedProduct>>(products, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/deleteUserProduct/{itemId}")
	public ResponseEntity<String> deleteUserProduct(
			@PathVariable String itemId,
			@RequestHeader(name = "Authorization", required = true) String token) throws Exception{
		
		validateToken(token);
		String username=getUsername(token);
		
		//List<BrandedProduct> products = wishListService.deleteWishListProduct(itemId, username);
		wishListService.deleteWishListProduct(itemId, username);
		
		return new ResponseEntity<String>("Item Removed from Wishlist", HttpStatus.CREATED);
	}
	
	
	

}
