package com.poc.linkedinqapoc.phantomBusterVersion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LinkedInScraperService {
    private final String apiKey = "j9LBaCYYuYBJrAj3P5924WQqt47qPy0NrFhKUwmwgQw";
    private final String apiUrl = "https://api.phantombuster.com/api/v2/agents/launch";

    public String runProfileScrapingAgent(String profile) throws IOException {
        System.out.println("fetching data from profile: " + profile);
        OkHttpClient client = new OkHttpClient();

        // Define Phantom ID and Profile URL
        String phantomId = "7256133352030335";
        String sessionCookie = "AQEDASxovA8BlhqYAAABlYqPavMAAAGVrpvu804AEzEZ3FtkTNofelc8d0g2kd36I4oTHS2oTY0CDi5XL5ZeMQ6jR9U_fJdJIyZ6BwyBRXGG9PoP-gWgBO8dmrpJjsYSoqI3q3AohEUbMVnkUGPNBKwy";

        // Create JSON Request Body
        String jsonPayload = "{"
                + "\"id\": \"" + phantomId + "\","
                + "\"argument\": {"
                + "\"sessionCookie\": \"" + sessionCookie + "\","
                + "\"profileUrls\": [\"" + profile + "\"]"
                + "}"
                + "}";

        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url("https://api.phantombuster.com/api/v2/agents/launch")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("X-Phantombuster-Key", "j9LBaCYYuYBJrAj3P5924WQqt47qPy0NrFhKUwmwgQw")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println("response: " + response);
        JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());
        return jsonNode.get("containerId").asText();
    }

    public String getProfile(String containerId) throws IOException {
        System.out.println("fetching results of container: " + containerId);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.phantombuster.com/api/v2/containers/fetch-result-object?id="+containerId)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("X-Phantombuster-Key", "j9LBaCYYuYBJrAj3P5924WQqt47qPy0NrFhKUwmwgQw")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println("response: " + response);
        JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());
        String profileResult = jsonNode.get("resultObject").asText();
        System.out.println("profileResult: " + profileResult);
        return profileResult;
    }
}
