/**
 * @author fpratici
 */
package com.fabrizio.rooftopchallenge.service;

import java.util.Map;

import com.fabrizio.rooftopchallenge.exception.WebException;

public interface TokenService {

    public Map<String,String> getRooftopToken(String email) throws WebException;
    
}