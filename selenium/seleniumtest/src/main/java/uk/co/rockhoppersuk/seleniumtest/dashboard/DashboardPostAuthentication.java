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
public class DashboardPostAuthentication {

    private final WebDriver driver;

    @FindBy(name = "Submit")
    private WebElement submitButton;

//    @Inject
    public DashboardPostAuthentication(final WebDriver driver) {
        this.driver = driver;
    }

    public void dismissWarning() {
        submitButton.click();
//        return PageFactory.initElements(driver, HomePage.class);
    }
}
