/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.rockhoppersuk.seleniumtest.dashboard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 *
 * @author mxbailey
 */
public class DashboardHomePage {

    private final WebDriver driver;

    @FindBy(name = "username")
    private WebElement userNameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    public DashboardHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public static DashboardHomePage navigateTo(WebDriver driver) {
        driver.get("http://156.24.67.30/admin/portal.do");
        return PageFactory.initElements(driver, DashboardHomePage.class);
    }

    public void enterDetails() {
        userNameField.sendKeys("administrator");
        passwordField.sendKeys("welcome");

//        submitButton.click();
    }

}
