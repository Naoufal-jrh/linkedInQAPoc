package com.poc.linkedinqapoc.scraper.services;

import com.poc.linkedinqapoc.scraper.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ScraperService scraperService;

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
