package com.poc.linkedinqapoc.scraper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ScraperService scraperService;
    private final String TOP_CARD = "main";
    private final int WAIT_FOR_ELEMENT_TIMEOUT = 5;


    public Person scrapeProfile(String profileUrl, String sessionCookie) {
        // open link
        scraperService.open(profileUrl);
        // login
        int retries = 2;
        while(!scraperService.isSignedIn()&&retries>0) {
            scraperService.setSessionCookie(sessionCookie);
            retries--;
        }
        // scrap data
        Person person = new Person();
        scraperService.extractProfile(person);

        return person;
    }

}
