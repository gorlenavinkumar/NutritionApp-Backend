package com.nutritionix.UserProfile.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class UserProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long UserId;
	//@NotBlank(message = "Please Provide a Username")
	String username;
	//@Size(min=8, message = "Password must be atleast 8 characters")
	//@NotBlank(message = "Please Provide a Password")
	String password;
	//@Email(message="Please provide a valid email address")
	String email;
	
	public Long getUserId() {
		return UserId;
	}
	public void setUserId(Long userId) {
		UserId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String mail) {
		this.email = mail;
	}
	
	

}
