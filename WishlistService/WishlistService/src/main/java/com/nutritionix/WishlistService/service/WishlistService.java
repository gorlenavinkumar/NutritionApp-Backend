package com.nutritionix.WishlistService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nutritionix.WishlistService.model.BrandedProduct;
import com.nutritionix.WishlistService.model.UserWishlist;
import com.nutritionix.WishlistService.repository.WishListRepository;

@Service
public class WishlistService {
	@Autowired
	WishListRepository wishListRepository;
	
	public List<BrandedProduct> addProdctToWishList(
			BrandedProduct product, String username) {
		System.out.println(product.toString());
		Optional<UserWishlist> userWishList = wishListRepository.findById(username);
		if(userWishList.isEmpty()) {
			List<BrandedProduct> products = new ArrayList<>();
			products.add(product);
			for(BrandedProduct prod: products) {
				System.out.println(prod.toString());
			}
			UserWishlist wishList = new UserWishlist(username,products);
			
			wishListRepository.save(wishList);
			return wishList.getProducts();
		}else {
			UserWishlist wishList = userWishList.get();
			List<BrandedProduct> products = wishList.getProducts();
			Optional<BrandedProduct> existProductCheck = products.stream()
	                .filter(p -> p.getNix_item_id().equals(product.getNix_item_id()))
	                .findFirst();
			if(existProductCheck.isEmpty())
				products.add(product);
			for(BrandedProduct prod: products) {
				System.out.println(prod.toString());
			}
			wishList.setProducts(products);
			wishListRepository.save(wishList);
			return wishList.getProducts();
		}
	
		
	}
	
	public List<BrandedProduct> getUserProducts(String username) throws Exception{
		Optional<UserWishlist> userWishList = wishListRepository.findById(username);
		if(userWishList.isEmpty()) {
			throw new Exception("User doesnot exist");
		}
		UserWishlist wishList = userWishList.get();
		return wishList.getProducts();
	}
	
	public List<BrandedProduct> deleteWishListProduct(String itemId, String username) throws Exception {
		Optional<UserWishlist> userWishList = wishListRepository.findById(username);
		if(userWishList.isEmpty()) {
			throw new Exception("User doesnot exist");
		}
		UserWishlist wishList = userWishList.get();
		List<BrandedProduct> products = wishList.getProducts();
//		
		products.removeIf(prod -> itemId.equals(prod.getNix_item_id()));
		wishList.setProducts(products);
		wishListRepository.save(wishList);
		
		return wishList.getProducts();
	}
	
	
}
