package com.poc.linkedinqapoc.controller;

import com.poc.linkedinqapoc.scraper.Person;
import com.poc.linkedinqapoc.scraper.ProfileService;
import com.poc.linkedinqapoc.scraper.ScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
