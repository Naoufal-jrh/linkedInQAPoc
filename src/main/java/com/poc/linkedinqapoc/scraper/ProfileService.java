package com.poc.linkedinqapoc.scraper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ScraperService scraperService;
    private final String TOP_CARD = "main";
    private final int WAIT_FOR_ELEMENT_TIMEOUT = 5;

    private void init(String profileUrl) {
        System.out.println("opening profile url: " + profileUrl);
        scraperService.getDriver().get(profileUrl);
    }

    public Person scrapeProfile(String profileUrl) {
        // open link
        // signup if not signed up
        init(profileUrl);
        if(scraperService.isSignedIn()) return scrapeSignedIn();
        else return null;
    }

    private Person scrapeSignedIn() {
        System.out.println("start scraping profile...");
        Person person = new Person();

        WebDriver driver = scraperService.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
        WebElement root = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(TOP_CARD)));

        scraperService.focus();
        ScraperService.wait(5);

        this.setNameAndLocation(person);

//        this.setAbout(person);
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.scrollTo(0, Math.ceil(document.body.scrollHeight / 2));");
//        js.executeScript("window.scrollTo(0, Math.ceil(document.body.scrollHeight / 1.5));");
//
//        this.setExperience(person);
//
//        this.setEducation(person);

        return person;
    }

    private void setEducation(Person person) {

    }

    private void setExperience(Person person) {
    }

    private void setAbout(Person person) {
    }

    private void setNameAndLocation(Person person) {
        System.out.println("getting name and location");
        WebElement topPanel = scraperService.getDriver().findElement(By.xpath("//*[@class='mt2 relative']"));
        person.setName(
                topPanel.findElement(
                        By.tagName("h1")
                ).getText()
        );
        person.setLocation(
                topPanel.findElement(
                        By.xpath(
                                "//*[@class='text-body-small inline t-black--light break-words']"
                        )
                ).getText()
        );
    }
}
