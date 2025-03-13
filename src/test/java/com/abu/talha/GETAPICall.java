package com.abu.talha;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class GETAPICall {
    Playwright playwright;
    APIRequest apiRequest;
    APIRequestContext apiRequestContext;

    @BeforeSuite
    public void beforeTest(){
        playwright = Playwright.create();
        apiRequest = playwright.request();
        apiRequestContext = apiRequest.newContext();
    }

    @Test(priority = 1)
    public void getSpecificAPITest(){
        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setQueryParam("id","856168")
                        .setQueryParam("status","inactive"));
    }

    @Test(priority = 2)
    public void getUserApiCall() throws IOException {
        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users");

        int status = apiResponse.status();
        System.out.println("Status is: " + status);
        Assert.assertEquals(status, 200);  // Fixed assertion

        String text = apiResponse.statusText();
        System.out.println("Response: " + text);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(apiResponse.body());
        String jsonPrettyResponse = jsonNode.toPrettyString();
        System.out.println(jsonPrettyResponse);

        System.out.println(apiResponse.url());
        Assert.assertEquals(apiResponse.url(),"https://gorest.co.in/public/v2/users");


        Map<String, String> headerMap = apiResponse.headers();
        Assert.assertEquals(headerMap.get("content-type"),"application/json; charset=utf-8");
        System.out.println(headerMap);
    }

    @AfterSuite
    public void tearDown(){
        playwright.close();
    }
}
