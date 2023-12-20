package com.nutritionix.Authentication;

/*
 * import org.junit.jupiter.api.Test; import
 * org.springframework.boot.test.context.SpringBootTest;
 * 
 * @SpringBootTest class AuthenticationApplicationTests {
 * 
 * @Test void contextLoads() { }
 * 
 * }
 */
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class AuthenticationApplicationTests {

    @Test
    public void contextLoads() {
        assertDoesNotThrow(() -> AuthenticationApplication.main(new String[]{}));
    }
}
