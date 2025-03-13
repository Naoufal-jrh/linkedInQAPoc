package com.poc.linkedinqapoc.scraper;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ScraperService {
    private final WebDriver driver;
    private static final int WAIT_FOR_ELEMENT_TIMEOUT = 5;
    private static final String TOP_CARD = "pv-top-card";

    public ScraperService(WebDriver driver) {
        this.driver = driver;
    }

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
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("VERIFY_LOGIN_ID")));
            driver.findElement(By.className("VERIFY_LOGIN_ID"));
            System.out.println("user is logged in");
            return true;
        } catch (Exception e) {
            System.out.println("user is not logged in");
            return false;
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

    public WebDriver getDriver() {return this.driver;}


}
