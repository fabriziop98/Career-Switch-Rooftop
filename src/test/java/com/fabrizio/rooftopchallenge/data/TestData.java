/**
 * @author fpratici
 */
package com.fabrizio.rooftopchallenge.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestData {

    public static Map<String,String> getToken(){
        Map<String,String> response = new HashMap<>();
        response.put("token", "tokenvalue");
        return response;
    }

    public static Map<String,Object> getTokenResponse(){
        Map<String,Object> response = new HashMap<>();
        response.put("token", "tokenvalue");
        return response;
    }

    public static Map<String,Object> getOrderedResponse(boolean ordered){
        Map<String,Object> response = new HashMap<>();
        response.put("message", ordered);
        return response;
    }

    public static String[] getBlocksArray(){
        String[] blocks = {"block1","block2","block3"};
        return blocks;
    }

    public static Map<String,Object> getBlocksResponse(){
        Map<String,Object> response = new HashMap<>();
        List<String> blocks = new ArrayList<>();
        blocks.add("block1");
        blocks.add("block2");
        blocks.add("block3");
        response.put("data", blocks);
        response.put("chunkSize", 100);
        response.put("length", 900);
        return response;
    }

}