/**
 * @author fpratici
 */
package com.fabrizio.rooftopchallenge.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fabrizio.rooftopchallenge.exception.WebException;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class CallApiUtils {

    public Map<String,Object> getMapObjectWithQueryParams(String serviceUrl) throws WebException {
        Map<String,Object> response = new HashMap<>();
        try (CloseableHttpClient httpclient = HttpClientBuilder.create().build();) {
            HttpGet httpGet = new HttpGet(serviceUrl);
            httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpGet.setHeader(HttpHeaders.ACCEPT, "application/json");

            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status == 200) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };

            String responseBody = httpclient.execute(httpGet, responseHandler);
            response = new Gson().fromJson(responseBody, HashMap.class);
        }catch (ClientProtocolException e) {
            throw new WebException("Call to ".concat(serviceUrl).concat(" failed. Status: ").concat(e.getMessage()));
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return response;
    }

    public Map<String,Object> postMapObjectWithQueryParams(String serviceUrl, Map<String,Object> map) throws WebException {

        Map<String,Object> response = new HashMap<>();
        JSONObject json = new JSONObject(map);

        try (CloseableHttpClient httpclient = HttpClientBuilder.create().build();) {
            HttpPost httpPost = new HttpPost(serviceUrl);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");
            StringEntity params = new StringEntity(json.toString());
            httpPost.setEntity(params);

            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status == 200) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };

            String responseBody = httpclient.execute(httpPost, responseHandler);
            response = new Gson().fromJson(responseBody, HashMap.class);
        }catch (ClientProtocolException e) {
            throw new WebException("Call to ".concat(serviceUrl).concat(" failed. Status: ").concat(e.getMessage()));
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return response;
    }
    
}