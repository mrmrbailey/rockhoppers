/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.seleniumtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author mxbailey
 */
public class Selenium2Example {

    public void seleniumIEExample() {
        searchGoogle(new InternetExplorerDriver(), "cheese!");
    }

    public void seleniumFireFoxExample() {
        searchGoogle(new FirefoxDriver(), "cheese!");
    }

    public void seleniumChromeExample() {
        searchGoogle(new ChromeDriver(), "cheese!");
    }

    private void searchGoogle(final WebDriver driver, final String searchText) {

        driver.get("http://www.google.com");

        WebElement element = driver.findElement(By.name("q"));

        element.sendKeys(searchText);

        element.submit();

        System.out.println("Page Title is : " + driver.getTitle());

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith(searchText.toLowerCase());
            }
        });

        System.out.println("Page Title is : " + driver.getTitle());
        driver.quit();

    }

}
