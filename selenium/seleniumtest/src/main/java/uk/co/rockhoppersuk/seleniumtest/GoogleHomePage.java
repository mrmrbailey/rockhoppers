/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.rockhoppersuk.seleniumtest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 *
 * @author mxbailey
 */
public class GoogleHomePage {
    
    private final WebDriver driver;
    
    @FindBy(name = "q")
    WebElement searchBox;

    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public static GoogleHomePage navigateTo(WebDriver driver) {
        driver.get("http://www.google.com");
        return PageFactory.initElements(driver, GoogleHomePage.class);
    }

    public void search() {
        searchBox.sendKeys("Cheese!");
        searchBox.submit();
    }


}
