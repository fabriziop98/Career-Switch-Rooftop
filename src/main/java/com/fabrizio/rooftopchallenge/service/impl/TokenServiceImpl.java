/**
 * @author fpratici
 */
package com.fabrizio.rooftopchallenge.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.fabrizio.rooftopchallenge.exception.WebException;
import com.fabrizio.rooftopchallenge.service.TokenService;
import com.fabrizio.rooftopchallenge.utils.CallApiUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private Environment env;

    @Autowired
    private CallApiUtils callApiUtils;

    @Override
    public Map<String,String> getRooftopToken(String email) throws WebException {
        
        if(email == null || email.isEmpty()) throw new WebException("Email can not be empty");

        String serviceUrl = env.getProperty("tokenurl");
        serviceUrl = serviceUrl.concat(email);

        Map<String,Object> responseApi = callApiUtils.getMapObjectWithQueryParams(serviceUrl);
        Map<String,String> response = new HashMap<>();

        if(responseApi != null && responseApi.get("token") != null ){
            response.put("token", responseApi.get("token").toString());
        } else {
            throw new WebException("Token authentication failed");
        }


        return response;
    }
    
}