package com.nutritionix.Authentication.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="users")
public class UserProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long userId;
	String username;
	String password;
	String email;
	
	public UserProfile() {
		
	}
	public UserProfile(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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

