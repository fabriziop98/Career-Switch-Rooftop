/**
 * @author fpratici
 */
package com.fabrizio.rooftopchallenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fabrizio.rooftopchallenge.data.TestData;
import com.fabrizio.rooftopchallenge.dto.BlocksResponseDTO;
import com.fabrizio.rooftopchallenge.exception.WebException;
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
public class BlocksServiceTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private CallApiUtils callApiUtils;

    @Mock
    private Environment environment;

    @InjectMocks
    private BlocksService blocksService;

    @Nested
    @DisplayName("Check Tests")
    class CheckTests{

        @Test
        @DisplayName("check OK")
        void test1(){

            when(environment.getProperty(anyString())).thenReturn("value");
            when(callApiUtils.postMapObjectWithQueryParams(anyString(), anyMap())).thenReturn(TestData.getOrderedResponse(true));

            String response = blocksService.check(TestData.getBlocksArray(), "token");

            assertNotNull(response);
            assertEquals("block1block2block3", response);
            
            verify(environment).getProperty(anyString());
            verify(callApiUtils, times(2)).postMapObjectWithQueryParams(anyString(), anyMap());
            
        }

        @Test
        @DisplayName("check not valid blocks")
        void test2(){

            WebException e = assertThrows(WebException.class, () -> {
                blocksService.check(null, "token");
            });

            assertNotNull(e);
            assertEquals("The given blocks are not valid", e.getMessage());

            verify(environment, never()).getProperty(anyString());
            verify(callApiUtils, never()).postMapObjectWithQueryParams(anyString(), anyMap());
            
        }

    }

    @Nested
    @DisplayName("Get blocks tests")
    class GetBlocksTests{

        @Test
        @DisplayName("getBlocks OK")
        void test1(){
            when(environment.getProperty(anyString())).thenReturn("value");
            when(callApiUtils.getMapObjectWithQueryParams(anyString())).thenReturn(TestData.getBlocksResponse());

            BlocksResponseDTO response = blocksService.getBlocks("token");

            assertNotNull(response);
            assertNotNull(response.getData());
            assertEquals(100, response.getChunkSize());
            assertEquals(900, response.getLength());
            assertEquals(3, response.getData().size());

            verify(environment).getProperty(anyString());
            verify(callApiUtils).getMapObjectWithQueryParams(anyString());
        }
    }

}