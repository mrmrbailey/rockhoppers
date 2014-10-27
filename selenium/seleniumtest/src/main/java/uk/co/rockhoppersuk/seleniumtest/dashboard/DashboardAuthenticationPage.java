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
public class DashboardAuthenticationPage {

    private final WebDriver driver;
    private final String portal;

    private static final String PAGE_TITLE = "Log into Enterprise Series System Portal";

    @FindBy(name = "username")
    private WebElement userNameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(name = "Submit")
    private WebElement submitButton;

//    @Inject
//    public DashboardAuthenticationPage(final WebDriver driver, @Named("dashboard.portal") final String portal) {
    public DashboardAuthenticationPage(final WebDriver driver, final String portal) {
        this.driver = driver;
        this.portal = portal;

        if (!PAGE_TITLE.equalsIgnoreCase(driver.getTitle())) {
            driver.get(portal);
            assert PAGE_TITLE.equalsIgnoreCase(driver.getTitle());
        }
        PageFactory.initElements(driver, this);
    }

    public DashboardPostAuthentication enterDetails() {
        userNameField.sendKeys("administrator");
        passwordField.sendKeys("welcome");

        submitButton.click();
        return PageFactory.initElements(driver, DashboardPostAuthentication.class);
    }

}
