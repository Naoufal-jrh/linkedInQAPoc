package com.poc.linkedinqapoc.scraper.services;

import com.poc.linkedinqapoc.scraper.models.Education;
import com.poc.linkedinqapoc.scraper.models.Experience;
import com.poc.linkedinqapoc.scraper.models.Person;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
public class ScraperService {
    private final WebDriver driver;
    private static final int WAIT_FOR_ELEMENT_TIMEOUT = 5;

    public static void wait(int duration) {
        try {
            Thread.sleep(duration * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void focus() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("alert('Focus window')");
        driver.switchTo().alert().accept();
    }


    public boolean isSignedIn() {
        System.out.println("checking if signed in");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("join-form")));
            driver.findElement(By.className("join-form"));
            System.out.println("user not logged in");
            return false;
        } catch (Exception e) {
            System.out.println("user is logged in");
            return true;
        }
    }

    public void open(String url) {
        System.out.println("opening " + url);
        driver.get(url);
    }

    public void setSessionCookie(String sessionCookie) {
        System.out.println("setting session cookie: " + sessionCookie);
        driver.manage().addCookie(new Cookie("li_at", sessionCookie));
        wait(5);
        driver.navigate().refresh();
    }

    public void extractPersonalInformation(Person person) {
        System.out.println("extracting personal information...");
        WebElement personalInformationBox = driver.findElement(By.xpath("//*[@class='mt2 relative']"));
//        name and headline
        WebElement nameAndHeadLineBox = personalInformationBox.findElements(By.tagName("div")).get(0);
        String name = nameAndHeadLineBox.findElements(By.tagName("div")).get(0).getText();
        String firstName = name.split("[ \n]")[0];
        String lastName = name.split("[ \n]")[1];
        String headLine = nameAndHeadLineBox.findElements(By.tagName("div")).get(3).getText();

//        company and school
        WebElement companyAndSchoolBox = personalInformationBox.findElement(By.tagName("ul"));
        String currentCompany = companyAndSchoolBox.findElements(By.tagName("span")).get(0).getText();
        String currentSchool = companyAndSchoolBox.findElements(By.tagName("span")).get(1).getText();

//        location and personal info button
        WebElement locationAndInfoButtonBox = personalInformationBox.findElements(By.tagName("div")).get(7);
        String currentLocation = locationAndInfoButtonBox.findElements(By.tagName("span")).get(0).getText();

//        scrap contactInfo from dialogue
        locationAndInfoButtonBox.findElements(By.tagName("span")).get(1).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='artdeco-modal__content ember-view']")));
        WebElement contactInfoBox = driver.findElement(By.xpath("//*[@class='artdeco-modal__content ember-view']"));

        List<String> contactInfo = new ArrayList<>();
        for(int i=1; i<contactInfoBox.findElements(By.tagName("section")).size();i++){
            String content = contactInfoBox.findElements(By.tagName("section")).get(i).findElement(By.tagName("div")).getText();
            contactInfo.add(content);
        }

        String linkedInLink = contactInfo.get(0);
        String email = contactInfo.get(1);
        String joinedDate = contactInfo.get(2);

        // close the dialogue
        WebElement closeButton = driver.findElement(By.xpath("//button[@aria-label='Dismiss']"));
        closeButton.click();

//        about section
        WebElement aboutSection = driver.findElement(By.xpath("//h2[span[text()='About']]"))
                .findElement(By.xpath("./ancestor::section"));
        String about = aboutSection.getText().split("\n")[2];

        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setHeadLine(headLine);
        person.setAbout(about);
        person.setCurrentCompany(currentCompany);
        person.setCurrentSchool(currentSchool);
        person.setCurrentLocation(currentLocation);
        person.setEmail(email);
        person.setJoinedDate(joinedDate);
        person.setLinkedInUrl(linkedInLink);
    }

    public void extractExperience(Person person) {
        //        scrap experiences
        WebElement experienceSection = driver.findElement(By.xpath("//h2[span[text()='Experience']]"))
                .findElement(By.xpath("./ancestor::section"));
        List<WebElement> experienceList = experienceSection.findElements(By.tagName("li"));


        List<Experience> experiences = new ArrayList<>();
        String title="";
        String description="";
        String company="";
        String type="";
        String startDate="";
        String endDate="";
        String location="";
        String duration="";
        String model="";
        for (WebElement experience : experienceList) {
            List<WebElement> elements = experience.findElements(By.tagName("span")).stream().filter(span -> "true".equals(span.getAttribute("aria-hidden"))).toList();
            if (elements.size() < 4) continue;
            title = elements.get(0).getText();
            company = elements.get(1).getText().split("·")[0].strip();
            type = elements.get(1).getText().split("·")[1].strip();
            startDate = elements.get(2).getText().split("[-·]")[0].strip();
            endDate = elements.get(2).getText().split("[-·]")[1].strip();
            duration = elements.get(2).getText().split("[-·]")[2].strip();
            location = elements.get(3).getText().split("·")[0].strip();
            model = elements.get(3).getText().split("·")[1].strip();
            if (elements.size() > 4) description = elements.get(4).getText();
            experiences.add(
                    Experience.builder()
                            .title(title)
                            .company(company)
                            .employmentType(type)
                            .description(description)
                            .fromDate(startDate)
                            .toDate(endDate)
                            .duration(duration)
                            .location(location)
                            .workMode(model)
                            .build()
            );
        }
        person.setExperiences(experiences);
    }

    public void extractEducation(Person person) {
        //        scrap education
        WebElement educationSection = driver.findElement(By.xpath("//h2[span[text()='Education']]"))
                .findElement(By.xpath("./ancestor::section"));
        List<WebElement> educationList = educationSection.findElements(By.tagName("li"));
        List<Education> educations = new ArrayList<>();
        for (WebElement education : educationList) {
            List<WebElement> elements = education.findElements(By.tagName("span")).stream().filter(span -> "true".equals(span.getAttribute("aria-hidden"))).toList();
            if (elements.size() < 3) continue;
            String institution = elements.get(0).getText();
            String degree = elements.get(1).getText().split(",")[0].strip();
            String major = elements.get(1).getText().split(",")[1].strip();
            String startDate = elements.get(2).getText().split("-")[0].strip();
            String endDate = elements.get(2).getText().split("-")[1].strip();

            educations.add(
                    Education.builder()
                            .institution(institution)
                            .degree(degree)
                            .major(major)
                            .fromDate(startDate)
                            .toDate(endDate)
                            .build()
            );
        }
        System.out.println("educations: " + educations);
        person.setEducations(educations);
    }

    public void extractProfile(Person person) {
        System.out.println("extracting profile...");
        extractPersonalInformation(person);
        extractExperience(person);
        extractEducation(person);

//        TODO: create a function that scraps the section with the section title, handle the situation of show all button
//        TODO: scrap the languages and skills sections
    }



}
