/**
 * @author fpratici
 */
package com.fabrizio.rooftopchallenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import com.fabrizio.rooftopchallenge.data.TestData;
import com.fabrizio.rooftopchallenge.exception.WebException;
import com.fabrizio.rooftopchallenge.service.impl.TokenServiceImpl;
import com.fabrizio.rooftopchallenge.utils.CallApiUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @Mock
    private Environment environment;

    @Mock
    private CallApiUtils callApiUtils;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Nested
    @DisplayName("getRooftopToken tests")
    class getRooftopTokenTests{

        @Test
        @DisplayName("Get token OK")
        void test1(){
            when(environment.getProperty(anyString())).thenReturn("mockurl");
            when(callApiUtils.getMapObjectWithQueryParams(anyString())).thenReturn(TestData.getTokenResponse());

            Map<String,String> response = tokenService.getRooftopToken("email");

            assertNotNull(response);
            assertNotNull(response.get("token"));
            assertEquals("tokenvalue", response.get("token"));

            verify(environment).getProperty(anyString());
            verify(callApiUtils).getMapObjectWithQueryParams(anyString());
        } 

        @Test
        @DisplayName("Get token without email parameter")
        void test2(){

            WebException exception = assertThrows(WebException.class, () -> {
                tokenService.getRooftopToken(null);
            });

            assertNotNull(exception);
            assertEquals("Email can not be empty", exception.getMessage());
        } 

        @Test
        @DisplayName("Get token with authentication failing")
        void test3(){
            when(environment.getProperty(anyString())).thenReturn("mockurl");
            when(callApiUtils.getMapObjectWithQueryParams(anyString())).thenReturn(null);

            WebException exception = assertThrows(WebException.class, () -> {
                tokenService.getRooftopToken("email");
            });

            assertNotNull(exception);
            assertEquals("Token authentication failed", exception.getMessage());
        } 
    }
}