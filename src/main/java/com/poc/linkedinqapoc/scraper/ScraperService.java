package com.poc.linkedinqapoc.scraper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
public class ScraperService {
    private final WebDriver driver;
    private static final int WAIT_FOR_ELEMENT_TIMEOUT = 5;
    private static final String TOP_CARD = "pv-top-card";

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

    public void mouseClick(WebElement elem) {
        Actions action = new Actions(driver);
        action.moveToElement(elem).perform();
    }

    public WebElement waitForElementToLoad(By by, String name, WebElement base) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.className(name)));
    }

    public List<WebElement> waitForAllElementsToLoad(By by, String name, WebElement base) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(name)));
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

    public void scrollToHalf() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, Math.ceil(document.body.scrollHeight/2));");
    }

    public void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void scrollClassNameElementToPagePercent(String className, float pagePercent) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("var elem = document.getElementsByClassName('" + className + "')[0]; elem.scrollTo(0, elem.scrollHeight * " + pagePercent + ");");
    }

    public boolean findElementByClassName(String className) {
        try {
            driver.findElement(By.className(className));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean findElementByXpath(String tagName) {
        try {
            driver.findElement(By.xpath(tagName));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean findElementByTagName(String tagName) {
        try {
            driver.findElement(By.tagName(tagName));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public boolean findEnabledElementByXpath(String tagName) {
        try {
            WebElement elem = driver.findElement(By.xpath(tagName));
            return elem.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static WebElement findFirstAvailableElement(WebElement... elements) {
        for (WebElement elem : elements) {
            if (elem != null) {
                return elem;
            }
        }
        return null;
    }



    public void open(String url) {
        System.out.println("opening " + url);
        driver.get(url);
    }

    public void setSessionCookie(String sessionCookie) {
        System.out.println("set session cookie: " + sessionCookie);
        driver.manage().addCookie(new Cookie("li_at", sessionCookie));
        wait(5);
        driver.navigate().refresh();
    }


    public void extractProfile(Person person) {
        System.out.println("extracting profile");
        // the profile information box, name, headline, school and comopany, location and contact info button.
        WebElement root = driver.findElement(By.xpath("//*[@class='mt2 relative']"));
        System.out.println("there are "+root.findElements(By.tagName("div")).size()+" divs inside the root element");

//        extracting name and headline--------------------------------------------------------------------------------------
        WebElement nameAndHeadLineBox = root.findElements(By.tagName("div")).get(0);

        String name = nameAndHeadLineBox.findElements(By.tagName("div")).get(0).getText();

        person.setFirstName(
                name.split(" ")[0]
        );
        person.setFirstName(
                name.split(" ")[1]
        );

        person.setHeadLine(
                nameAndHeadLineBox.findElements(By.tagName("div")).get(3).getText()
        );
//        company and school
        WebElement companyAndSchoolBox = root.findElement(By.tagName("ul"));
        System.out.println();
        System.out.println("current company "+companyAndSchoolBox.findElements(By.tagName("span")).get(0).getText());
        System.out.println("current school "+companyAndSchoolBox.findElements(By.tagName("span")).get(1).getText());
//        location and personal info button

        WebElement locationAndInfoButtonBox = root.findElements(By.tagName("div")).get(7);

        System.out.println("current location "+locationAndInfoButtonBox.findElements(By.tagName("span")).get(0).getText());


//        scrap contactInfo from dialogue
        locationAndInfoButtonBox.findElements(By.tagName("span")).get(1).click();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='artdeco-modal__content ember-view']")));
        WebElement contactInfoBox = driver.findElement(By.xpath("//*[@class='artdeco-modal__content ember-view']"));



        System.out.println("contact info section has "+contactInfoBox.findElements(By.tagName("section")).size()+" sections");
        System.out.println("contact info section has "+contactInfoBox.findElements(By.tagName("button")).size()+" buttons");
        System.out.println("contact info box text : "+contactInfoBox.getText());

        for(int i=1; i<contactInfoBox.findElements(By.tagName("section")).size();i++){
            String content = contactInfoBox.findElements(By.tagName("section")).get(i).findElement(By.tagName("div")).getText();
            System.out.println("section "+i+" has "+content);
        }

        WebElement closeButton = driver.findElement(By.xpath("//button[@aria-label='Dismiss']"));
        closeButton.click();
//        scrap the about section
        WebElement aboutSection = driver.findElement(By.xpath("//h2[span[text()='About']]"))
                .findElement(By.xpath("./ancestor::section"));
        System.out.println("about section "+aboutSection.getText().split("\n")[2]);

//        TODO: create a function that scraps the section with the section title, handle the situation of show all button
//        scrap experiences
        WebElement experienceSection = driver.findElement(By.xpath("//h2[span[text()='Experience']]"))
                .findElement(By.xpath("./ancestor::section"));
        List<WebElement> experienceList = experienceSection.findElements(By.tagName("li"));

        System.out.println("the "+experienceList.size()+" experiences :");
        System.out.println("------------------------------------------------------------");
        for (WebElement experience : experienceList) {
            List<WebElement> elements = experience.findElements(By.tagName("span")).stream().filter(span -> "true".equals(span.getAttribute("aria-hidden"))).toList();
            if (elements.size() < 3) continue;
            elements.forEach(element -> System.out.println(element.getText()));
            System.out.println("-------------------------------------------------------------------------");
        }



//        scrap education
        WebElement educationSection = driver.findElement(By.xpath("//h2[span[text()='Education']]"))
                .findElement(By.xpath("./ancestor::section"));
        List<WebElement> educationList = educationSection.findElements(By.tagName("li"));


        System.out.println("the "+educationList.size()+" educations :");
        System.out.println("-----------------------------------------------------------");
        for (WebElement education : educationList) {
            List<WebElement> elements = education.findElements(By.tagName("span")).stream().filter(span -> "true".equals(span.getAttribute("aria-hidden"))).toList();
            if (elements.size() < 3) continue;
            elements.forEach(element -> System.out.println(element.getText()));
            System.out.println("--------------------------------------------------------------");
        }
//        System.out.println("education section "+educationSection.getText());

//        TODO: scrap the languages and skills sections
    }



}
