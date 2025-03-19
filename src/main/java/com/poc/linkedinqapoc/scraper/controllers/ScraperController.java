package com.poc.linkedinqapoc.scraper.controllers;

import com.poc.linkedinqapoc.scraper.models.Person;
import com.poc.linkedinqapoc.scraper.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScraperController {
    private final ProfileService profileService;

    @PostMapping("/profile")
    public Person scrapeProfile(@RequestParam String profileUrl, @RequestParam String sessionCookie) {
        System.out.println("scraping profile url: " + profileUrl);
        System.out.println("scraping profile cookie: " + sessionCookie);
        return profileService.scrapeProfile(profileUrl,sessionCookie);
    }
}
