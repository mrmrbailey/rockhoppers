package com.gtech.selenium.yr;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        String baseUrl = "http://www.yr.no";
        driver.get(baseUrl + "/");
    }

    public SearchResultPage searchFor(String location) {
        WebElement searchField = driver.findElement(By.id("sted"));
        searchField.sendKeys(location);
        searchField.submit();
        return new SearchResultPage(driver);
    }

}
