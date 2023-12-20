package com.nutritionix.NutritionService.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.nutritionix.NutritionService.model.BrandedProduct;

@Service
public class NutritionService {

//	String nutritionApiKey="36625e785051944113389c941607bda2";//cts mail
//	String nutritionApiAppId="5eb48c38";
	
//	String nutritionApiKey="41a2dbf08c1754cd2853bb6cdef99255";// personal msg
//	String nutritionApiAppId="3bfe7fba";
	
	String nutritionApiKey="3f56b92a75a6cf89301e4439ebb1f015";
	String nutritionApiAppId="1082c37d";
	

	public List<BrandedProduct> seaechProductsfromNutritionApi(String query) throws Exception{
		 List<BrandedProduct> products=new ArrayList<>();
		
		String nutritionApiUrl="https://trackapi.nutritionix.com/v2/search/";
		String nutritionixInstantSearch = nutritionApiUrl+"instant";
		
		String jsonPayload = "{\"query\":\""+query+"\"}";
		//System.out.println(jsonPayload);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(nutritionixInstantSearch);
        postRequest.setHeader("Content-Type", "application/json");
        postRequest.setHeader("x-app-id", nutritionApiAppId);
        postRequest.setHeader("x-app-key", nutritionApiKey);
        postRequest.setEntity(new StringEntity(jsonPayload));

        String str = "";

        try{
            HttpResponse response = httpClient.execute(postRequest);
            HttpEntity entity = response.getEntity();
            
            str = EntityUtils.toString(entity);
        }catch (IOException e){
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(str);
        JSONArray common = (JSONArray) jsonObject.get("common");
        //System.out.println("2222222---------------------------------"+common);
        JSONArray branded = (JSONArray) jsonObject.get("branded");
        //System.out.println("33333333---------------------------------"+branded);
//        for (int i = 0; i < branded.length(); i++) {
        for (int i = 0; i < 4; i++) {
        	JSONObject product = branded.getJSONObject(i);
        	//System.out.println(product.getString("nix_item_id"));
        	products.add(getBrandedProduct(product.getString("nix_item_id")));
        	
        }
        
        return products;
		
	}

	
	
	
	public BrandedProduct getBrandedProduct(String nixItemId) {
		
		String itemUrl = "https://trackapi.nutritionix.com/v2/search/item?nix_item_id="+nixItemId;
		HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(itemUrl);
        getRequest.setHeader("Content-Type", "application/json");
        getRequest.setHeader("x-app-id", nutritionApiAppId);
        getRequest.setHeader("x-app-key", nutritionApiKey);
        String str="";
        try{
            HttpResponse response = httpClient.execute(getRequest);
            HttpEntity entity = response.getEntity();
            
            str = EntityUtils.toString(entity);
        }catch (IOException e){
            e.printStackTrace();
        }
        
        
        JSONObject jsonObject = new JSONObject(str);
        System.out.println(str);
        JSONArray foods = (JSONArray) jsonObject.get("foods");
        JSONObject product = foods.getJSONObject(0);
        BrandedProduct prod = new BrandedProduct();
        prod.setNix_item_id(product.getString("nix_item_id"));
    	prod.setNix_item_name(product.getString("food_name"));
    	
    	prod.setNix_brand_id(product.getString("nix_brand_id"));
    	prod.setNix_brand_name(product.getString("brand_name"));
    	prod.setServing_qty(product.getInt("serving_qty"));
    	try{prod.setServing_unit(product.getString("serving_unit"));}catch(Exception e) {prod.setServing_unit("null");}
    	try{prod.setServing_weight_grams(product.getInt("serving_weight_grams"));}catch(Exception e) {prod.setServing_weight_grams(0);}
    	try{prod.setNf_metric_qty(product.getInt("nf_metric_qty"));}catch(Exception e) {prod.setNf_metric_qty(0);}
    	try{prod.setNf_metric_uom(product.getString("nf_metric_uom"));}catch(Exception e) {prod.setNf_metric_uom("null");}
    	try{prod.setNf_calories(product.getInt("nf_calories"));}catch(Exception e) {prod.setNf_calories(0);}
    	try{prod.setNf_total_fat(product.getInt("nf_total_fat"));}catch(Exception e) {prod.setNf_total_fat(0);}
    	
    	try{prod.setNf_saturated_fat(product.getInt("nf_saturated_fat"));}catch(Exception e) {prod.setNf_saturated_fat(0);}
    	try{prod.setNf_cholesterol(product.getInt("nf_cholesterol"));}catch(Exception e) {prod.setNf_cholesterol(0);}
    	try{prod.setNf_sodium(product.getInt("nf_sodium"));}catch(Exception e) {prod.setNf_sodium(0);}
    	try{prod.setNf_total_carbohydrate(product.getInt("nf_total_carbohydrate"));}catch(Exception e) {prod.setNf_total_carbohydrate(0);}
    	try{prod.setNf_dietary_fiber(product.getInt("nf_dietary_fiber"));}catch(Exception e) {prod.setNf_dietary_fiber(0);}
    	try{prod.setNf_sugars(product.getInt("nf_sugars"));}catch(Exception e) {prod.setNf_sugars(0);}
    	try{prod.setNf_protein(product.getInt("nf_protein"));}catch(Exception e) {prod.setNf_protein(0);}
    	try{prod.setNf_potassium(product.getInt("nf_potassium"));}catch(Exception e) {prod.setNf_potassium(0);}
    	
    	
    	return prod;
        
		
	}




	public void setHttpClient(HttpClient httpClient) {
		this.setHttpClient(httpClient);		
	}
	
	
}
