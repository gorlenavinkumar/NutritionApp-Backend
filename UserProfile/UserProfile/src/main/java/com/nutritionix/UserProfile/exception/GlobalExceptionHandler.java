package com.nutritionix.UserProfile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author hp
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	/**
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<String> userNameAlreadyExists(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
