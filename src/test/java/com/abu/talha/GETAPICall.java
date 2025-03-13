package com.abu.talha;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class GETAPICall {

    @Test
    public void getUserApiCall() throws IOException {  // Fixed method name
        Playwright playwright = Playwright.create(); // Ensuring resources are closed
        APIRequest apiRequest = playwright.request();
        APIRequestContext apiRequestContext = apiRequest.newContext();
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

        Map<String, String> headerMap = apiResponse.headers();
        System.out.println(headerMap);
    }
}
