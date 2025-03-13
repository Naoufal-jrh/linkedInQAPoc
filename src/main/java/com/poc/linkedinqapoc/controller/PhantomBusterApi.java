package com.poc.linkedinqapoc.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.linkedinqapoc.services.LinkedInScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PhantomBusterApi {
    private final String apiKey = "j9LBaCYYuYBJrAj3P5924WQqt47qPy0NrFhKUwmwgQw";
    private final LinkedInScraperService linkedInScraperService;
    private final ObjectMapper objectMapper;

    @PostMapping("/profile")
    public String getProfile(@RequestBody String profile) throws IOException {
        System.out.println("request body: " + profile);
        JsonNode jsonNode = objectMapper.readTree(profile);
        String profileUrl = jsonNode.get("profile").asText();
        System.out.println("fetching data from profile: " + profileUrl);
        return linkedInScraperService.runProfileScrapingAgent(profileUrl);
    }

    @GetMapping("/container/{containerId}")
    public String getContainer(@PathVariable String containerId) throws IOException {
        return linkedInScraperService.getProfile(containerId);
    }

}
