/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.rockhoppersuk.seleniumtest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class GoogleHomePageTest {


    private WebDriver driver;

    @Before
    public void setupSelenium() {
        driver = new FirefoxDriver();
    }

    /**
     * Test of navigateTo method, of class GoogleHomePage.
     */
    @Test
    public void testGoogle() {
        System.out.println("testGoogle");

        GoogleHomePage homePage = GoogleHomePage.navigateTo(driver);
        homePage.search();

    }

    @After
    public void closeSelenium() {
        driver.close();
        driver.quit();
    }
}
