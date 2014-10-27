/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.rockhoppersuk.seleniumtest.dashboard;

import uk.co.rockhoppersuk.seleniumtest.dashboard.DashboardHomePage;
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
public class DashboardHomePageTest {



    private WebDriver driver;

    @Before
    public void setupSelenium() {
        driver = new FirefoxDriver();
    }

    /**
     * Test of navigateTo method, of class GoogleHomePage.
     */
    @Test
    public void testDashBaord() {
        System.out.println("testDashBaord");

        DashboardHomePage dashboard = DashboardHomePage.navigateTo(driver);
        dashboard.enterDetails();

    }

    @After
    public void closeSelenium() {
        driver.close();
        driver.quit();
    }

}
